package com.ahmedadel.twitterclient.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ahmedadel.twitterclient.ApplicationStatus;
import com.ahmedadel.twitterclient.LocaleHelper;
import com.ahmedadel.twitterclient.MainApplication;
import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.WifiInternetConnectivity;
import com.ahmedadel.twitterclient.databinding.ActivityFollowersBinding;
import com.ahmedadel.twitterclient.model.Followers;
import com.ahmedadel.twitterclient.model.User;
import com.ahmedadel.twitterclient.viewmodel.DataListener;
import com.ahmedadel.twitterclient.viewmodel.FollowersViewModel;
import com.ahmedadel.twitterclient.viewmodel.adapter.FollowersActivityAdapter;
import com.ahmedadel.twitterclient.viewmodel.adapter.OnLoadMoreListener;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by ahmedadel on 24/06/16.
 * <p/>
 * FollowersActivity is a view that handle binding components of activity_followers and FollowersViewModel class
 */

public class FollowersActivity extends AppCompatActivity implements DataListener, OnLoadMoreListener {

    public static final String EXTRA_REPOSITORY = "EXTRA_FOLLOWER_INFO";

    private ActivityFollowersBinding activityFollowersBinding;
    private FollowersViewModel followersViewModel;
    private DataListener dataListener = this;

    private FollowersActivityAdapter followersActivityAdapter;
    private Followers followers;
    private List<User> userList = new ArrayList<>();

    private static FollowersActivity followersActivity;

    private Realm realm;
    private ApplicationStatus applicationStatus;

    private WifiInternetConnectivity wifiInternetConnectivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        followersActivity = this;

        // initialize session, realm , applicationStatus and wifiInternetConnectivity
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        MainApplication mainApplication = MainApplication.getInstance().create(FollowersActivity.this);
        realm = mainApplication.getRealm();
        applicationStatus = ApplicationStatus.getInstance(FollowersActivity.this);
        wifiInternetConnectivity = WifiInternetConnectivity.getInstance(FollowersActivity.this);
        //----------------------------------------------------------------------------------------------------------//

        // initialize data binding components and support required action bar after making data binding
        initDataBinding(session, session.getUserId());
        setSupportActionBar(activityFollowersBinding.activityFollowersAppToolbar);
        activityFollowersBinding.toolbarTitle.setText(getString(R.string.followers, session.getUserName()));
        //----------------------------------------------------------------------------------------------------------//

        // set up Followers list and initialize its components
        setupListFollowers(activityFollowersBinding.activityFollowersRecyclerView);
        //----------------------------------------------------------------------------------------------------------//

    }

    /**
     * initialize data binding components from FollowersViewModel that is connected to activity_followers xml file
     *
     * @param session is the TwitterSession that is sent to followersViewModel objetc to make follower/list API request
     * @param userId  of the user that we need his/her followers/list API
     */
    private void initDataBinding(TwitterSession session, Long userId) {
        activityFollowersBinding = DataBindingUtil.setContentView(this, R.layout.activity_followers);
        followersViewModel = new FollowersViewModel(dataListener, getContext(), session, userId);
        activityFollowersBinding.setFollowersViewModel(followersViewModel);
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * set up list of followers, initialize recycler view and set its layout to linear layout and initialize its adapter
     *
     * @param recyclerView that we be returned from binding data
     */
    private void setupListFollowers(RecyclerView recyclerView) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FollowersActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        followersActivityAdapter = new FollowersActivityAdapter(recyclerView, userList);
        followersActivityAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(followersActivityAdapter);
        followersViewModel.setFollowersActivityAdapter(followersActivityAdapter);

    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * newIntent is a static global function that is called from FollowersItemViewModel to be navigated to
     * FollowerInfoActivity to show tweets of specific user
     *
     * @param user that we will send via intent to FollowersInfoActivity
     */
    public static void newIntent(User user) {
        Intent intent = new Intent(followersActivity, FollowersInfoActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, Parcels.wrap(user));
        followersActivity.startActivity(intent);
        followersActivity.overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * showCachedFollowers() function is to check from Realm database schema if users list is exists or not to show
     * them in no internet connection status
     */
    private void showCachedFollowers() {
        RealmResults<User> users = realm.where(User.class).findAll();
        if (!users.isEmpty()) {
            if (users.size() != 0) {
                followersActivityAdapter = (FollowersActivityAdapter)
                        activityFollowersBinding.activityFollowersRecyclerView.getAdapter();
                for (int i = 0; i < users.size(); i++) {
                    userList.add(users.get(i));
                    followersActivityAdapter.notifyItemInserted(userList.size());
                }
            } else {
                Toast.makeText(FollowersActivity.this, "No More Followers are cached", Toast.LENGTH_SHORT).show();
            }
            followersActivityAdapter.notifyDataSetChanged();
        }
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public Context getContext() {
        return FollowersActivity.this;
    }

    /**
     * dataListenerLoaded is a callback that is fired once a successive response is returned from follower/list API
     * this callback is called inside FollowersViewModel initialized method
     *
     * @param object          that is returned from successive response of API
     * @param isPullToRefresh illustrates if this request that is returned from FollowersViewModel comes from initialized
     *                        method of FollowersViewModel or from Pull To Refresh
     * @param isLoadMore      illustrates if this request that is returned from FollowersViewModel comes from initialized
     *                        method of FollowersViewModel or from Load More
     */
    @Override
    public void dataListenerLoaded(Object object, boolean isPullToRefresh, boolean isLoadMore) {
        followers = (Followers) object;

        followersActivityAdapter = (FollowersActivityAdapter)
                activityFollowersBinding.activityFollowersRecyclerView.getAdapter();

        if (isLoadMore) {
            userList.remove(userList.size() - 1);
            followersActivityAdapter.notifyItemRemoved(userList.size());
        }

        if (isPullToRefresh) {
            userList.clear();
        }

        for (int i = 0; i < followers.getUsers().size(); i++) {
            userList.add(followers.getUsers().get(i));
            followersActivityAdapter.notifyItemInserted(userList.size());
        }

        followersActivityAdapter.notifyDataSetChanged();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(userList);
        realm.commitTransaction();
    }
    //----------------------------------------------------------------------------------------------------------//


    /**
     * errorListener is a callback that is fired once an error occurs from any Observable
     *
     * @param message that is returned from handled Observable
     */
    @Override
    public void errorListenerLoaded(String message) {
        showCachedFollowers();
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * onLoadMore is a callback that is fired when the user scroll down and get the last index of items of recycler view
     * Since this callback is handled inside FollowersActivityAdapter
     *
     * @param isLoading indicated if the user reached the last index of recycler view or not
     */
    @Override
    public void onLoadMore(boolean isLoading) {
        if (isLoading) {
            userList.add(null);
            followersActivityAdapter.notifyItemInserted(userList.size() - 1);
            if (!wifiInternetConnectivity.checkInternetAvailability()) {
                followersActivityAdapter = (FollowersActivityAdapter)
                        activityFollowersBinding.activityFollowersRecyclerView.getAdapter();
                userList.remove(userList.size() - 1);
                followersActivityAdapter.notifyItemRemoved(userList.size());
                Toast.makeText(FollowersActivity.this, "No More Followers are cached", Toast.LENGTH_SHORT).show();
            } else {
                followersViewModel.fetchFollowersList(false, true, followers.getNextCursor());
            }
        }
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            changeAppLocalize();
        }
        if (id == R.id.logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    //----------------------------------------------------------------------------------------------------------//


    private void changeAppLocalize() {
        String currentLanguage = "";
        if (LocaleHelper.getLanguage(FollowersActivity.this).equals("en")) {
            currentLanguage = "Arabic";
        } else if (LocaleHelper.getLanguage(FollowersActivity.this).equals("ar")) {
            currentLanguage = "English";
        }

        final String finalCurrentLanguage = currentLanguage;
        AlertDialog changeAppLocalizeAlertDialog = new AlertDialog.Builder(FollowersActivity.this)
                .setTitle(getResources().getString(R.string.change_language))
                .setMessage(currentLanguage.equals("English")
                        ? getResources().getString(R.string.change_language_body_english)
                        : getResources().getString(R.string.change_language_body_arabic))
                .setPositiveButton(R.string.logout_yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                if (finalCurrentLanguage.equals("English")) {
                                    LocaleHelper.setLocale(FollowersActivity.this, "en");
                                } else if (finalCurrentLanguage.equals("Arabic")) {
                                    LocaleHelper.setLocale(FollowersActivity.this, "ar");
                                }

                                Intent followersActivityIntent = new Intent(FollowersActivity.this, SplashScreen.class);
                                followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                FollowersActivity.this.startActivity(followersActivityIntent);
                                FollowersActivity.this.finish();
                            }
                        })
                .setNegativeButton(R.string.logout_cancel, null).create();
        changeAppLocalizeAlertDialog.show();
    }

    /**
     * logount() is function that has AlertDialog and confirmation message that telling the user to be sure that he/sher
     * wants to log out
     */
    private void logout() {
        AlertDialog logoutAlertDialog = new AlertDialog.Builder(FollowersActivity.this)
                .setTitle(getResources().getString(R.string.logout_title))
                .setMessage(getResources().getString(R.string.logout_body))
                .setPositiveButton(R.string.logout_yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Remove all Realm Database Schemas
                                realm.beginTransaction();
                                realm.deleteAll();
                                realm.commitTransaction();

                                // Logout from Twitter Session Manager and Twitter SDK
                                Twitter.getSessionManager().clearActiveSession();
                                Twitter.logOut();

                                // update login status and navigate to SplashScreen to start again
                                applicationStatus.userLoginStatus(false);
                                Intent followersActivityIntent = new Intent(FollowersActivity.this, SplashScreen.class);
                                followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                FollowersActivity.this.startActivity(followersActivityIntent);
                                FollowersActivity.this.finish();
                            }
                        })
                .setNegativeButton(R.string.logout_cancel, null).create();
        logoutAlertDialog.show();
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    protected void onDestroy() {
        super.onDestroy();
        followersViewModel.destroy();
    }
}

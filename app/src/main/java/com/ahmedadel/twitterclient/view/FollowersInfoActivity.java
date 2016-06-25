package com.ahmedadel.twitterclient.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmedadel.twitterclient.MainApplication;
import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.databinding.ActivityFollowersInfoBinding;
import com.ahmedadel.twitterclient.model.Tweets;
import com.ahmedadel.twitterclient.model.User;
import com.ahmedadel.twitterclient.model.UserTweets;
import com.ahmedadel.twitterclient.viewmodel.DataListener;
import com.ahmedadel.twitterclient.viewmodel.FollowersInfoViewModel;
import com.ahmedadel.twitterclient.viewmodel.adapter.FollowersInfoActivityAdapter;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by ahmedadel on 25/06/16.
 * <p/>
 * FollowersInfoActivity is a view that handle binding components of activity_followers_info and FollowersInfoViewModel class
 */

public class FollowersInfoActivity extends AppCompatActivity implements DataListener {

    private ActivityFollowersInfoBinding activityFollowersInfoBinding;
    private FollowersInfoViewModel followersInfoViewModel;
    private DataListener dataListener = this;

    private FollowersInfoActivityAdapter followersInfoActivityAdapter;

    private User user;
    private List<Tweets> tweetsList = new ArrayList<>();

    private MainApplication mainApplication;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get user from FollowersActivity public static golbal class that is called from FollowersItemListViewModel class
        user = Parcels.unwrap(getIntent().getParcelableExtra(FollowersActivity.EXTRA_REPOSITORY));
        //----------------------------------------------------------------------------------------------------------//

        // initialize mainApplication and realm
        mainApplication = MainApplication.getInstance().create(FollowersInfoActivity.this);
        realm = mainApplication.getRealm();
        //----------------------------------------------------------------------------------------------------------//

        // initialize initDataBinding and set setSupportActionBar with the binded toolbar
        initDataBinding(user);
        setSupportActionBar(activityFollowersInfoBinding.activityFollowersInfoToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityFollowersInfoBinding.activityFollowersInfoCollapsingToolbarLayout.setTitle(user.getFollowerFullName());
        //----------------------------------------------------------------------------------------------------------//

        // Load header image using Picasso
        loadImage(activityFollowersInfoBinding.activityFollowersInfoBackgroundImageIvew, user.getFollowerBackgroundImage());
        //----------------------------------------------------------------------------------------------------------//

        // set up UserFollowers list and initialize its components
        setupListFollowersInfo(activityFollowersInfoBinding.activityFollowersInfoRecyclerView);
        //----------------------------------------------------------------------------------------------------------//
    }

    /**
     * initialize data binding components from FollowersInfoViewModel that is connected to activity_followers_info xml file
     *
     * @param user that will be used inside FollowersInfoModel to git his/her ID
     */
    private void initDataBinding(User user) {
        TwitterSession session = Twitter.getSessionManager()
                .getActiveSession();
        activityFollowersInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_followers_info);
        followersInfoViewModel = new FollowersInfoViewModel(dataListener, getContext(), session, user);
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * loadImage is a function that load the background header image of te the user
     *
     * @param view is an instance of ImageView
     * @param url  of the target user
     */
    private void loadImage(ImageView view, String url) {
        Picasso.with(FollowersInfoActivity.this).load(url).error(R.drawable.ic_signin_twitter)
                .placeholder(R.mipmap.ic_launcher).into(view);
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * set up list of tweets, initialize recycler view and set its layout to linear layout and initialize its adapter
     *
     * @param recyclerView that we be returned from binding data
     */
    private void setupListFollowersInfo(RecyclerView recyclerView) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FollowersInfoActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        followersInfoActivityAdapter = new FollowersInfoActivityAdapter(tweetsList);
        recyclerView.setAdapter(followersInfoActivityAdapter);
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public Context getContext() {
        return FollowersInfoActivity.this;
    }

    /**
     * dataListenerLoaded is a callback that is fired once a successive response is returned from user_timeline API
     * this callback is called inside FollowersInfoViewModel initialized method
     *
     * @param object          that is returned from successive response of API
     * @param isPullToRefresh illustrates if this request that is returned from FollowersInfoViewModel comes from initialized
     *                        method of FollowersInfoViewModel or from Pull To Refresh
     * @param isLoadMore      illustrates if this request that is returned from FollowersInfoViewModel comes from initialized
     *                        method of FollowersInfoViewModel or from Load More
     */
    @Override
    public void dataListenerLoaded(Object object, boolean isPullToRefresh, boolean isLoadMore) {
        List<Tweets> tweetsList = ((List<Tweets>) object);
        RealmList<Tweets> tweetsRealmList = new RealmList<>();

        followersInfoActivityAdapter = (FollowersInfoActivityAdapter)
                activityFollowersInfoBinding.activityFollowersInfoRecyclerView.getAdapter();

        for (int i = 0; i < tweetsList.size(); i++) {
            this.tweetsList.add(tweetsList.get(i));
            /**
             * a workaround to save a list of tweets in Realm Schema since it has to be of type RealmList
             * so we make a RealmList "tweetsRealmList" and add inside it each tweet object
             * and than pass it to Realm Schema
             */
            tweetsRealmList.add(tweetsList.get(i));
            followersInfoActivityAdapter.notifyItemInserted(this.tweetsList.size());
        }
        followersInfoActivityAdapter.notifyDataSetChanged();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new UserTweets(user.getFollowerId(), tweetsRealmList));
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
        /**
         * get show the cached data in any case of error to make the app real not lazy
         */
        UserTweets userTweets = realm.where(UserTweets.class).equalTo("userId", user.getFollowerId()).findFirst();
        if (userTweets != null) {
            followersInfoActivityAdapter = (FollowersInfoActivityAdapter)
                    activityFollowersInfoBinding.activityFollowersInfoRecyclerView.getAdapter();
            for (int i = 0; i < userTweets.getTweetsList().size(); i++) {
                this.tweetsList.add(userTweets.getTweetsList().get(i));
                followersInfoActivityAdapter.notifyItemInserted(this.tweetsList.size());
            }
            followersInfoActivityAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(FollowersInfoActivity.this, "No Followers Info are cached", Toast.LENGTH_SHORT).show();
        }

    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * make an animation after onBackPressed is fired
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    protected void onDestroy() {
        super.onDestroy();
        followersInfoViewModel.destroy();
    }
}
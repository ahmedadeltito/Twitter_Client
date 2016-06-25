package com.ahmedadel.twitterclient.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.ahmedadel.twitterclient.MainApplication;
import com.ahmedadel.twitterclient.data.TwitterFactory;
import com.ahmedadel.twitterclient.model.Followers;
import com.ahmedadel.twitterclient.viewmodel.adapter.FollowersActivityAdapter;
import com.twitter.sdk.android.core.TwitterSession;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * FollowersViewModel is a ViewModel of activity_followers xml File
 *
 */
public class FollowersViewModel implements ViewModel {

    // observables can deal with activity_followers xml runtime and get
    // the updated values that occurs during real runtime interactions inside activity_followers xml file
    public ObservableInt activityFollowerProgressBar;
    public ObservableInt activityFollowerSwipeRefreshLayout;
    public ObservableInt activityFollowerRecyclerView;
    public ObservableBoolean activityFollowerIsPullToRefresh;

    private DataListener dataListener;
    private Context context;
    private TwitterSession session;

    private final Long userId;
    private String cursor = "-1";

    private FollowersActivityAdapter followersActivityAdapter;

    private Subscription subscription;

    public FollowersViewModel(@NonNull DataListener dataListener, @NonNull Context context,
                              TwitterSession session, Long userId) {
        this.dataListener = dataListener;
        this.context = context;
        this.session = session;
        this.userId = userId;
        activityFollowerProgressBar = new ObservableInt(View.VISIBLE);
        activityFollowerSwipeRefreshLayout = new ObservableInt(View.GONE);
        activityFollowerRecyclerView = new ObservableInt(View.GONE);
        activityFollowerIsPullToRefresh = new ObservableBoolean(false);
        initializeViews();
        fetchFollowersList(false, false, cursor);
    }

    private void initializeViews() {
        activityFollowerProgressBar.set(View.VISIBLE);
        activityFollowerSwipeRefreshLayout.set(View.GONE);
        activityFollowerRecyclerView.set(View.GONE);
        activityFollowerIsPullToRefresh.set(false);
    }

    public void setFollowersActivityAdapter(FollowersActivityAdapter followersActivityAdapter) {
        this.followersActivityAdapter = followersActivityAdapter;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityFollowerIsPullToRefresh.set(true);
                followersActivityAdapter.setLoaded(false);
                fetchFollowersList(true, false, cursor);
            }
        };
    }

    public void fetchFollowersList(final boolean isPullToRefresh, final boolean isLoadMore, String cursor) {
        final MainApplication mainApplication = MainApplication.getInstance().create(context);
        final TwitterFactory twitterFactory = new TwitterFactory(session);
        subscription = twitterFactory.getUserFollowers(userId, cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mainApplication.subscribeScheduler())
                .subscribe(new Action1<Followers>() {
                    @Override
                    public void call(Followers followers) {

                        activityFollowerProgressBar.set(View.GONE);
                        activityFollowerSwipeRefreshLayout.set(View.VISIBLE);
                        activityFollowerRecyclerView.set(View.VISIBLE);
                        activityFollowerIsPullToRefresh.set(false);

                        if (followers.getUsers().size() > 0) {

                            if (isLoadMore && followersActivityAdapter != null)
                                followersActivityAdapter.setLoaded(false);

                            if (dataListener != null) {
                                dataListener.dataListenerLoaded(followers, isPullToRefresh, isLoadMore);
                            }
                        } else {
                            Toast.makeText(context, "No More Followers", Toast.LENGTH_SHORT).show();
                            if (isLoadMore && followersActivityAdapter != null)
                                followersActivityAdapter.setLoaded(true);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        activityFollowerProgressBar.set(View.GONE);
                        activityFollowerSwipeRefreshLayout.set(View.VISIBLE);
                        activityFollowerRecyclerView.set(View.VISIBLE);
                        activityFollowerIsPullToRefresh.set(false);
                        if (dataListener != null) {
                            dataListener.errorListenerLoaded(throwable.getMessage());
                        }
                    }
                });
    }

    @BindingAdapter({"pullToRefresh"})
    public static void cancelPullToRefresh(View view, boolean isPullToRefresh) {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view;
        if (!isPullToRefresh) {
            swipeRefreshLayout.setRefreshing(isPullToRefresh);
        }
    }

    @Override
    public void destroy() {
        reset();
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void reset() {
        unSubscribeFromObservable();
        subscription = null;
        context = null;
        dataListener = null;
    }
}

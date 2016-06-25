package com.ahmedadel.twitterclient.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.ahmedadel.twitterclient.MainApplication;
import com.ahmedadel.twitterclient.data.TwitterFactory;
import com.ahmedadel.twitterclient.model.Tweets;
import com.ahmedadel.twitterclient.model.User;
import com.twitter.sdk.android.core.Session;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * FollowersInfoViewModel is a ViewModel of activity_followers_info xml File
 *
 */
public class FollowersInfoViewModel implements ViewModel {

    private final User user;

    // activityFollowersInfoRecyclerView is an observable that can deal with activity_followers_info xml runtime and get
    // the updated values that occurs during real runtime interactions inside activity_followers_info xml file
    public ObservableInt activityFollowersInfoRecyclerView;

    private DataListener dataListener;
    private Context context;
    private Session session;

    private Subscription subscription;

    public FollowersInfoViewModel(@NonNull DataListener dataListener, @NonNull Context context,
                                  Session session, User user) {
        this.dataListener = dataListener;
        this.context = context;
        this.session = session;
        this.user = user;

        activityFollowersInfoRecyclerView = new ObservableInt(View.GONE);

        initializeViews();
        fetchTweetsList();
    }

    public void initializeViews() {
        activityFollowersInfoRecyclerView.set(View.GONE);
    }

    private void fetchTweetsList() {
        MainApplication mainApplication = MainApplication.getInstance().create(context);
        final TwitterFactory twitterFactory = new TwitterFactory(session);
        subscription = twitterFactory.getUserTweets(Long.valueOf(user.getFollowerId()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(mainApplication.subscribeScheduler())
                .subscribe(new Action1<List<Tweets>>() {
                    @Override
                    public void call(List<Tweets> tweetsList) {
                        activityFollowersInfoRecyclerView.set(View.VISIBLE);

                        if (dataListener != null) {
                            dataListener.dataListenerLoaded(tweetsList, false, false);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        activityFollowersInfoRecyclerView.set(View.VISIBLE);
                        if (dataListener != null) {
                            dataListener.errorListenerLoaded(throwable.getMessage());
                        }
                    }
                });
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

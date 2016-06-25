package com.ahmedadel.twitterclient.data;

import com.ahmedadel.twitterclient.model.Followers;
import com.ahmedadel.twitterclient.model.Tweets;
import com.twitter.sdk.android.core.Session;

import java.util.List;

import rx.Observable;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * TwitterFactory is a factory class that we can call from it follower/list and user_timeline Twitter API
 *
 */
public class TwitterFactory {

    public static final int COUNT = 10;
    public static final String SCREEN_NAME = "twitterdev";
    public static final boolean SKIP_STATUS = true;
    public static final boolean INCLUDE_USER_ENTITIES = false;

    private TwitterClient twitterClient;

    public TwitterFactory(Session session) {
        twitterClient = new TwitterClient(session);
        twitterClient.getTwitterService();
    }

    /**
     * getUserFollowers() is a method that get follower/list of logged user
     * @param userId of the user that we need his/her follower list
     * @param cursor causes the results to be broken into pages. If no cursor is provided, a value of -1 will be assumed, which is the first “page.”
     * @return Observable of type Followers Model that has follower/list of logged user
     */
    public Observable<Followers> getUserFollowers(Long userId, String cursor) {
        return twitterClient.getTwitterService().showFollowersList(userId, SCREEN_NAME, SKIP_STATUS, INCLUDE_USER_ENTITIES, COUNT, cursor);
    }

    /**
     * getUserTweets() is a method that get user_timeline tweets of specific user
     * @param userId of the user that we need his/her user_timeline tweets
     * @return Observable of type List<Tweets> Model that contain all tweets of this user
     */
    public Observable<List<Tweets>> getUserTweets(Long userId) {
        return twitterClient.getTwitterService().showTweetsList(userId, SCREEN_NAME, COUNT);
    }
}

package com.ahmedadel.twitterclient.data;

import com.ahmedadel.twitterclient.model.Followers;
import com.ahmedadel.twitterclient.model.Tweets;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * TwitterService is an interface that contains header methods of the follower/list and user_timeline Twitter API requests
 * Since these header methods are used inside TwitterClient and passed to getService() Twitter SDK method
 *
 */
public interface TwitterService {

    @GET("/1.1/followers/list.json")
    Observable<Followers> showFollowersList(@Query("user_id") Long userId, @Query("screen_name") String
            screenName, @Query("skip_status") Boolean skipStatus,
                                            @Query("include_user_entities") Boolean includeUserEntities,
                                            @Query("count") Integer count, @Query("cursor") String cursor);

    @GET("/1.1/statuses/user_timeline.json")
    Observable<List<Tweets>> showTweetsList(@Query("user_id") Long userId, @Query("screen_name") String
            screenName, @Query("count") Integer count);
}

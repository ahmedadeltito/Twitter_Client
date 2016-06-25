package com.ahmedadel.twitterclient.data;

import android.content.Context;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * TwitterClient is a class that extends TwitterApiClient and it will be used to start Twitter Session and initialize
 * Twitter SDK session to be able to call any Twitter API request we will need
 *
 */
public class TwitterClient extends TwitterApiClient {

    /**
     * Must be instantiated after {@link TwitterCore} has been
     * initialized via {@link Fabric#with(Context, Kit[])}.
     *
     * @param session Session to be used to create the API calls.
     * @throws IllegalArgumentException if TwitterSession argument is null
     */
    public TwitterClient(Session session) {
        super(session);
    }

    public TwitterService getTwitterService() {
        return getService(TwitterService.class);
    }

}

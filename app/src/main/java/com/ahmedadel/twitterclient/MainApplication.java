package com.ahmedadel.twitterclient;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ahmedadel on 25/06/16.
 * <p/>
 * Create MainApplication class that handles and initializes some important configuration once the application is started
 */
public class MainApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Cv1CFYOT7EfzahJRPMprhFBQA";
    private static final String TWITTER_SECRET = "wFaYNYLlgZjEacWpHUNcCy8pFUpSxYyXYf1QWWl70Ll75PLE5y";

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

    }
}

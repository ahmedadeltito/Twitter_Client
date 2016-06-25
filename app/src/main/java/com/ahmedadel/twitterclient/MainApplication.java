package com.ahmedadel.twitterclient;

import android.app.Application;
import android.content.Context;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * Create MainApplication class that handles and initializes some important configuration once the application is started
 */
public class MainApplication extends Application {

    // Note: Out consumer key and secret should be obfuscated in our source code before shipping.
    private static final String TWITTER_KEY = "biYk719rCHFQzBqYZgPCDOeNZ";
    private static final String TWITTER_SECRET = "u5q8uh2mkqy4LAzC3jLxNdZY8wlyWBFO3W7AAZR3ZvexlFgB6R";

    private static MainApplication singleton;

    private Scheduler scheduler;

    private Realm realm;

    public static MainApplication getInstance() {
        if (singleton == null) {
            singleton = new MainApplication();
            return singleton;
        } else {
            return singleton;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();
    }

    private MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public MainApplication create(Context context) {
        return MainApplication.getInstance().get(context);
    }

    public Realm getRealm() {
        return realm;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) scheduler = Schedulers.io();

        return scheduler;
    }
}

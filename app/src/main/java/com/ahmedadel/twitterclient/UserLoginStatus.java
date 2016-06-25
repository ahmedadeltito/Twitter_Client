package com.ahmedadel.twitterclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ahmedadel.twitterclient.view.FollowersActivity;
import com.ahmedadel.twitterclient.view.LoginActivity;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * UserLoginStatus is a UserManager Class that save user login status to be checked every time SplashScreen is opened
 *
 */
public class UserLoginStatus {

    private SharedPreferences userLoginStatusSharedPreferences;
    final String USER_LOGIN_STATUS_PREFERENCE = "UserLoginStatusPreference";
    private final String USER_LOGIN_STATUS_EDITOR = "UserLoginStatusEditor";

    static UserLoginStatus userLoginStatus;

    private Activity activity;

    public static UserLoginStatus getInstance(Activity activity) {
        if (userLoginStatus == null) {
            userLoginStatus = new UserLoginStatus(activity);
            return userLoginStatus;
        } else {
            return userLoginStatus;
        }
    }

    public UserLoginStatus(Activity activity) {
        this.activity = activity;
        userLoginStatusSharedPreferences = activity.getApplicationContext().getSharedPreferences(USER_LOGIN_STATUS_PREFERENCE, 0);
    }

    public void userLoginStatus(boolean userLoginStatus) {
        SharedPreferences.Editor userLoginStatusEditor = userLoginStatusSharedPreferences.edit();
        userLoginStatusEditor.putBoolean(USER_LOGIN_STATUS_EDITOR, userLoginStatus);
        userLoginStatusEditor.apply();
    }

    public void checkLogin() {
        if (!this.isUserLoggedIn()) {
            Intent followersActivityIntent = new Intent(activity, LoginActivity.class);
            followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(followersActivityIntent);
            activity.finish();
        } else {
            Intent followersActivityIntent = new Intent(activity, FollowersActivity.class);
            followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            followersActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(followersActivityIntent);
            activity.finish();
        }
    }

    public boolean isUserLoggedIn() {
        return userLoginStatusSharedPreferences.getBoolean(USER_LOGIN_STATUS_EDITOR, false);
    }

}

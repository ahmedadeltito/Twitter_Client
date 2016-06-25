package com.ahmedadel.twitterclient.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedadel.twitterclient.ApplicationStatus;
import com.ahmedadel.twitterclient.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * LoginActivity is a view that responsible for log in to Twitter Client via Twitter SDK Login
 * No data banding here, the old fashioned is applied to get notice about the difference between two both models
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TwitterAuthClient twitterAuthClient;
    private ApplicationStatus applicationStatus;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize twitterAuthClient and applicationStatus that will be used to set loginStatus
        twitterAuthClient = new TwitterAuthClient();
        applicationStatus = ApplicationStatus.getInstance(LoginActivity.this);
        //----------------------------------------------------------------------------------------------------------//

        // initialize views in the old fashioned technique
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_login_app_toolbar);
        Button loginButton = (Button) findViewById(R.id.activity_login_button);
        //----------------------------------------------------------------------------------------------------------//

        // check if toolbar is not null then initialize its text view
        if (toolbar != null) {
            TextView toolbarTextView = (TextView) toolbar.findViewById(R.id.activity_login_toolbar_title);
            toolbarTextView.setText(getResources().getString(R.string.login_in));
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }
        //----------------------------------------------------------------------------------------------------------//

        // check if button is not null to fire Twitter SDK login listener
        if (loginButton != null)
            loginButton.setOnClickListener(this);
        //----------------------------------------------------------------------------------------------------------//

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // this line helps Twitter SDK to handle a lot of cases when it navigate to Twitter Mobile Application or
        // twitter webpage to get your approval about this twitter client application
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }
    //----------------------------------------------------------------------------------------------------------//

    /**
     * twitterLogin() method is responsible for authorize twitterAuthClient instance, since it has too callback one for
     * success and the other for failer
     */
    private void twitterLogin() {
        twitterAuthClient.authorize(LoginActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(LoginActivity.this, "Welcome " + result.data.getUserName(), Toast.LENGTH_SHORT).show();
                applicationStatus.userLoginStatus(true);

                // just show a loading progress bar to make the user feel the UX of application not to navigate him/her
                // directly to the FollowersActivity
                showLoading();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        applicationStatus.checkLogin();
                        LoginActivity.this.finish();
                    }
                }, 2000);
            }

            @Override
            public void failure(TwitterException exception) {
                assert exception.getMessage() != null;
                Toast.makeText(LoginActivity.this, "Error : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //----------------------------------------------------------------------------------------------------------//

    private void showLoading() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public void onClick(View view) {
        twitterLogin();
    }
    //----------------------------------------------------------------------------------------------------------//
}

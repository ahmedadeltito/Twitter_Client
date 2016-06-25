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

import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.UserLoginStatus;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView toolbarTextView;
    private Button loginButton;

    private TwitterAuthClient twitterAuthClient;
    private UserLoginStatus userLoginStatus;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        twitterAuthClient = new TwitterAuthClient();
        userLoginStatus = UserLoginStatus.getInstance(LoginActivity.this);

        toolbar = (Toolbar) findViewById(R.id.activity_login_app_toolbar);
        loginButton = (Button) findViewById(R.id.activity_login_button);

        if (toolbar != null) {
            toolbarTextView = (TextView) toolbar.findViewById(R.id.activity_login_toolbar_title);
            toolbarTextView.setText(getResources().getString(R.string.login_in));
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }

        if (loginButton != null)
            loginButton.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    private void twitterLogin() {
        twitterAuthClient.authorize(LoginActivity.this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(LoginActivity.this, "Welcome " + result.data.getUserName(), Toast.LENGTH_SHORT).show();
                userLoginStatus.userLoginStatus(true);

                showLoading();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        userLoginStatus.checkLogin();
                        progressDialog.dismiss();
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

    private void showLoading() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void onClick(View view) {
        twitterLogin();
    }
}

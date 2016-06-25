package com.ahmedadel.twitterclient.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.UserLoginStatus;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {

    private ImageView imageView;

    private Animation fadeInTextViewAnimation, fadeInImageViewAnimation;
    private UserLoginStatus userLoginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        userLoginStatus = UserLoginStatus.getInstance(SplashScreen.this);

        fadeInTextViewAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeInImageViewAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        imageView = (ImageView) findViewById(R.id.activity_splash_screen_image_view);
        TextView textView = (TextView) findViewById(R.id.activity_splash_screen_text_view);

        if (imageView != null && textView != null) {
            textView.startAnimation(fadeInTextViewAnimation);
            imageView.setVisibility(View.VISIBLE);
        }

        fadeInTextViewAnimation.setAnimationListener(this);
        fadeInImageViewAnimation.setAnimationListener(this);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation.equals(fadeInTextViewAnimation)) {
            imageView.startAnimation(fadeInImageViewAnimation);
        }
        if (animation.equals(fadeInImageViewAnimation)) {
            userLoginStatus.checkLogin();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

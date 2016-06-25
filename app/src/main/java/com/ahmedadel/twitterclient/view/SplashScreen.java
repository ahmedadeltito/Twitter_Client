package com.ahmedadel.twitterclient.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.ApplicationStatus;

/**
 *
 * Created by ahmedadel on 24/06/16.
 *
 * SplashScreen is the main entry of the application that it shows a simple animation and after finishing this animation
 * it is going to check if the user is first time to open the application or he/she logged in before
 * No data banding here, the old fashioned is applied to get notice about the difference between two both models
 */

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {

    private ImageView imageView;

    private Animation fadeInTextViewAnimation, fadeInImageViewAnimation;
    private ApplicationStatus applicationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // initialize applicationStatus to check if user is logged in before r not
        applicationStatus = ApplicationStatus.getInstance(SplashScreen.this);
        //----------------------------------------------------------------------------------------------------------//

        // initialize fade in animation that will be used for text view and image view
        fadeInTextViewAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeInImageViewAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        //----------------------------------------------------------------------------------------------------------//

        // initialize views in the old fashioned technique
        imageView = (ImageView) findViewById(R.id.activity_splash_screen_image_view);
        TextView textView = (TextView) findViewById(R.id.activity_splash_screen_text_view);
        if (imageView != null && textView != null) {
            textView.startAnimation(fadeInTextViewAnimation);
            imageView.setVisibility(View.VISIBLE);
        }
        //----------------------------------------------------------------------------------------------------------//

        // set listeners of animations to track them once the first one is finished the second one has to start and so on
        fadeInTextViewAnimation.setAnimationListener(this);
        fadeInImageViewAnimation.setAnimationListener(this);
        //----------------------------------------------------------------------------------------------------------//

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation.equals(fadeInTextViewAnimation)) {
            imageView.startAnimation(fadeInImageViewAnimation);
        }
        if (animation.equals(fadeInImageViewAnimation)) {
            applicationStatus.checkLogin();
        }
    }
    //----------------------------------------------------------------------------------------------------------//

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    //----------------------------------------------------------------------------------------------------------//
}

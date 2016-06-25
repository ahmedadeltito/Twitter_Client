package com.ahmedadel.twitterclient.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.model.User;
import com.ahmedadel.twitterclient.view.FollowersActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * FollowersItemListViewModel is a ViewModel of followers_item_list xml File
 *
 */
public class FollowersItemListViewModel extends BaseObservable {

    private User user;
    private Context context;

    public FollowersItemListViewModel(User user, Context context) {
        this.user = user;
        this.context = context;
    }

    public String getName() {
        return user.getFollowerFullName();
    }

    public String getScreenName() {
        return context.getString(R.string.screen_name, user.getFollowerScreenName());
    }

    public String getDescription() {
        return context.getString(R.string.bio, user.getFollowerDesc().equals("") ? "No User Biography" : user.getFollowerDesc());
    }

    public String getProfileImage() {
        return user.getFollowerProfileImage();
    }

    public void onItemClick(View view) {
        FollowersActivity.newIntent(user);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).error(R.drawable.ic_signin_twitter)
                .placeholder(R.mipmap.ic_launcher).into(view);
    }

    public void setUser(User user) {
        this.user = user;
        notifyChange();
    }
}

package com.ahmedadel.twitterclient.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.ahmedadel.twitterclient.R;
import com.ahmedadel.twitterclient.model.Tweets;

/**
 * Created by ahmedadel on 24/06/16.
 *
 * FollowersInfoItemListViewModel is a ViewModel of followers_info_item_list xml File
 *
 */
public class FollowersInfoItemListViewModel extends BaseObservable {

    private Tweets tweets;
    private Context context;

    public FollowersInfoItemListViewModel(Tweets tweets, Context context) {
        this.tweets = tweets;
        this.context = context;
    }

    public String getTweet() {
        return tweets.getText();
    }

    public String getRetweetCounts() {
        return context.getString(R.string.retweets_count, tweets.getRetweetCount());
    }

    public String getCreatedAt() {
        return tweets.getCreatedAt().split(" ")[0] + " " + tweets.getCreatedAt().split(" ")[1] + " " + tweets.getCreatedAt().split(" ")[2];
    }

    public void setTweets(Tweets tweets) {
        this.tweets = tweets;
        notifyChange();
    }
}


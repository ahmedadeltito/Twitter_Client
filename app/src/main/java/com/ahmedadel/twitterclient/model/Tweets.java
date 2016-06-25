package com.ahmedadel.twitterclient.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.TweetsRealmProxy;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * Tweets Model that will be user in Retrofit Requests and Realm Database
 *
 */

@RealmClass
@Parcel(implementations = {TweetsRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {Tweets.class})
public class Tweets extends RealmObject {

    @PrimaryKey
    public int tweetsPrimaryKey;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("text")
    public String text;
    @SerializedName("retweet_count")
    public int retweetCount;

    public Tweets() {

    }

    public Tweets(String createdAt, String text, int retweetCount) {
        this.createdAt = createdAt;
        this.text = text;
        this.retweetCount = retweetCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getTweetsPrimaryKey() {
        return tweetsPrimaryKey;
    }

    public void setTweetsPrimaryKey(int tweetsPrimaryKey) {
        this.tweetsPrimaryKey = tweetsPrimaryKey;
    }
}

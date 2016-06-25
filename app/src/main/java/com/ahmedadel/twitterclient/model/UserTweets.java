package com.ahmedadel.twitterclient.model;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserTweetsRealmProxy;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * UserTweets Model that will be user in Retrofit Requests and Realm Database.
 * This model is not blueprint of returned response of user_timeline Twitter API request since we made this model to be
 * like a layer that contains the ID of specific user and his/her tweets list to be user inside Realm ORM Database Library
 * and be easily to make operations on them
 */

@RealmClass
@Parcel(implementations = {UserTweetsRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {UserTweets.class})
public class UserTweets extends RealmObject {

    @PrimaryKey
    public String userId;

    @ParcelPropertyConverter(RealmListParcelConverter.class)
    public RealmList<Tweets> tweetsList;

    public UserTweets() {

    }

    public UserTweets(String userId, RealmList<Tweets> tweetsList) {
        this.userId = userId;
        this.tweetsList = tweetsList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RealmList<Tweets> getTweetsList() {
        return tweetsList;
    }
}

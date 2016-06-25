package com.ahmedadel.twitterclient.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.FollowersRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * Followers Model that will be user in Retrofit Requests and Realm Database
 */

@RealmClass
@Parcel(implementations = {FollowersRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {Followers.class})
public class Followers extends RealmObject {

    @PrimaryKey
    public int followerPrimaryKey;

    /**
     * Since Realm can't make operations on fields of type List<> so we make its type RealmList and convert the returned list
     * from Retrofit response to be readable for Realm ORM Database Library
     **/
    @ParcelPropertyConverter(RealmListParcelConverter.class)
    @SerializedName("users")
    public RealmList<User> users;

    @SerializedName("next_cursor")
    public String nextCursor;

    @SerializedName("previous_cursor")
    public String previousCursor;

    public Followers() {

    }

    public Followers(RealmList<User> users, String nextCursor, String previousCursor) {
        this.users = users;
        this.nextCursor = nextCursor;
        this.previousCursor = previousCursor;
    }

    public RealmList<User> getUsers() {
        return users;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public String getPreviousCursor() {
        return previousCursor;
    }

    public int getFollowerPrimaryKey() {
        return followerPrimaryKey;
    }

    public void setFollowerPrimaryKey(int followerPrimaryKey) {
        this.followerPrimaryKey = followerPrimaryKey;
    }
}

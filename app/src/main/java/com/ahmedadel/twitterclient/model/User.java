package com.ahmedadel.twitterclient.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UserRealmProxy;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * User Model that will be user in Retrofit Requests and Realm Database
 *
 */

@RealmClass
@Parcel(implementations = {UserRealmProxy.class},value = Parcel.Serialization.BEAN, analyze = { User.class })
public class User extends RealmObject {

    @PrimaryKey
    @SerializedName("id_str")
    public String followerId;
    @SerializedName("profile_image_url")
    public String followerProfileImage;
    @SerializedName("profile_background_image_url")
    public String followerBackgroundImage;
    @SerializedName("name")
    public String followerFullName;
    @SerializedName("screen_name")
    public String followerScreenName;
    @SerializedName("description")
    public String followerDesc;

    public User() {
    }

    public User(String followerId, String followerProfileImage, String followerBackgroundImage, String followerFullName, String followerScreenName, String followerDesc) {
        this.followerId = followerId;
        this.followerProfileImage = followerProfileImage;
        this.followerBackgroundImage = followerBackgroundImage;
        this.followerFullName = followerFullName;
        this.followerScreenName = followerScreenName;
        this.followerDesc = followerDesc;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowerProfileImage() {
        return followerProfileImage;
    }

    public void setFollowerProfileImage(String followerProfileImage) {
        this.followerProfileImage = followerProfileImage;
    }

    public String getFollowerBackgroundImage() {
        return followerBackgroundImage;
    }

    public void setFollowerBackgroundImage(String followerBackgroundImage) {
        this.followerBackgroundImage = followerBackgroundImage;
    }

    public String getFollowerFullName() {
        return followerFullName;
    }

    public void setFollowerFullName(String followerFullName) {
        this.followerFullName = followerFullName;
    }

    public String getFollowerScreenName() {
        return followerScreenName;
    }

    public void setFollowerScreenName(String followerScreenName) {
        this.followerScreenName = followerScreenName;
    }

    public String getFollowerDesc() {
        return followerDesc;
    }

    public void setFollowerDesc(String followerDesc) {
        this.followerDesc = followerDesc;
    }

}

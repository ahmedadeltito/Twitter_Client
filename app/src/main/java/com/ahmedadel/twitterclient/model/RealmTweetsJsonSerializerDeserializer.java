package com.ahmedadel.twitterclient.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import io.realm.RealmList;

/**
 * Created by ahmedadel on 25/06/16.
 *
 * RealmTweetsJsonSerializerDeserializer is a class for serialize and deserialize Json Object (Tweets) that is returned
 * from Retrofit response to be used as a RealmObject and make a lot of operations on it
 *
 * RealmTweetsJsonSerializerDeserializer is better to use JsonSerializer and JsonDeserializer
 * rather than TypeAdapter for your RealmObject (Tweets)
 * because :
 * 1. They allow you to delegate (de)serialization for your RealmObject (Tweets) to the default Gson (de)serializer,
 * which means you don't need to write the boilerplate yourself.
 * 2. There's a weird bug in Gson 2.3.1 that might cause a StackOverflowError during deserialization
 * (I tried the TypeAdapter approach myself and encountered this bug).
 *
 */
public class RealmTweetsJsonSerializerDeserializer implements JsonSerializer<RealmList<Tweets>>,
        JsonDeserializer<RealmList<Tweets>> {

    @Override
    public JsonElement serialize(RealmList<Tweets> src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        for (Tweets tweet : src) {
            ja.add(context.serialize(tweet));
        }
        return ja;
    }

    @Override
    public RealmList<Tweets> deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context)
            throws JsonParseException {
        RealmList<Tweets> tweets = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();
        for (JsonElement je : ja) {
            tweets.add((Tweets) context.deserialize(je, Tweets.class));
        }
        return tweets;
    }

}
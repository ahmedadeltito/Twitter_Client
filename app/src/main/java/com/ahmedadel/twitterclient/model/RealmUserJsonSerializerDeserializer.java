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
 * RealmUserJsonSerializerDeserializer is a class for serialize and deserialize Json Object (User) that is returned
 * from Retrofit response to be used as a RealmObject and make a lot of operations on it
 *
 * RealmUserJsonSerializerDeserializer is better to use JsonSerializer and JsonDeserializer
 * rather than TypeAdapter for your RealmObject (User)
 * because :
 * 1. They allow you to delegate (de)serialization for your RealmObject (User) to the default Gson (de)serializer,
 * which means you don't need to write the boilerplate yourself.
 * 2. There's a weird bug in Gson 2.3.1 that might cause a StackOverflowError during deserialization
 * (I tried the TypeAdapter approach myself and encountered this bug).
 *
 */
public class RealmUserJsonSerializerDeserializer implements JsonSerializer<RealmList<User>>,
        JsonDeserializer<RealmList<User>> {

    @Override
    public JsonElement serialize(RealmList<User> src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonArray ja = new JsonArray();
        for (User user : src) {
            ja.add(context.serialize(user));
        }
        return ja;
    }

    @Override
    public RealmList<User> deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context)
            throws JsonParseException {
        RealmList<User> users = new RealmList<>();
        JsonArray ja = json.getAsJsonArray();
        for (JsonElement je : ja) {
            users.add((User) context.deserialize(je, User.class));
        }
        return users;
    }

}

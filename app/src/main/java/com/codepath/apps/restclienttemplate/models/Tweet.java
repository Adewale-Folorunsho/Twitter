package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String image;


    // the empty constructor is needed by parcel
    public Tweet(){};

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        if(jsonObject.has("full_text")){
            tweet.body =  jsonObject.getString("full_text");
        } else {
            tweet.body = jsonObject.getString("text");
        }

        JSONObject entities = jsonObject.getJSONObject("entities");
        if(entities.has("media")){
            JSONArray imageArray = entities.getJSONArray("media");
            Log.i("reached", "got an image");
            JSONObject firstItemInMediaArray = (JSONObject) imageArray.get(0);
            tweet.image = firstItemInMediaArray.getString("media_url_https");
        }

        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}

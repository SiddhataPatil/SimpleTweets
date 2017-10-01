package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by siddhatapatil on 10/1/17.
 */

public class User {
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;

    public static User fromJSON (JSONObject json) throws JSONException{
        User user = new User();
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("profil_image_url");
        user.profileImageUrl = json.getString("profile_image_url");
        return user;
    }
}

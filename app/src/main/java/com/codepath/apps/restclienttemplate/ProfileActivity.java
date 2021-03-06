package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.Fragment.UserFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String otherScreenName = getIntent().getStringExtra("NAME");
        long userID = getIntent().getLongExtra("userID", 20);
        // get the screen name from the intent
        String screenName = getIntent().getStringExtra("screen_name");
        UserFragment userFragment = UserFragment.newInstance(screenName);
        // display the user timeline fragment inside the container dynamically
        FragmentManager ft = getSupportFragmentManager();
        ft.beginTransaction();
        //replace(R.id.flContainer, userFragment).commit();

        client = TwitterApp.getRestClient();

        // calling own profile
        if (otherScreenName == null) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    // deserialize the User object
                    try {
                        User user = User.fromJSON(response);

                        // set the title of the ACtionBar based on the user info
                        getSupportActionBar().setTitle(user.screenName);

                        // populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            client.getOtherUserTimeline(String.valueOf(userID), otherScreenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    // deserialize the User object
                    try {
                        User user = User.fromJSON(response);

                        // set the title of the ActionBar based on the user info
                        getSupportActionBar().setTitle(user.screenName);

                        // populate the user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void populateUserHeadline(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingCount + " Following");

        // load profile image with Glide
        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);
    }
}
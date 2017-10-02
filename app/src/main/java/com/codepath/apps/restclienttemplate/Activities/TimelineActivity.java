package com.codepath.apps.restclienttemplate.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.Adapter.PagerAdapter;
import com.codepath.apps.restclienttemplate.Adapter.TweetAdapter;
import com.codepath.apps.restclienttemplate.Fragment.TweetListFragment;
import com.codepath.apps.restclienttemplate.ProfileActivity;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class TimelineActivity extends AppCompatActivity {

    TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    final int REQUEST_CODE = 20;
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get access to Twitter client
        //client = TwitterApp.getRestClient();

        // find the RecyclerView
        // rvTweets = (RecyclerView)findViewById(R.id.rvTweet);

        // init the arrayList (data source)
        // tweets = new ArrayList<>();

        // construct the adapter from this data source
        //tweetAdapter = new TweetAdapter(tweets,this);

        // RecyclerView setup (layout manager, use adapter)
        // rvTweets.setLayoutManager(new LinearLayoutManager(this));

        // set the adapter
        // rvTweets.setAdapter(tweetAdapter);

        // populateTimeline();
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), this));
        // set the adapter for the pager
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }

    private void populateTimeline() {

        // make the network request to get data from Twitter API
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                // Log.d("TwitterClient", response.toString());

                // iterate through the JSON array

                // for each entry, deserialize the JSON object
                for (int i = 0; i < response.length(); i++) {

                    // convert each object to a Tweet model
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));

                        // add that Tweet model to our data source
                        tweets.add(tweet);

                        // notify the adapter that we've added an item
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    public void onProfileView(MenuItem item) {
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract tweet from result extras

            // the new tweet we just made
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0,tweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
        }
    }
}

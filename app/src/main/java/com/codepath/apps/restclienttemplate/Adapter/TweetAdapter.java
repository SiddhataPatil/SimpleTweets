package com.codepath.apps.restclienttemplate.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.Fragment.TweetListFragment;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by siddhatapatil on 10/1/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private TweetAdapterListener mListener;


    public interface TweetAdapterListener {
        public void onItemSelected(View view, int position);
    }

    // pass the Tweets array into the Constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener) {
        mTweets = tweets;
        mListener = listener;
    }

    // for each row, inflate the layout and cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // get the data according to position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create ViewHolder class


    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvRelativeTime;

        public ViewHolder(View itemView) {

            super(itemView);

            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvRelativeTime = (TextView) itemView.findViewById(R.id.tvRelativeTime);

        }
    }


    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}


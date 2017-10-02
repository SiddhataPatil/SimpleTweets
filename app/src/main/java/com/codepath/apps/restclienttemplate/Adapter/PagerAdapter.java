package com.codepath.apps.restclienttemplate.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
import com.codepath.apps.restclienttemplate.Fragment.HomeFragment;
import com.codepath.apps.restclienttemplate.Fragment.MentionsFragment;

/**
 * Created by siddhatapatil on 10/1/17.
 */

public class PagerAdapter extends SmartFragmentPageAdapter {

    // instance variables
    private String tabTitles[] = new String[]{"Home", "Mentions"};
    private Context context;

    // Constructor
    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    // return the total # of fragments
    @Override
    public int getCount() {
        return 2;
    }

    // return the fragment to use depending on the position
    @Override
    public Fragment getItem(int position) {
        if (position != 0) {
            return new Fragment();
        } else {
            return null;
        }
    }

    // return title
    @Override
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tabTitles[position];
    }
}
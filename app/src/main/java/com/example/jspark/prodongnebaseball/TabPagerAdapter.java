package com.example.jspark.prodongnebaseball;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jspark on 2017-11-18.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount ;



    public TabPagerAdapter(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                BlankFragment tabFragment1 = new BlankFragment();
                return tabFragment1;
            case 1:
                BlankFragment2 tabFragment2 = new BlankFragment2();
                return tabFragment2;
            case 2:
                BlankFragment3 tabFragment3 = new BlankFragment3();
                return tabFragment3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

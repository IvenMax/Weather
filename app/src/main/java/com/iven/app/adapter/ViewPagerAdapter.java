package com.iven.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iven
 * @date 2017/3/1 20:25
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private ArrayList<String> titles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
        titles = new ArrayList<>();
        titles.add("天气");
        titles.add("走势");
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        int size = mFragments.size();
        return null != mFragments ? size : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

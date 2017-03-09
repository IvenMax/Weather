package com.iven.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Iven
 * @date 2017/3/7 22:23
 */

public class NewsFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "zpy_MyPagerAdapter";
    private List<String> titles;
    private List<Fragment> fragments;
    private LayoutInflater mInflater;

    public NewsFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return null != fragments ? fragments.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }
}

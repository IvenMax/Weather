package com.iven.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iven
 * @date 2017/3/7 22:23
 */

public class NewsFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private ArrayList<String> titles;

    public NewsFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
        titles = new ArrayList<>();
        titles.add("头条");
        titles.add("财经");
        titles.add("军事");
        titles.add("体育");
        titles.add("娱乐");
        titles.add("笑话");
        titles.add("游戏");
        titles.add("时尚");
        titles.add("旅游");
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

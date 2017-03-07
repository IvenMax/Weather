package com.iven.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iven.app.R;
import com.iven.app.adapter.NewsFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iven
 * @date 2017/2/21 14:43
 * @Description 新闻页
 */

public class NewsFragment extends Fragment {
    private TabLayout mTabs;
    private ViewPager view_pager;
    private NewsFragmentPagerAdapter mNewsFragmentPagerAdapter;
    private List<Fragment> mFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_news, container, false);
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(View view) {
        mTabs = (TabLayout) view.findViewById(R.id.tabs);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        mFragments = new ArrayList<>();
        mNewsFragmentPagerAdapter = new NewsFragmentPagerAdapter(getFragmentManager(), mFragments);
        view_pager.setAdapter(mNewsFragmentPagerAdapter);
        mTabs.setupWithViewPager(view_pager);
    }
}

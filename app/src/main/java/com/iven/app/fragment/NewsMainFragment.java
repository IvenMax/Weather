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

public class NewsMainFragment extends Fragment {
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private NewsFragmentPagerAdapter adapter;
    private List<Fragment> mFragments;
    private List<String> titles;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_news_main, container, false);
        init(view);
        initOther();
        return view;
    }

    private void initOther() {
        titles = new ArrayList<>();
        titles.add("头条");
        titles.add("娱乐");
        titles.add("体育");
        titles.add("财经");
        titles.add("科技");
        titles.add("电影");
        titles.add("汽车");
        titles.add("笑话");
        titles.add("游戏");
        titles.add("时尚");
        titles.add("情感");
        titles.add("精选");
        titles.add("数码");
        titles.add("彩票");
        titles.add("教育");
        titles.add("旅游");
        titles.add("手机");
        titles.add("社会");
        titles.add("家居");
        mFragments = new ArrayList<>();
        int size = titles.size();//为什么提出来呢？嗯
        for (int i = 0; i < size; i++) {
            NewsFragment testFrament = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", titles.get(i));
            testFrament.setArguments(bundle);
            mFragments.add(testFrament);
        }
        adapter = new NewsFragmentPagerAdapter(getChildFragmentManager(), mFragments, titles);

        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);//可滑动
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(View view) {
        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
    }
}

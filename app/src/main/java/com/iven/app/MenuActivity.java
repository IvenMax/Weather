package com.iven.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iven.app.base.BaseActivity;
import com.iven.app.bean.HistoryOfTodayBean;
import com.iven.app.fragment.NewsFragment;
import com.iven.app.fragment.ThirdFragment;
import com.iven.app.fragment.WeatherFragment;
import com.iven.app.utils.Api;
import com.iven.app.utils.ScreenObserver;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MenuActivity extends BaseActivity {
    private static final String TAG = "zpy_MenuActivity";
    private NavigationView navigation_view;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ArrayList<Fragment> mFragmentArrayList;
    private FragmentManager manager;
    private int currentPage = 0;
    private WeatherFragment mWeatherFragment;
    private NewsFragment mNewsFragment;
    private ThirdFragment mThirdFragment;
    private FloatingActionButton floating_button;
    /**
     * 锁屏监听
     */
    public ScreenObserver mScreenObserver;

    @Override
    public int setLayout() {
        return R.layout.activity_menu;
    }

    @Override
    public void setTitle() {
        title_title.setText("看见");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.editext_tip), null, null, null);
        title_left.setOnClickListener(this);
        title_right.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_right), null, null, null);
        title_right.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        //左侧抽屉相关
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        navigation_view.setItemIconTintList(null);//让menu里边的图片显示原始颜色
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_menu);
        //底部导航栏 && Fragment
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mFragmentArrayList = new ArrayList<>();
        manager = getFragmentManager();
        mWeatherFragment = new WeatherFragment();
        mNewsFragment = new NewsFragment();
        mThirdFragment = new ThirdFragment();
        mFragmentArrayList.add(mWeatherFragment);
        mFragmentArrayList.add(mNewsFragment);
        mFragmentArrayList.add(mThirdFragment);
        //悬浮按钮
        floating_button = (FloatingActionButton) findViewById(R.id.floating_button);
        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String loc = formatter.format(curDate);
                String time = loc.substring(loc.length() - 6, loc.length());
                String historyYear = loc.substring(0, 4);
                String historyMonth = loc.substring(5, 7);
                String historyDay = loc.substring(8, 10);
                http_history(historyMonth, historyDay);
            }
        });

        initTab();
        //左侧抽屉的内容的点击事件
        itemClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**-----锁屏监听-----**/
        mScreenObserver = new ScreenObserver(this);
        mScreenObserver.requestScreenStateUpdate(new ScreenObserver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
            }

            @Override
            public void onScreenOff() {
            }
        });
        /**-----锁屏监听-----**/
    }

    /**
     * 查询历史上的几天的事件
     */
    private void http_history(String month, String day) {
        String url = "month=" + month + "&day=" + day;
        OkGo.get(Api.HISTORY_OF_TODAY + url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                HistoryOfTodayBean historyOfTodayBean = gson.fromJson(s, HistoryOfTodayBean.class);
                List<HistoryOfTodayBean.ResultBean> result = historyOfTodayBean.getResult();
                Log.e(TAG, "onSuccess: 114" + "行 = " +result.size());
            }
        });

    }

    /**
     * 初始化底部导航栏
     */
    private void initTab() {
        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText("常规"));
        mTabLayout.addTab(mTabLayout.newTab().setText("新闻"));
        mTabLayout.addTab(mTabLayout.newTab().setText("设置"));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        Fragment fragment = mFragmentArrayList.get(currentPage);
                        switchContent(fragment, mFragmentArrayList.get(0), "first");
                        currentPage = 0;
                        break;
                    case 1:
                        Fragment fragment1 = mFragmentArrayList.get(currentPage);
                        switchContent(fragment1, mFragmentArrayList.get(1), "second");
                        currentPage = 1;
                        break;
                    case 2:
                        Fragment fragment2 = mFragmentArrayList.get(currentPage);
                        switchContent(fragment2, mFragmentArrayList.get(2), "third");
                        currentPage = 2;
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                if (!mDrawerLayout.isDrawerOpen(navigation_view)) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }
                break;
            case R.id.title_right:
                Toast.makeText(this, "todo", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    /**
     * Item点击事件处理
     */
    private void itemClick() {
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_01:
                        mTabLayout.getTabAt(0).select();
                        break;
                    case R.id.menu_02:
                        mTabLayout.getTabAt(1).select();
                        break;
                    case R.id.menu_03:
                        mTabLayout.getTabAt(2).select();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    /**
     * Fragmen切换
     *
     * @param from 切换前所显示的
     * @param to   切换的目标
     * @param tag  tag
     */
    public void switchContent(Fragment from, Fragment to, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (null == from || !from.isAdded()) {
            transaction.add(R.id.md_container, to, tag).commitAllowingStateLoss();
            return;
        }
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.md_container, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchContent(null, mFragmentArrayList.get(0), "first");
    }
}

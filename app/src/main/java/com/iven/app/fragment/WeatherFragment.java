package com.iven.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.iven.app.MyApp;
import com.iven.app.R;
import com.iven.app.activity.WeatherSuggestionActivity;
import com.iven.app.adapter.ViewPagerAdapter;
import com.iven.app.bean.DailyForecastBean;
import com.iven.app.bean.TotalWeatherBean;
import com.iven.app.okgo.JsonCallback;
import com.iven.app.utils.Api;
import com.iven.app.utils.Constant;
import com.iven.app.utils.IconSetting;
import com.iven.app.utils.NewLoadingUtil;
import com.iven.app.utils.RexUtils;
import com.iven.app.utils.T;
import com.iven.app.utils.VibrationUtils;
import com.iven.app.view.NoScrollViewPager;
import com.iven.app.view.PullToRefreshLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author Iven
 * @date 2017/2/21 14:42
 * @Description
 */
// TODO: 2017/2/27 添加网络检测
public class WeatherFragment extends Fragment implements View.OnClickListener {
    //刷新相关
    private PullToRefreshLayout mPullToRefreshView;
    private static final String TAG = "zpy_WeatherFragment";
    //现在温度,最高温度,最低温度,天气描述,空气指数,更新时间,风向风力,
    private TextView tv_now_tmp, tv_tmp_hight, tv_tmp_low, tv_tmp_txt, tv_zhishu, tv_update_time, tv_wind_size, tv_pm25, tv_hum;
    private ImageView iv_tmp_logo;
    private NewLoadingUtil mNewLoadingUtil;
    private ProgressBar progressBar;
    private ScrollView scrl_view_weather;
    private ImageView mImageView;
    private ArrayList<DailyForecastBean> mDailyForecastBeanArrayList;
    private ArrayList<TotalWeatherBean.HeWeather5Bean.HourlyForecastBean> mHourlyForecastBeanArrayList;
    private List<Fragment> mFragments;
    private NoScrollViewPager vp_noviewpager;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout tablayout_vp;
    private LinearLayout ll_hourly;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_weather, container, false);
        init(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        http_request(MyApp.currentCity);
    }

    //实例化组件
    private void init(View view) {
        tv_now_tmp = (TextView) view.findViewById(R.id.tv_now_tmp);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tv_tmp_hight = (TextView) view.findViewById(R.id.tv_tmp_hight);
        tv_tmp_low = (TextView) view.findViewById(R.id.tv_tmp_low);
        tv_tmp_txt = (TextView) view.findViewById(R.id.tv_tmp_txt);
        tv_zhishu = (TextView) view.findViewById(R.id.tv_zhishu);
        tv_update_time = (TextView) view.findViewById(R.id.tv_update_time);
        tv_wind_size = (TextView) view.findViewById(R.id.tv_wind_size);
        tv_pm25 = (TextView) view.findViewById(R.id.tv_pm25);
        tv_hum = (TextView) view.findViewById(R.id.tv_hum);
        iv_tmp_logo = (ImageView) view.findViewById(R.id.iv_tmp_logo);
        scrl_view_weather = (ScrollView) view.findViewById(R.id.scrl_view_weather);
        mDailyForecastBeanArrayList = new ArrayList<>();
        mHourlyForecastBeanArrayList = new ArrayList<>();
        initPullToRefreshLayout(view);
        /***********中间ViewPager相关**********/
        mFragments = new ArrayList<>();
        mFragments.add(new Left7DaysFragment());
        mFragments.add(new Right7DaysFragment());
        vp_noviewpager = (NoScrollViewPager) view.findViewById(R.id.vp_noviewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), mFragments);
        vp_noviewpager.setAdapter(mViewPagerAdapter);
        tablayout_vp = (TabLayout) view.findViewById(R.id.tablayout_vp);
        tablayout_vp.addTab(tablayout_vp.newTab().setText("列表"));
        tablayout_vp.addTab(tablayout_vp.newTab().setText("趋势"));
        /**
         * 此处有坑。。。setupWithViewPager方法会自动将Tab清空...
         * //tablayout_vp.getTabAt(0).setText("");
         * or
         * //在适配器里边重写getPageTitle()方法返回新的Tab的名字
         */
        tablayout_vp.setupWithViewPager(vp_noviewpager);
        /***********中间ViewPager相关**********/
        ll_hourly = (LinearLayout) view.findViewById(R.id.ll_hourly);
        setClickListener(view);
    }

    /**
     * 全部信息请求
     *
     * @param city 城市
     */
    private void http_request(String city) {
        mNewLoadingUtil = NewLoadingUtil.getInstance(getActivity());
        Log.e(TAG, "http_request: 83" + "行 = " + Api.HEWEATHER5_WEATHER + city);
        OkGo.get(Api.HEWEATHER5_WEATHER + city).execute(new JsonCallback<TotalWeatherBean>() {
            @Override
            public void onSuccess(TotalWeatherBean totalWeatherBean, Call call, Response response) {
                List<TotalWeatherBean.HeWeather5Bean> heWeather5 = totalWeatherBean.getHeWeather5();
                TotalWeatherBean.HeWeather5Bean heWeather5Bean = heWeather5.get(0);
                List<TotalWeatherBean.HeWeather5Bean.DailyForecastBean> daily_forecast = heWeather5Bean.getDaily_forecast();
                int size = daily_forecast.size();
                for (int i = 0; i < size; i++) {
                    TotalWeatherBean.HeWeather5Bean.DailyForecastBean dailyForecastBean = daily_forecast.get(i);
                    DailyForecastBean bean = new DailyForecastBean();
                    bean.setDate(dailyForecastBean.getDate());
                    bean.setCode_d(dailyForecastBean.getCond().getCode_d());
                    bean.setCode_n(dailyForecastBean.getCond().getCode_n());
                    bean.setTxt_d(dailyForecastBean.getCond().getTxt_d());
                    bean.setTxt_n(dailyForecastBean.getCond().getTxt_n());
                    bean.setMax(dailyForecastBean.getTmp().getMax());
                    bean.setMin(dailyForecastBean.getTmp().getMin());
                    bean.setWind_dir(dailyForecastBean.getWind().getDir());
                    bean.setWind_spd(dailyForecastBean.getWind().getSpd());
                    mDailyForecastBeanArrayList.add(bean);
                }
                mHourlyForecastBeanArrayList.clear();//每次刷新的时候，先将原来的集合清空
                mHourlyForecastBeanArrayList.addAll(heWeather5Bean.getHourly_forecast());
                setHourlyData();
                setData(heWeather5Bean);
                T.showLong(getActivity(), "更新完成");
            }


            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                mNewLoadingUtil.startShowLoading();
            }

            @Override
            public void onAfter(TotalWeatherBean totalWeatherBean, Exception e) {
                super.onAfter(totalWeatherBean, e);
                mNewLoadingUtil.stopShowLoading();
                VibrationUtils.vibrate(getActivity(), 100);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                T.showLong(getActivity(), "更新失败");
            }
        });
    }

    /**
     * 实时天气展示
     */
    private void setHourlyData() {
        int size = mHourlyForecastBeanArrayList.size();
        ll_hourly.removeAllViews();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_item_hourly, null, false);
                TextView tv_hour_time = (TextView) view.findViewById(R.id.tv_hour_time);
                TextView tv_hour_temp = (TextView) view.findViewById(R.id.tv_hour_temp);
                TextView tv_hour_wind = (TextView) view.findViewById(R.id.tv_hour_wind);
                TextView tv_hour_wind_dir = (TextView) view.findViewById(R.id.tv_hour_wind_dir);
                tv_hour_time.setText(String.format("时间:%s", mHourlyForecastBeanArrayList.get(i).getDate().substring(mHourlyForecastBeanArrayList.get(i).getDate().length() - 5, mHourlyForecastBeanArrayList.get(i).getDate().length())));
                tv_hour_temp.setText(String.format("温度:%s℃", mHourlyForecastBeanArrayList.get(i).getTmp()));
                if (RexUtils.isMatch(RexUtils.REGEX_ZH, mHourlyForecastBeanArrayList.get(i).getWind().getSc())) {
                    tv_hour_wind.setText(String.format("风力:%s", mHourlyForecastBeanArrayList.get(i).getWind().getSc()));
                } else {
                    tv_hour_wind.setText(String.format("风力:%s级", mHourlyForecastBeanArrayList.get(i).getWind().getSc()));
                }
                tv_hour_wind_dir.setText(String.format("风向:%s", mHourlyForecastBeanArrayList.get(i).getWind().getDir()));
                ll_hourly.addView(view);
            }
        } else {//夜间22点之后应该没有实时数据了
            TextView textView = new TextView(getActivity());
            textView.setText("今日已无实时数据");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setGravity(Gravity.CENTER);
            ll_hourly.addView(textView, params);
        }


    }

    /**
     * 初始化下拉刷新
     */
    private void initPullToRefreshLayout(View view) {
        //-------------------------下拉刷新
        mPullToRefreshView = (PullToRefreshLayout) view.findViewById(R.id.pull_to_refresh);
        createRefreshView();
        mPullToRefreshView.setChildView(scrl_view_weather);//将ListView添加到Layout中去

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.setRefreshing(false);
                http_request(MyApp.currentCity);
            }

            @Override
            public void onDragDistanceChange(float distance, float percent, float offset) {
                mPullToRefreshView.setRefreshing(false);
            }

            @Override
            public void onFinish() {
            }
        });
        //-------------------------下拉刷新
    }

    public View createRefreshView() {
        View headerView = mPullToRefreshView.setRefreshView(R.layout.layout_head);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        mImageView = (ImageView) headerView.findViewById(R.id.text_view);
        //        LoadingUtils.startLoading(mImageView);
        progressBar.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.GONE);

        //		imageView = (ImageView) headerView.findViewById(R.id.image_view);
        //		imageView.setVisibility(View.VISIBLE);
        //		imageView.setImageResource(R.mipmap.drawable_arrow);
        //		progressBar.setVisibility(View.GONE);
        return headerView;
    }

    /**
     * 显示数据
     */
    private void setData(TotalWeatherBean.HeWeather5Bean heWeather5Bean) {
        tv_now_tmp.setText(String.format("%s℃", heWeather5Bean.getNow().getTmp()));
        tv_tmp_hight.setText(String.format("%s℃", heWeather5Bean.getDaily_forecast().get(0).getTmp().getMax()));
        tv_tmp_low.setText(String.format("%s℃", heWeather5Bean.getDaily_forecast().get(0).getTmp().getMin()));
        tv_tmp_txt.setText(heWeather5Bean.getNow().getCond().getTxt());
        tv_zhishu.setText("空气指数 : " + heWeather5Bean.getAqi().getCity().getAqi() + " | " + heWeather5Bean.getAqi().getCity().getQlty());
        Picasso.with(getActivity()).load(IconSetting.getIconUrl(heWeather5Bean.getNow().getCond().getCode())).into(iv_tmp_logo);
        tv_wind_size.setText(String.format("%s : %s级", heWeather5Bean.getNow().getWind().getDir(), heWeather5Bean.getNow().getWind().getSc()));
        tv_pm25.setText(String.format("PM2.5 : %sμg/m³", heWeather5Bean.getAqi().getCity().getPm25()));
        tv_hum.setText(String.format("空气湿度 : %s%%", heWeather5Bean.getNow().getHum()));
        String loc = heWeather5Bean.getBasic().getUpdate().getLoc();
        //2017-02-22 12:52
        String time = loc.substring(loc.length() - 6, loc.length());
        String historyYear = loc.substring(0, 4);
        String historyMonth = loc.substring(5, 7);
        tv_update_time.setText(String.format("更新时间 : %s", time));


    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 生活指数相关
     *
     * @param view
     */
    private void setClickListener(View view) {
        RelativeLayout rl_suggestion_comf = (RelativeLayout) view.findViewById(R.id.rl_suggestion_comf);
        RelativeLayout rl_suggestion_cw = (RelativeLayout) view.findViewById(R.id.rl_suggestion_cw);
        RelativeLayout rl_suggestion_drsg = (RelativeLayout) view.findViewById(R.id.rl_suggestion_drsg);
        RelativeLayout rl_suggestion_flu = (RelativeLayout) view.findViewById(R.id.rl_suggestion_flu);
        RelativeLayout rl_suggestion_sport = (RelativeLayout) view.findViewById(R.id.rl_suggestion_sport);
        RelativeLayout rl_suggestion_trav = (RelativeLayout) view.findViewById(R.id.rl_suggestion_trav);
        RelativeLayout rl_suggestion_uv = (RelativeLayout) view.findViewById(R.id.rl_suggestion_uv);
        RelativeLayout rl_suggestion_life = (RelativeLayout) view.findViewById(R.id.rl_suggestion_life);
        rl_suggestion_comf.setOnClickListener(this);
        rl_suggestion_cw.setOnClickListener(this);
        rl_suggestion_drsg.setOnClickListener(this);
        rl_suggestion_flu.setOnClickListener(this);
        rl_suggestion_sport.setOnClickListener(this);
        rl_suggestion_trav.setOnClickListener(this);
        rl_suggestion_uv.setOnClickListener(this);
        rl_suggestion_life.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), WeatherSuggestionActivity.class);
        switch (v.getId()) {
            case R.id.rl_suggestion_comf://第一个
                intent.putExtra(Constant.SUGGESTION_FLAG, 1);
                break;
            case R.id.rl_suggestion_cw://第2个
                intent.putExtra(Constant.SUGGESTION_FLAG, 2);
                break;
            case R.id.rl_suggestion_drsg://第3个
                intent.putExtra(Constant.SUGGESTION_FLAG, 3);
                break;
            case R.id.rl_suggestion_flu://第4个
                intent.putExtra(Constant.SUGGESTION_FLAG, 4);
                break;
            case R.id.rl_suggestion_sport://第5个
                intent.putExtra(Constant.SUGGESTION_FLAG, 5);
                break;
            case R.id.rl_suggestion_trav://第6个
                intent.putExtra(Constant.SUGGESTION_FLAG, 6);
                break;
            case R.id.rl_suggestion_uv://第7个
                intent.putExtra(Constant.SUGGESTION_FLAG, 7);
                break;
            case R.id.rl_suggestion_life://第8个
                intent.putExtra(Constant.SUGGESTION_FLAG, 8);
                break;
        }
        startActivity(intent);
    }

    /**
     * 选择七日天气的TabLayout的控制器
     */
    private class onPagerTabSelectedListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}

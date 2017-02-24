package com.iven.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iven.app.MyApp;
import com.iven.app.R;
import com.iven.app.bean.TotalWeatherBean;
import com.iven.app.utils.Api;
import com.iven.app.utils.IconSetting;
import com.iven.app.utils.NewLoadingUtil;
import com.iven.app.utils.T;
import com.iven.app.utils.VibrationUtils;
import com.iven.app.view.PullToRefreshLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author Iven
 * @date 2017/2/21 14:42
 * @Description
 */

public class WeatherFragment extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_weather, container, false);
        tv_now_tmp = (TextView) view.findViewById(R.id.tv_now_tmp);
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
        initPullToRefreshLayout(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        http_request(MyApp.currentCity);
    }

    private void http_request(String city) {
        mNewLoadingUtil = NewLoadingUtil.getInstance(getActivity());
        OkGo.get(Api.HEWEATHER5_WEATHER + city).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                TotalWeatherBean totalWeatherBean = gson.fromJson(s, TotalWeatherBean.class);
                List<TotalWeatherBean.HeWeather5Bean> heWeather5 = totalWeatherBean.getHeWeather5();
                TotalWeatherBean.HeWeather5Bean heWeather5Bean = heWeather5.get(0);
                setData(heWeather5Bean);
                T.showLong(getActivity(), "更新完成");
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                mNewLoadingUtil.startShowLoading();
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                mNewLoadingUtil.stopShowLoading();
                VibrationUtils.vibrate(getActivity(),100);
            }
        });
    }

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
}

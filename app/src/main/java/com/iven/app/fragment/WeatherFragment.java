package com.iven.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iven.app.MyApp;
import com.iven.app.R;
import com.iven.app.bean.TotalWeatherBean;
import com.iven.app.utils.Api;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author Iven
 * @date 2017/2/21 14:42
 * @Description
 */

public class WeatherFragment extends Fragment {
    private static final String TAG = "zpy_WeatherFragment";
    //现在温度,最高温度,最低温度,天气描述,空气指数,更新时间,风向风力,
    private TextView tv_now_tmp, tv_tmp_hight, tv_tmp_low,tv_tmp_txt, tv_zhishu, tv_update_time, tv_wind_size, tv_pm25, tv_hum;
    private ImageView iv_tmp_logo;

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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        http_request(MyApp.currentCity);
    }

    private void http_request(String city) {
        OkGo.get(Api.HEWEATHER5_WEATHER + city).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                TotalWeatherBean totalWeatherBean = gson.fromJson(s, TotalWeatherBean.class);
                List<TotalWeatherBean.HeWeather5Bean> heWeather5 = totalWeatherBean.getHeWeather5();
                TotalWeatherBean.HeWeather5Bean heWeather5Bean = heWeather5.get(0);
                setData(heWeather5Bean);
            }
        });
    }

    /**
     * 显示数据
     */
    private void setData(TotalWeatherBean.HeWeather5Bean heWeather5Bean) {
        tv_now_tmp.setText(String.format("%s℃", heWeather5Bean.getNow().getTmp()));
        tv_tmp_hight.setText(String.format("%s℃", heWeather5Bean.getDaily_forecast().get(0).getTmp().getMax()));
        tv_tmp_low.setText(String.format("%s℃", heWeather5Bean.getDaily_forecast().get(0).getTmp().getMin()));
        tv_tmp_txt.setText(heWeather5Bean.getNow().getCond().getTxt());
    }
}

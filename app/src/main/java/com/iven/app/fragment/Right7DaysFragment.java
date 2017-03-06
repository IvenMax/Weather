package com.iven.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iven.app.MyApp;
import com.iven.app.R;
import com.iven.app.bean.SevenDaysBean;
import com.iven.app.okgo.JsonCallback;
import com.iven.app.utils.Api;
import com.iven.app.utils.T;
import com.iven.app.view.weatherview.WeatherItemView;
import com.iven.app.view.weatherview.ZzWeatherView;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author Iven
 * @date 2017/3/1 17:14
 * @Description
 */

public class Right7DaysFragment extends Fragment {
    private static final String TAG = "zpy_Right7DaysFragment";
    public ArrayList<SevenDaysBean.HeWeather5Bean.DailyForecastBean> mDailyForecastBeanArrayList;
    private ZzWeatherView weather_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_right7days, null, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mDailyForecastBeanArrayList = new ArrayList<>();
        weather_view = ((ZzWeatherView) view.findViewById(R.id.weather_view));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        http(MyApp.currentCity);
    }

    private void http(String city) {
        OkGo.get(Api.HEWEATHER5_FORECAST + city).execute(new JsonCallback<SevenDaysBean>() {

            @Override
            public void onSuccess(SevenDaysBean sevenDaysBean, Call call, Response response) {
                List<SevenDaysBean.HeWeather5Bean.DailyForecastBean> daily_forecast = sevenDaysBean.getHeWeather5().get(0).getDaily_forecast();
                Log.e(TAG, "onSuccess: 108" + "行 = daily_forecast.size()" + daily_forecast.size());
                mDailyForecastBeanArrayList.addAll(daily_forecast);
                weather_view.setList(mDailyForecastBeanArrayList);
                weather_view.setLineType(ZzWeatherView.LINE_TYPE_DISCOUNT);
                weather_view.setLineWidth(6f);

                //点击某一列
                weather_view.setOnWeatherItemClickListener(new ZzWeatherView.OnWeatherItemClickListener() {
                    @Override
                    public void onItemClick(WeatherItemView itemView, int position, SevenDaysBean.HeWeather5Bean.DailyForecastBean dailyForecastBean) {
//                        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                T.showShort(getActivity().getApplicationContext(), e.getMessage());
            }
        });
    }
}

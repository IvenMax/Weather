package com.iven.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iven.app.MyApp;
import com.iven.app.R;
import com.iven.app.adapter.Left7DaysAdapter;
import com.iven.app.bean.SevenDaysBean;
import com.iven.app.okgo.JsonCallback;
import com.iven.app.utils.Api;
import com.iven.app.utils.T;
import com.iven.app.view.NoScrollViewPager;
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

public class Left7DaysFragment extends Fragment {
    private static final String TAG = "zpy_Left7DaysFragment";
    private RecyclerView hor_recyclerview;//横向的RecyclerView
    private Left7DaysAdapter adapter;
    private NoScrollViewPager mNoScrollViewPager;
    private ArrayList<SevenDaysBean.HeWeather5Bean.DailyForecastBean> mDailyForecastBeanArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_left7days, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        hor_recyclerview = ((RecyclerView) view.findViewById(R.id.hor_recyclerview));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hor_recyclerview.setLayoutManager(linearLayoutManager);
        //        hor_recyclerview.setItemAnimator(new DefaultItemAnimator());
        mDailyForecastBeanArrayList = new ArrayList<>();
        adapter = new Left7DaysAdapter(mDailyForecastBeanArrayList, getActivity());
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("iiiii" + i);
        }
        hor_recyclerview.setAdapter(adapter);
        //        hor_recyclerview.setAdapter(new TestAdapter(list,getActivity()));
        mNoScrollViewPager = (NoScrollViewPager) getActivity().findViewById(R.id.vp_noviewpager);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        http(MyApp.currentCity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 七日预报
     */
    private void http(String city) {
        OkGo.get(Api.HEWEATHER5_FORECAST + city).execute(new JsonCallback<SevenDaysBean>() {
            @Override
            public void onSuccess(SevenDaysBean sevenDaysBean, Call call, Response response) {
                List<SevenDaysBean.HeWeather5Bean.DailyForecastBean> daily_forecast = sevenDaysBean.getHeWeather5().get(0).getDaily_forecast();
                Log.e(TAG, "onSuccess: 108" + "行 = daily_forecast.size()" + daily_forecast.size());
                mDailyForecastBeanArrayList.addAll(daily_forecast);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                T.showShort(getActivity().getApplicationContext(), e.getMessage());
            }
        });
    }

}

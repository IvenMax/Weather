package com.iven.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.TotalWeatherBean;
import com.iven.app.utils.IconSetting;
import com.iven.app.view.WeatherLineView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeaDataAdapter extends RecyclerView.Adapter<WeaDataAdapter.WeatherDataViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TotalWeatherBean.HeWeather5Bean.DailyForecastBean> datas;
    private int mLowestTem;
    private int mHighestTem;

    public WeaDataAdapter(Context context, ArrayList<TotalWeatherBean.HeWeather5Bean.DailyForecastBean> datats, int lowtem, int hightem) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.datas = datats;
        mLowestTem = lowtem;
        mHighestTem = hightem;
    }

    @Override
    public WeatherDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item, parent, false);
        WeatherDataViewHolder viewHolder = new WeatherDataViewHolder(view);
        viewHolder.dayText = (TextView) view.findViewById(R.id.id_day_text_tv);
        viewHolder.dayIcon = (ImageView) view.findViewById(R.id.id_day_icon_iv);
        viewHolder.weatherLineView = (WeatherLineView) view.findViewById(R.id.wea_line);
        viewHolder.nighticon = (ImageView) view.findViewById(R.id.id_night_icon_iv);
        viewHolder.nightText = (TextView) view.findViewById(R.id.id_night_text_tv);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherDataViewHolder holder, int position) {
        // 最低温度设置为15，最高温度设置为30
        Resources resources = mContext.getResources();
        TotalWeatherBean.HeWeather5Bean.DailyForecastBean dailyForecastBean = datas.get(position);
        holder.dayText.setText(dailyForecastBean.getCond().getTxt_d());
        String code_d = dailyForecastBean.getCond().getCode_d();
        if (!TextUtils.isEmpty(code_d)) {
            Picasso.with(mContext).load(IconSetting.getIconUrl(code_d)).into(holder.dayIcon);
        } else {
            holder.dayIcon.setVisibility(View.INVISIBLE);
        }
        String code_n = dailyForecastBean.getCond().getCode_n();
        if (!TextUtils.isEmpty(code_n)) {
            Picasso.with(mContext).load(IconSetting.getIconUrl(code_d)).into(holder.nighticon);
        } else {
            holder.nighticon.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(dailyForecastBean.getCond().getTxt_d())) {
            holder.dayText.setText(dailyForecastBean.getCond().getTxt_d());
        }
        if (!TextUtils.isEmpty(dailyForecastBean.getCond().getTxt_n())) {
            holder.nightText.setText(dailyForecastBean.getCond().getTxt_n());
        }
        holder.weatherLineView.setLowHighestData(mLowestTem, mHighestTem);//设置最高最低温度
        int low[] = new int[3];
        int high[] = new int[3];
        low[1] = Integer.parseInt(dailyForecastBean.getTmp().getMin());
        high[1] = Integer.parseInt(dailyForecastBean.getTmp().getMax());
        if (position <= 0) {
            low[0] = 0;
            high[0] = 0;
        } else {
            TotalWeatherBean.HeWeather5Bean.DailyForecastBean dailyForecastBean1 = datas.get(position - 1);
            low[0] = (Integer.parseInt(dailyForecastBean1.getTmp().getMin()) + Integer.parseInt(dailyForecastBean1.getTmp().getMin())) / 2;
            high[0] = (Integer.parseInt(dailyForecastBean1.getTmp().getMax()) + Integer.parseInt(dailyForecastBean1.getTmp().getMax())) / 2;
        }
        if (position >= datas.size() - 1) {
            low[2] = 0;
            high[2] = 0;
        } else {
            TotalWeatherBean.HeWeather5Bean.DailyForecastBean weatherModelRight = datas.get(position + 1);
            low[2] = (Integer.parseInt(weatherModelRight.getTmp().getMin()) + Integer.parseInt(weatherModelRight.getTmp().getMin())) / 2;
            high[2] = (Integer.parseInt(weatherModelRight.getTmp().getMax()) + Integer.parseInt(weatherModelRight.getTmp().getMax())) / 2;
        }
        holder.weatherLineView.setLowHighData(low, high);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class WeatherDataViewHolder extends RecyclerView.ViewHolder {

        TextView dayText;
        ImageView dayIcon;
        WeatherLineView weatherLineView;
        ImageView nighticon;
        TextView nightText;

        public WeatherDataViewHolder(View itemView) {
            super(itemView);
        }
    }
}
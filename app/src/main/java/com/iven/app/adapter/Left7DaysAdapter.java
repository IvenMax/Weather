package com.iven.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.SevenDaysBean;
import com.iven.app.utils.IconSetting;
import com.iven.app.utils.RexUtils;
import com.iven.app.utils.SpannableStrings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Iven
 * @date 2017/3/2 12:35
 * @Description
 */

public class Left7DaysAdapter extends RecyclerView.Adapter<Left7DaysAdapter.MyViewHolder> {
    private ArrayList<SevenDaysBean.HeWeather5Bean.DailyForecastBean> datas;
    private static final String TAG = "zpy_Left7DaysAdapter";
    private Context mContext;

    public Left7DaysAdapter(ArrayList<SevenDaysBean.HeWeather5Bean.DailyForecastBean> datas, Context context) {
        this.datas = datas;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_left7days, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String date = datas.get(position).getDate();
        String txt_d = datas.get(position).getCond().getTxt_d();
        String txt_n = datas.get(position).getCond().getTxt_n();
        String code_d = datas.get(position).getCond().getCode_d();
        String code_n = datas.get(position).getCond().getCode_n();
        String dir = datas.get(position).getWind().getDir();
        if (dir.contains("无")){
            dir = "无";
        }
        if(dir.length()>3){
            dir = dir.substring(0,2);
        }
        String sc = datas.get(position).getWind().getSc();
        String max = datas.get(position).getTmp().getMax();
        String min = datas.get(position).getTmp().getMin();
        holder.tv_leftdays_time.setText(String.format("%s日", date.substring(date.length() - 2, date.length())));
        holder.tv_leftdays_desc_d.setText(txt_d);
        holder.tv_leftdays_desc_n.setText(txt_n);
        holder.tv_leftdays_wind_dir.setText(dir);
        if (RexUtils.isMatch(RexUtils.REGEX_ZH, sc)) {
            holder.tv_leftdays_wind_sc.setText(sc);
        } else {
            holder.tv_leftdays_wind_sc.setText(String.format("%s级", sc));
        }
        String temp = "11/-1℃";
//        String temp = max + "/" + min+"℃" ;//+"℃"
        SpannableString spannableString = SpannableStrings.setTextColor(temp, Color.parseColor("#F56A13"), 0, temp.indexOf("/"));
        holder.tv_leftdays_temp.setText(spannableString);
        Picasso.with(mContext).load(IconSetting.getIconUrl(code_d)).into(holder.iv_leftdays_img_d);
        Picasso.with(mContext).load(IconSetting.getIconUrl(code_n)).into(holder.iv_leftdays_img_n);
    }

    @Override
    public int getItemCount() {
        return null != datas ? datas.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_leftdays_time;
        private ImageView iv_leftdays_img_d;
        private ImageView iv_leftdays_img_n;
        private TextView tv_leftdays_desc_d;
        private TextView tv_leftdays_desc_n;
        private TextView tv_leftdays_temp;
        private TextView tv_leftdays_wind_dir;
        private TextView tv_leftdays_wind_sc;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_leftdays_time = (TextView) itemView.findViewById(R.id.tv_leftdays_time);
            iv_leftdays_img_d = (ImageView) itemView.findViewById(R.id.iv_leftdays_img_d);
            iv_leftdays_img_n = (ImageView) itemView.findViewById(R.id.iv_leftdays_img_n);
            tv_leftdays_desc_d = (TextView) itemView.findViewById(R.id.tv_leftdays_desc_d);
            tv_leftdays_desc_n = (TextView) itemView.findViewById(R.id.tv_leftdays_desc_n);
            tv_leftdays_temp = (TextView) itemView.findViewById(R.id.tv_leftdays_temp);
            tv_leftdays_wind_dir = (TextView) itemView.findViewById(R.id.tv_leftdays_wind_dir);
            tv_leftdays_wind_sc = (TextView) itemView.findViewById(R.id.tv_leftdays_wind_sc);
        }
    }

}

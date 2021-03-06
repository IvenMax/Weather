package com.iven.app.utils;

import com.iven.app.R;

/**
 * @author Iven
 * @date 2017/2/27 17:45
 * @Description 根据天气描述，选择对应的Icon
 */

public class WeatherIconUtils {

    public static int getDayWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.drawable.w0;
            case "多云":
                return R.drawable.w1;
            case "阴":
                return R.drawable.w2;
            case "雷阵雨":
                return R.drawable.w4;
            case "雨夹雪":
                return R.drawable.w6;
            case "小雨":
                return R.drawable.w7;
            case "中雨":
                return R.drawable.w8;
            case "大雨":
                return R.drawable.w9;
            case "暴雨":
                return R.drawable.w10;
            case "大雪":
                return R.drawable.w17;
            case "中雪":
                return R.drawable.w16;
            case "冰雹":
                return R.drawable.w15;
            case "霾":
                return R.mipmap.m502;
        }
        return R.mipmap.n999;
    }

    public static int getNightWeatherPic(String weatherName) {
        switch (weatherName) {
            case "晴":
                return R.drawable.w30;
            case "多云":
                return R.drawable.w31;
            case "阴":
                return R.drawable.w2;
            case "雷阵雨":
                return R.drawable.w4;
            case "雨夹雪":
                return R.drawable.w6;
            case "小雨":
                return R.drawable.w7;
            case "中雨":
                return R.drawable.w8;
            case "大雨":
                return R.drawable.w9;
            case "暴雨":
                return R.drawable.w10;
            case "大雪":
                return R.drawable.w17;
            case "中雪":
                return R.drawable.w16;
            case "冰雹":
                return R.drawable.w15;
            case "霾":
                return R.mipmap.m502;
        }
        return R.mipmap.n999;
    }
}

package com.iven.app.bean;

/**
 * @author Iven
 * @date 2017/2/27 16:19
 * @Description
 */

public class DailyForecastBean {
    private String code_d;//白天天气代码
    private String code_n;//夜间天气代码
    private String txt_d;//白天描述
    private String txt_n;//夜间描述
    private String date;//
    private String max;//
    private String min;//
    private String wind_dir;//
    private String wind_spd;//

    public String getCode_d() {
        return code_d;
    }

    public DailyForecastBean setCode_d(String code_d) {
        this.code_d = code_d;
        return this;
    }

    public String getCode_n() {
        return code_n;
    }

    public DailyForecastBean setCode_n(String code_n) {
        this.code_n = code_n;
        return this;
    }

    public String getTxt_d() {
        return txt_d;
    }

    public DailyForecastBean setTxt_d(String txt_d) {
        this.txt_d = txt_d;
        return this;
    }

    public String getTxt_n() {
        return txt_n;
    }

    public DailyForecastBean setTxt_n(String txt_n) {
        this.txt_n = txt_n;
        return this;
    }

    public String getDate() {
        return date;
    }

    public DailyForecastBean setDate(String date) {
        this.date = date;
        return this;
    }

    public String getMax() {
        return max;
    }

    public DailyForecastBean setMax(String max) {
        this.max = max;
        return this;
    }

    public String getMin() {
        return min;
    }

    public DailyForecastBean setMin(String min) {
        this.min = min;
        return this;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public DailyForecastBean setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
        return this;
    }

    public String getWind_spd() {
        return wind_spd;
    }

    public DailyForecastBean setWind_spd(String wind_spd) {
        this.wind_spd = wind_spd;
        return this;
    }

    @Override
    public String toString() {
        return "DailyForecastBean{" +
                "code_d='" + code_d + '\'' +
                ", code_n='" + code_n + '\'' +
                ", txt_d='" + txt_d + '\'' +
                ", txt_n='" + txt_n + '\'' +
                ", date='" + date + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                '}';
    }
}

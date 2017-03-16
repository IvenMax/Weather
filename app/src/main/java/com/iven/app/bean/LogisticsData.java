package com.iven.app.bean;

import java.util.List;

/**
 * @author Iven
 * @date 2017/3/15 10:10
 * @Description
 */

public class LogisticsData {

    /**
     * message : ok
     * nu : 70069047090116
     * ischeck : 0
     * condition : 00
     * com : baishiwuliu
     * status : 200
     * state : 0
     * data : [{"time":"2017-03-15 08:29:56","ftime":"2017-03-15 08:29:56","context":"南京市|到件|到南京市【南京江北新城分部】","location":""},{"time":"2017-03-15 08:28:26","ftime":"2017-03-15 08:28:26","context":"南京市|发件|南京市【南京江北集散仓】，正发往【南京江北新城分部】","location":""},{"time":"2017-03-15 08:28:00","ftime":"2017-03-15 08:28:00","context":"南京市|到件|到南京市【南京江北集散仓】","location":""},{"time":"2017-03-15 05:06:54","ftime":"2017-03-15 05:06:54","context":"南京市|发件|南京市【南京转运中心】，正发往【南京江北集散仓】","location":""},{"time":"2017-03-15 04:39:09","ftime":"2017-03-15 04:39:09","context":"南京市|到件|到南京市【南京转运中心】","location":""},{"time":"2017-03-14 23:14:40","ftime":"2017-03-14 23:14:40","context":"杭州市|到件|到杭州市【杭州转运中心】","location":""},{"time":"2017-03-14 23:14:02","ftime":"2017-03-14 23:14:02","context":"杭州市|发件|杭州市【杭州转运中心】，正发往【南京转运中心】","location":""},{"time":"2017-03-14 20:59:29","ftime":"2017-03-14 20:59:29","context":"杭州市|发件|杭州市【余杭】，正发往【杭州转运中心】","location":""},{"time":"2017-03-14 20:19:24","ftime":"2017-03-14 20:19:24","context":"杭州市|收件|杭州市【余杭】，【郭靖/18143430455】已揽收","location":""}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2017-03-15 08:29:56
         * ftime : 2017-03-15 08:29:56
         * context : 南京市|到件|到南京市【南京江北新城分部】
         * location :
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public DataBean setTime(String time) {
            this.time = time;
            return this;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public DataBean setContext(String context) {
            this.context = context;
            return this;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "time='" + time + '\'' +
                    ", ftime='" + ftime + '\'' +
                    ", context='" + context + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }
    }
}

package com.iven.app.bean;

import java.util.List;

/**
 * @author Iven
 * @date 2017/3/2 12:44
 * @Description
 */

public class SevenDaysBean {

    private List<HeWeather5Bean> HeWeather5;

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean {
        /**
         * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","update":{"loc":"2017-03-02 11:51","utc":"2017-03-02 03:51"}}
         * daily_forecast : [{"astro":{"mr":"08:57","ms":"22:13","sr":"06:45","ss":"18:07"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2017-03-02","hum":"23","pcpn":"0.0","pop":"0","pres":"1020","tmp":{"max":"13","min":"-2"},"uv":"3","vis":"10","wind":{"deg":"228","dir":"南风","sc":"3-4","spd":"16"}},{"astro":{"mr":"09:36","ms":"23:22","sr":"06:44","ss":"18:08"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2017-03-03","hum":"31","pcpn":"0.0","pop":"0","pres":"1016","tmp":{"max":"12","min":"-2"},"uv":"3","vis":"10","wind":{"deg":"188","dir":"南风","sc":"微风","spd":"1"}},{"astro":{"mr":"10:16","ms":"null","sr":"06:42","ss":"18:09"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-03-04","hum":"40","pcpn":"0.0","pop":"0","pres":"1019","tmp":{"max":"13","min":"0"},"uv":"3","vis":"10","wind":{"deg":"166","dir":"北风","sc":"微风","spd":"7"}},{"astro":{"mr":"11:02","ms":"00:29","sr":"06:41","ss":"18:10"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2017-03-05","hum":"26","pcpn":"0.0","pop":"0","pres":"1025","tmp":{"max":"11","min":"-1"},"uv":"3","vis":"10","wind":{"deg":"333","dir":"北风","sc":"4-5","spd":"22"}},{"astro":{"mr":"11:52","ms":"01:35","sr":"06:39","ss":"18:11"},"cond":{"code_d":"100","code_n":"101","txt_d":"晴","txt_n":"多云"},"date":"2017-03-06","hum":"27","pcpn":"0.0","pop":"0","pres":"1025","tmp":{"max":"12","min":"-1"},"uv":"3","vis":"10","wind":{"deg":"312","dir":"北风","sc":"3-4","spd":"12"}},{"astro":{"mr":"12:48","ms":"02:35","sr":"06:38","ss":"18:12"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2017-03-07","hum":"26","pcpn":"0.0","pop":"0","pres":"1025","tmp":{"max":"10","min":"-1"},"uv":"-999","vis":"10","wind":{"deg":"326","dir":"西北风","sc":"3-4","spd":"10"}},{"astro":{"mr":"13:48","ms":"03:31","sr":"06:36","ss":"18:13"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2017-03-08","hum":"31","pcpn":"0.0","pop":"0","pres":"1020","tmp":{"max":"12","min":"-1"},"uv":"-999","vis":"10","wind":{"deg":"321","dir":"西北风","sc":"微风","spd":"6"}}]
         * status : ok
         */

        private BasicBean basic;
        private String status;
        private List<DailyForecastBean> daily_forecast;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public static class BasicBean {
            /**
             * city : 北京
             * cnty : 中国
             * id : CN101010100
             * lat : 39.904000
             * lon : 116.391000
             * update : {"loc":"2017-03-02 11:51","utc":"2017-03-02 03:51"}
             */

            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private UpdateBean update;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public static class UpdateBean {
                /**
                 * loc : 2017-03-02 11:51
                 * utc : 2017-03-02 03:51
                 */

                private String loc;
                private String utc;

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public static class DailyForecastBean {
            /**
             * astro : {"mr":"08:57","ms":"22:13","sr":"06:45","ss":"18:07"}
             * cond : {"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"}
             * date : 2017-03-02
             * hum : 23
             * pcpn : 0.0
             * pop : 0
             * pres : 1020
             * tmp : {"max":"13","min":"-2"}
             * uv : 3
             * vis : 10
             * wind : {"deg":"228","dir":"南风","sc":"3-4","spd":"16"}
             */

            private AstroBean astro;
            private CondBean cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            private TmpBean tmp;
            private String uv;
            private String vis;
            private WindBean wind;

            public AstroBean getAstro() {
                return astro;
            }

            public void setAstro(AstroBean astro) {
                this.astro = astro;
            }

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public TmpBean getTmp() {
                return tmp;
            }

            public void setTmp(TmpBean tmp) {
                this.tmp = tmp;
            }

            public String getUv() {
                return uv;
            }

            public void setUv(String uv) {
                this.uv = uv;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class AstroBean {
                /**
                 * mr : 08:57
                 * ms : 22:13
                 * sr : 06:45
                 * ss : 18:07
                 */

                private String mr;
                private String ms;
                private String sr;
                private String ss;

                public String getMr() {
                    return mr;
                }

                public void setMr(String mr) {
                    this.mr = mr;
                }

                public String getMs() {
                    return ms;
                }

                public void setMs(String ms) {
                    this.ms = ms;
                }

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }
            }

            public static class CondBean {
                /**
                 * code_d : 100
                 * code_n : 100
                 * txt_d : 晴
                 * txt_n : 晴
                 */

                private String code_d;
                private String code_n;
                private String txt_d;
                private String txt_n;

                public String getCode_d() {
                    return code_d;
                }

                public void setCode_d(String code_d) {
                    this.code_d = code_d;
                }

                public String getCode_n() {
                    return code_n;
                }

                public void setCode_n(String code_n) {
                    this.code_n = code_n;
                }

                public String getTxt_d() {
                    return txt_d;
                }

                public void setTxt_d(String txt_d) {
                    this.txt_d = txt_d;
                }

                public String getTxt_n() {
                    return txt_n;
                }

                public void setTxt_n(String txt_n) {
                    this.txt_n = txt_n;
                }
            }

            public static class TmpBean {
                /**
                 * max : 13
                 * min : -2
                 */

                private String max;
                private String min;

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public static class WindBean {
                /**
                 * deg : 228
                 * dir : 南风
                 * sc : 3-4
                 * spd : 16
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }
    }
}

package com.iven.app.utils;

/**
 * @author Iven
 * @date 2017/2/22 10:15
 * @Description
 */

public class IconSetting {
    public static String getIconUrl(String code) {
        String url = "http://files.heweather.com/cond_icon/";
        url = url + code + ".png";
        return url;
    }
}

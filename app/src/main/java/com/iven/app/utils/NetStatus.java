package com.iven.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

public class NetStatus {

    /**
     * 网络状态
     */

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断加载成功还是失败
     *
     * @param view
     * @param errorView
     * @param show      false 显示异常页面 true 正常页面
     */
    public static void BooleanLoadPage(View view, View errorView, boolean show) {
        if (show) {
            view.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        }
    }
}

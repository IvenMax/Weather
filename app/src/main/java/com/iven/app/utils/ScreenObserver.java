package com.iven.app.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author Iven
 * @date 2017/2/22 15:14
 * @Description 锁屏监控
 */

public class ScreenObserver {
    private static final String TAG = "zpy_ScreenObserver";
    private Context mContext;
    private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;
    private static Method mReflectScreenState;

    public ScreenObserver(Context context) {
        mContext = context;

        mScreenReceiver = new ScreenBroadcastReceiver();

        try {
            mReflectScreenState = PowerManager.class.getMethod("isScreenOn");
        } catch (NoSuchMethodException nsme) {
            Log.d(TAG, "API < 7," + nsme);
        }
    }

    /**
     * screen状态广播接收者
     *
     * @author zhangyg
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            //            if (Intent.ACTION_USER_PRESENT.equals(action)) {
            //                mScreenStateListener.onScreenOn();
            //            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            //                mScreenStateListener.onScreenOff();
            //            }
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mScreenStateListener.onScreenOff();
            }
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mScreenStateListener.onScreenOn();
            }
        }
    }

    /**
     * 请求screen状态更新
     *
     * @param listener
     */
    public void requestScreenStateUpdate(ScreenStateListener listener) {
        mScreenStateListener = listener;
        startScreenBroadcastReceiver();

        // firstGetScreenState();
    }

    /**
     * 第一次请求screen状态
     */
    private void firstGetScreenState() {
        PowerManager manager = (PowerManager) mContext.getSystemService(Activity.POWER_SERVICE);
        if (isScreenOn(manager)) {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOn();
            }
        } else {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOff();
            }
        }
    }

    /**
     * 停止screen状态更新
     */
    public void stopScreenStateUpdate() {
        try {
            if (mReceiverTag) {
                mReceiverTag = false;
                Log.i(TAG, "广播取消");
                mContext.unregisterReceiver(mScreenReceiver);
            }

            mReceiverTag = false;
        } catch (Exception e) {
            e.printStackTrace();
            mReceiverTag = false;
        }
    }

    /**
     * 启动screen状态广播接收器
     */
    private boolean mReceiverTag = false;   //广播接受者标识

    private void startScreenBroadcastReceiver() {
        if (!mReceiverTag) {
            Log.i(TAG, "广播注册");
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            filter.setPriority(1000);
            mContext.registerReceiver(mScreenReceiver, filter);
            mReceiverTag = true;
        }
    }

    /**
     * screen是否打开状态
     *
     * @param pm
     * @return
     */
    private static boolean isScreenOn(PowerManager pm) {
        boolean screenState;
        try {
            screenState = (Boolean) mReflectScreenState.invoke(pm);
        } catch (Exception e) {
            screenState = false;
        }
        return screenState;
    }

    public interface ScreenStateListener {
        void onScreenOn();

        void onScreenOff();
    }
}

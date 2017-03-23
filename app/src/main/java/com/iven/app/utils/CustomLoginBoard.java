package com.iven.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.iven.app.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 登录的面板, 一个popupWIndow
 */
public class CustomLoginBoard extends PopupWindow implements OnClickListener {
    private static final String TAG = "zpy_CustomLoginBoard";
    private Activity mActivity;
    private final UMShareAPI umShareAPI;

    /**
     * @param activity 当前Activity
     */
    public CustomLoginBoard(Activity activity) {
        this.mActivity = activity;
        initView(activity);
        umShareAPI = UMShareAPI.get(mActivity);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 14) {
            mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_pp_login, null);
        rootView.findViewById(R.id.login_weinxin).setOnClickListener(this);
        rootView.findViewById(R.id.login_sina).setOnClickListener(this);
        rootView.findViewById(R.id.login_qq).setOnClickListener(this);
        rootView.findViewById(R.id.cancleLogin).setOnClickListener(this);
        setContentView(rootView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_qq://QQ登录
                QQSSO();
                umShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.login_weinxin://微信登录
                umShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.login_sina://新浪微博登录
                umShareAPI.getPlatformInfo(mActivity, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.cancleLogin:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    private void performShare(SHARE_MEDIA platform) {
        //权限
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(mActivity, mPermissionList, 2000);
        }
    }


    /**
     * 授权侦听
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            if (null != map) {
                String uid = map.get("uid");
                Log.e(TAG, "onComplete: 197" + "行 = uid == " + uid);
                Log.e(TAG, "onComplete: 197" + "行 = name == " + map.get("name"));
                Log.e(TAG, "onComplete: 197" + "行 = gender == " + map.get("gender"));
                Log.e(TAG, "onComplete: 197" + "行 = iconurl == " + map.get("iconurl"));
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int i, Throwable t) {
            if (t != null && platform != null && (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE)) {
                T.showShort(mActivity, "您还没有安装微信");
            } else if (t != null && platform != null && platform == SHARE_MEDIA.QQ) {
                T.showShort(mActivity, "您还没有安装QQ");
            } else {
                Toast.makeText(mActivity, "登录失败啦", Toast.LENGTH_SHORT).show();
            }
            if (t != null) {
                L.e("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(mActivity, share_media + "取消授权", Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * sina授权
     */
    void sinaSSO() {
        umShareAPI.deleteOauth(mActivity, SHARE_MEDIA.SINA, umAuthListener);
    }
    void QQSSO() {
        umShareAPI.deleteOauth(mActivity, SHARE_MEDIA.QQ, umAuthListener);
    }

}

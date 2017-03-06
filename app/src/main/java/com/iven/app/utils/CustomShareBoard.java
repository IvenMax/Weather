/**
 *
 */

package com.iven.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.iven.app.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享的面板, 一个popupWIndow
 */
public class CustomShareBoard extends PopupWindow implements OnClickListener {

    private String share_title = "title";
    private String share_content = "content";
    private String share_url = "url";
    private int which_Page = -1;
    private Activity mActivity;
    private final UMImage umImage;
    private final UMShareAPI umShareAPI;
    private String sinaurl;

    /**
     * @param activity      当前Activity
     * @param which_Page    页面区分 1:ISM完成期分享, 2:个人中心邀请好友 3:其他页面
     * @param share_title   分享title
     * @param share_url     分享url
     * @param share_content 分享正文
     */
    public CustomShareBoard(Activity activity, int which_Page, String share_title, String share_url, String share_content) {
        this.share_title = share_title;
        this.share_url = share_url;
        this.share_content = share_content;
        this.which_Page = which_Page;
        this.mActivity = activity;
        initView(activity);
        umImage = new UMImage(mActivity, R.mipmap.ic_launcher);
        umShareAPI = UMShareAPI.get(mActivity);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 14) {
            mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_pop_share_frends, null);
        rootView.findViewById(R.id.wennxin).setOnClickListener(this);
        rootView.findViewById(R.id.wx_friends).setOnClickListener(this);
        rootView.findViewById(R.id.sina).setOnClickListener(this);
        rootView.findViewById(R.id.qq_friends).setOnClickListener(this);
        rootView.findViewById(R.id.lin_message).setOnClickListener(this);
        rootView.findViewById(R.id.tencent_wb).setOnClickListener(this);
        rootView.findViewById(R.id.cancleShare).setOnClickListener(this);
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
            case R.id.wennxin:
                performShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.wx_friends:
                //                event_id = getEventID("208001003", "402001003");
                //                performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.sina://新浪微博
                //                event_id = getEventID("208001001", "402001001");
                //                performShare(SHARE_MEDIA.SINA);
                break;
            case R.id.qq_friends:
                //                event_id = getEventID("208001004", "402001004");
                performShare(SHARE_MEDIA.QQ);

                break;
            case R.id.lin_message:
                //事件统计id
                //                event_id = getEventID("208001005", "402001005");
                //                getShortUrlFromSina();

                break;
            case R.id.tencent_wb:
                //                performShare(SHARE_MEDIA.TENCENT);
                break;
            case R.id.cancleShare:
                this.dismiss();
            default:
                break;
        }
        if (which_Page == 1 || which_Page == 2) {
            //事件统计
            //            UmsAgent.onEvent(GlobalContext.context, event_id);
        }
    }

    /***
     * 判断是完成期分享还是个人中心邀请好友
     ***/
    private String getPageId() {
        String page = "";
        switch (which_Page) {
            case 1:
                //                page = ConstantsEvent.ISM_COMPLETE_SHARE;
                break;
            case 2:
                //                page = ConstantsEvent.SHARED_FRIEND;
                break;
        }
        return page;
    }


    private void performShare(SHARE_MEDIA platform) {
        //权限
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(mActivity, mPermissionList, 2000);
        }
        ShareAction shareAction = new ShareAction(mActivity);
        shareAction.withText("分享......").withMedia(umImage);
        shareAction.setPlatform(platform).setCallback(umShareListener).share();
    }

    /**
     * 分享侦听
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            L.d("plat", "platform" + platform);
            if (!(SHARE_MEDIA.SMS == platform)) {
                Toast.makeText(mActivity, "分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null && platform != null && (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE)) {
                T.showShort(mActivity, "您还没有安装微信");
            } else if (t != null && platform != null && platform == SHARE_MEDIA.QQ) {
                T.showShort(mActivity, "您还没有安装QQ");
            } else {
                Toast.makeText(mActivity, "分享失败啦", Toast.LENGTH_SHORT).show();
            }
            if (t != null) {
                L.e("throw", "throw:" + t.getMessage());
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mActivity, "分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 授权侦听
     */
/*    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            //            Toast.makeText(mActivity, share_media + "授权成功", Toast.LENGTH_SHORT).show();

            //            if (SHARE_MEDIA.SINA.equals(share_media)) {
            //                umShareAPI.getFriend(mActivity, SHARE_MEDIA.SINA, new MyUMFriendListener());
            //            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            //            Toast.makeText(mActivity, "onError", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            //            Toast.makeText(mActivity, share_media + "取消授权", Toast.LENGTH_SHORT).show();
        }
    };*/
    //发短信
    private void sendSMS() {
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra("sms_body", "test" + "\n" + "url_test");
        sendIntent.setType("vnd.android-dir/mms-sms");
        mActivity.startActivityForResult(sendIntent, 1002);
    }

    /**
     * sina授权
     */
    void sinaSSO() {
        //        umShareAPI.deleteOauth(mActivity, SHARE_MEDIA.SINA, umAuthListener);
    }

    /**
     * 获取 短链接从新浪
     */
/*    void getShortUrlFromSina() {
        String encodeUrl = URLEncoder.encode(share_url);
        String params = "source=2012969184&url_long=" + encodeUrl;

        new OkHttpRequest.Builder().url(ConstantValue.SINA_SHORTURL + params).get(new MyDefaultCallback<String>(mActivity, false) {
            @Override
            public void onError(Request request, Exception e) {
                //Log.e("TAG", "onError , e = " + e.getMessage());
                T.showShort(mActivity, "分享失败");
            }

            @Override
            public void onResponse(String response, String message) {

                Gson gson = new Gson();
                SinaShortUrlBean sinaShortUrlBean = gson.fromJson(response, SinaShortUrlBean.class);


                sinaurl = sinaShortUrlBean.getUrls().get(0).getUrl_short();
                performShare(SHARE_MEDIA.SMS);
                L.e("TAG", response);
            }
        });
    }*/
}

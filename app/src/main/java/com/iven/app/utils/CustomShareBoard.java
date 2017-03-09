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
import android.util.Log;
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
import com.umeng.socialize.media.UMWeb;

/**
 * 分享的面板, 一个popupWIndow
 */
public class CustomShareBoard extends PopupWindow implements OnClickListener {
    private static final String TAG = "zpy_CustomShareBoard";
    private String share_title = "title";
    private String share_content = "content";
    private String share_url = "url";
    private int which_Page = -1;
    private Activity mActivity;
    private final UMImage umImage;
    private final UMShareAPI umShareAPI;
    private String sinaurl = "http://www.baidu.com";

    /**
     * @param activity      当前Activity
     * @param share_title   分享title
     * @param share_url     分享url
     * @param share_content 分享正文
     *                      顺序：title、content、url
     */
    public CustomShareBoard(Activity activity, String share_title, String share_content, String share_url) {
        this.share_title = share_title;
        this.share_url = share_url;
        this.share_content = share_content;
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
                performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.sina://新浪微博
                T.showShort(mActivity, "待接入...");
                //performShare(SHARE_MEDIA.SINA);
                break;
            case R.id.qq_friends:
                Log.e(TAG, "onClick: 101" + "行 = " );
                performShare(SHARE_MEDIA.QQ);
                break;
            case R.id.lin_message:
                performShare(SHARE_MEDIA.SMS);
                break;
            case R.id.cancleShare:
                this.dismiss();
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
        ShareAction shareAction = new ShareAction(mActivity);
        //设置分享的链接
        UMImage image = new UMImage(mActivity, R.mipmap.ic_share);//资源文件
        //注意在新浪平台，缩略图属于必传参数，否则会报错
        UMWeb web = new UMWeb(share_url);//分享链接
        web.setTitle(share_title);//设置分享时候的显示标题
        web.setDescription(share_content);//描述信息
        web.setThumb(image);//图片显示
        if (SHARE_MEDIA.SMS.equals(platform)) {//短信分享
            shareAction.withText(share_title.concat(sinaurl));
        } else if (SHARE_MEDIA.SINA.equals(platform)) {//新浪

        } else {
            shareAction.withMedia(web);
        }
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
            //只要并不是短信分享，都吐司提示
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

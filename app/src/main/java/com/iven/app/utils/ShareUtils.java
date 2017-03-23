package com.iven.app.utils;

import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.widget.PopupWindow;

/**
 * 分享的面板显示
 *
 * @author JinnyZh
 */
public class ShareUtils {

    private CustomShareBoard shareBoard;
    private Activity activity;
    private CustomLoginBoard mCustomLoginBoard;
    private int which_Page = -1;


    /**
     * @param activity     当前Activity
     * @param shareUrl     分享的url
     * @param shareTitle   分享的title
     * @param shareContent 分享的content
     *                     顺序：title、content、url
     */
    public ShareUtils(Activity activity, String shareTitle, String shareContent, String shareUrl) {
        this.activity = activity;
        shareBoard = new CustomShareBoard(activity, shareTitle, shareContent, shareUrl);
    }
    //登录
    public ShareUtils(Activity activity) {
        this.activity = activity;
        mCustomLoginBoard = new CustomLoginBoard(activity);
    }

    /**
     * popupWindow弹窗消失的监听
     */
    public void setDismissListener(PopupWindow.OnDismissListener OnDismissListener) {
        shareBoard.setOnDismissListener(OnDismissListener);
    }

    public void showLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCustomLoginBoard.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        }, 700);
    }
    public void show(){
        shareBoard.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //		UmsAgent.onResume(GlobalContext.context, page);
    }
}

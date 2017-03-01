package com.iven.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iven.app.R;

public class NewLoadingUtil {

    private static final String TAG = "Loading";
    private static Dialog pd;
    private AnimationDrawable loadingAnimation;
    private ImageView loading;
    private boolean showLoading = true;
    public static Activity activity;
    private LinearLayout layout;
    private String dialogTitle;
    private FrameLayout frame_loading;
    private AlphaAnimation alphaAnimation;
    private ImageView loading_circle;
    private LinearLayout ll_container;
    private Boolean isStart = true;
    long duration = 800;
    private Dialog dialog;
    private Animation animation;
    private TextView loading_text;
    private TextView loading_tv;

    /**
     * 构造方法
     *
     * @param activity 这个context 其实应该是Activity , 否则要报错
     */
    private NewLoadingUtil(Activity activity) {
        this(activity, "加载中");

    }

    /**
     * 构造方法
     *
     * @param activity
     * @param dialogTitle
     */
    private NewLoadingUtil(Activity activity, String dialogTitle) {
        super();
        this.activity = activity;
        this.dialogTitle = dialogTitle;
    }

    private static NewLoadingUtil newload = null;

    public static NewLoadingUtil getInstance(Activity activity) {
        try {

            if (newload == null && activity != null) {
                newload = new NewLoadingUtil(activity);
                newload.startShowLoading();
            } else {

                if (pd == null || !pd.isShowing()) {
                    newload.startShowLoading();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return newload;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // loadingAnimation.start();
            //            if (!loadingAnimation.isRunning()) {
            //                loading.setBackgroundResource(R.drawable.loading_08);
            //            }


        }
    };
    private TextView tv_loading;

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
    public void startShowLoading() {
        backgroundAlpha(0.8f);
        //        if (showLoading && activity != null && pd == null) {
        //            if (pd != null) {
        //                pd.cancel();
        //                pd = null;
        //            }
        //            if(getInstance()==null){
        //                Log.e(TAG, "startShowLoading: dialog" );
        //                this.pd = createDialog();
        //            }
        //
        //            if (!this.pd.isShowing()) {
        //                if (!activity.isFinishing()) {
        //                    this.pd.show();
        //                }
        //            }
        //            handler.sendEmptyMessage(0);
        //        } else {
        //            this.pd = null;
        //        }
        if (pd == null) {
            pd = createDialog();
            pd.show();

        } else if (pd != null && !pd.isShowing()) {
            pd.show();
        }


    }


    public void stopShowLoading() {
        backgroundAlpha(1.0f);
        // 回调
        if (this.pd != null && this.pd.isShowing() && activity != null && !activity.isFinishing()) {

            try {
                //stopAnimation();
                // loading.clearAnimation();
                this.pd.dismiss();
                pd = null;
                newload = null;
            } catch (Exception ex) {
                ex.printStackTrace();
                //loading.clearAnimation();
                pd.dismiss();
                pd = null;
                newload = null;
            }
        }
    }


    private void addAnimation() {
        animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(800);
        animation.setRepeatCount(1000);
        animation.setFillBefore(true);
        loading.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Log.e(TAG, "onAnimationEnd: " + animation.getRepeatCount());
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
                //Log.e(TAG, "onAnimationRepeat: " + animation.getRepeatCount());

                if (isStart) {
                    duration -= 200;
                    if (duration < 200) {
                        duration = 250;
                    }
                }
                animation.setDuration(duration);
            }
        });

    }

    private Dialog createDialog() {

        View view = View.inflate(activity, R.layout.layout_loading, null);

        loading = (ImageView) view.findViewById(R.id.loading);

        addAnimation();
        isStart = true;

        dialog = new Dialog(activity, R.style.loading_dialog);

        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        // 设置透明度
        //lp.alpha = 0.8f;
        lp.width = ScreenUtils.getInstance(activity).dip2px(100);
        lp.height = ScreenUtils.getInstance(activity).dip2px(100);
        window.setAttributes(lp);
        // dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);

        return dialog;
    }


    void stopAnimation() {
        isStart = false;
    }
}

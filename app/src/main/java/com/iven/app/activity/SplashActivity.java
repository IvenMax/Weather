package com.iven.app.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.MenuActivity;
import com.iven.app.R;

import java.util.Random;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "zpy_SplashActivity";
    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private static final int[] SPLASH_ARRAY = {R.drawable.splash0, R.drawable.splash1, R.drawable.splash2, R.drawable.splash3, R.drawable.splash4,

            R.drawable.splash6, R.drawable.splash7, R.drawable.splash8, R.drawable.splash9, R.drawable.splash10, R.drawable.splash11, R.drawable.splash12, R.drawable.splash13, R.drawable.splash14, R.drawable.splash15, R.drawable.splash16,};

    private ImageView mIvSplash;

    private TextView mSplashAppName;
    private TextView mSplashSlogan;
    private TextView mSplashVersionName;
    private TextView mSplashCopyright;
    //定位

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        init();
        Random r = new Random(SystemClock.elapsedRealtime());
        mIvSplash.setImageResource(SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        animateImage();
    }

    private void init() {
        mIvSplash = (ImageView) findViewById(R.id.iv_splash);
        mSplashAppName = (TextView) findViewById(R.id.splash_app_name);
        mSplashSlogan = (TextView) findViewById(R.id.splash_slogan);
        mSplashVersionName = (TextView) findViewById(R.id.splash_version_name);
        mSplashCopyright = (TextView) findViewById(R.id.splash_copyright);
        mSplashVersionName.setText(getVersion());
    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }

    /**
     * 获取版本号
     * @return V2.3
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "V"+version;
        } catch (Exception e) {
            e.printStackTrace();
            return "V1.0";
        }
    }
    /*private void finishActivity() {
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                startActivity(new Intent(SplashActivity.this, NewsActivity.class));
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });
    }*/
}

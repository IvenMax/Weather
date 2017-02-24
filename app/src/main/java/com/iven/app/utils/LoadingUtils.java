package com.iven.app.utils;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * @author Iven
 * @date 2017/2/24 9:39
 * @Description
 */

public class LoadingUtils {
    private static AnimationDrawable drawable;

    public static void startLoading(ImageView imageView) {
        imageView.setVisibility(View.VISIBLE);
        LoadingUtils.drawable = (AnimationDrawable) imageView.getBackground();
        LoadingUtils.drawable.start();
    }

    public static void stopLoading(ImageView imageView) {
        imageView.setVisibility(View.GONE);
        drawable = (AnimationDrawable) imageView.getBackground();
        drawable.stop();
    }
}

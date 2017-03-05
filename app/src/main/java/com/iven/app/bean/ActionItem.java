package com.iven.app.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ActionItem {

    /**
     * 弹出pop图片
     */
    public Drawable mDrawable;

    /**
     * 弹出pop标题
     */
    public CharSequence mTitle;

    public Class clazz;

    public ActionItem(Drawable mDrawable, CharSequence mTitle) {
        this.mDrawable = mDrawable;
        this.mTitle = mTitle;
    }

    public ActionItem(Context context, int drawbaleResId, int titleResId) {
        mDrawable = context.getResources().getDrawable(drawbaleResId);
        mTitle = context.getResources().getString(titleResId);
    }

    public ActionItem(Context context, CharSequence title, int drawableResId, Class cls) {
        mDrawable = context.getResources().getDrawable(drawableResId);
        mTitle = title;
        clazz = cls;
    }

}

package com.iven.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.ActionItem;
import com.iven.app.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iven
 * @date 2017/3/5 17:55
 * 自定义PopWindow实现分享弹出框以及其他功能
 */

public class MyPopWindow extends PopupWindow {
    private Context mContext;
    /**
     * 屏幕宽高
     */
    private int screenHight, screenWidth;
    /**
     * 弹出框点击事件
     */
    private OnItemClickListener mItemClickListener;
    /**
     * 每个条目的内容
     */
    private ArrayList<ActionItem> mItems = new ArrayList<>();
    /**
     * 用于显示每个小条目
     */
    private ListView mListView;
    //不要显示在中间的位置
    private int popGravity = Gravity.NO_GRAVITY;
    private int[] mLocation = new int[2];
    private Rect mRect = new Rect();

    public MyPopWindow(Context context) {

        this(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public MyPopWindow(Context context, int width, int height) {
        this.mContext = context;
        // 获取焦点
        setFocusable(true);
        //弹窗外部可点击
        setOutsideTouchable(true);
        //设置弹窗内可点击
        setTouchable(true);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHight = dm.heightPixels;
        setWidth(width);
        setHeight(height);
        setBackgroundDrawable(new BitmapDrawable());
        View view = View.inflate(context, R.layout.layout_pop, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        setContentView(view);
        init();
    }

    private void init() {
        mListView = (ListView) getContentView().findViewById(R.id.list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pop弹窗消失
                dismiss();
                if (null != mItemClickListener) {
                    mItemClickListener.onItemClickListener(mItems, position);
                }
            }
        });
    }

    public void show(View view) {
        view.getLocationInWindow(mLocation);
        //设置矩阵大小
        mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth() / 2, mLocation[1] + view.getHeight());
        populateActions();
        showAtLocation(view, popGravity, screenWidth - ScreenUtils.dip2px(mContext, 98) - 34, mRect.bottom);
    }

    private void populateActions() {

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return null == mItems ? 0 : mItems.size();
            }

            @Override
            public Object getItem(int position) {
                return mItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = null;
                if (null == convertView) {
                    textView = new TextView(mContext);
                    textView.setTextSize(16);

                    textView.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    textView.setPadding(0, 20, 0, 10);
                    textView.setSingleLine(true);
                } else {
                    textView = (TextView) convertView;
                }
                ActionItem item = mItems.get(position);
                textView.setText(item.mTitle);
                //设置文字与图片间的距离
                textView.setCompoundDrawablePadding(0);
                //文字左面设置文字
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(item.mDrawable, null, null, null);
                return textView;
            }
        });
    }
    public void addAction(List<ActionItem> actionItem) {
        if (null != actionItem) {
            mItems.addAll(actionItem);
        }
    }

    public void clearActions(int position){
        if (null != mItems && !mItems.isEmpty()) {
            mItems.remove(position);
        }
    }

    public void clearActions() {
        if (null != mItems && !mItems.isEmpty()) {
            mItems.clear();
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(List<ActionItem> items, int position);
    }
}

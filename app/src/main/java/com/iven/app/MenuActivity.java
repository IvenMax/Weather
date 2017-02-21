package com.iven.app;

import android.support.design.widget.NavigationView;
import android.view.View;

import com.iven.app.base.BaseActivity;

public class MenuActivity extends BaseActivity {
    private NavigationView navigation_view;

    @Override
    public int setLayout() {
        return R.layout.activity_menu;
    }

    @Override
    public void setTitle() {
        title_title.setText("天气");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_back), null, null, null);
        title_left.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        navigation_view.setItemIconTintList(null);//让menu里边的图片显示原始颜色

    }

    @Override
    public void widgetClick(View view) {

    }
}

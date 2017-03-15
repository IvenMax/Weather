package com.iven.app.activity;

import android.view.View;

import com.iven.app.R;
import com.iven.app.base.BaseActivity;

public class SelectCompanyActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_select_company;
    }

    @Override
    public void setTitle() {
        title_title.setText("生活指数");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_back), null, null, null);
        title_left.setOnClickListener(this);
        title.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void widgetClick(View view) {

    }
}

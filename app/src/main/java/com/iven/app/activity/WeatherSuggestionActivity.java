package com.iven.app.activity;

import android.util.Log;
import android.view.View;

import com.iven.app.R;
import com.iven.app.base.BaseActivity;
import com.iven.app.utils.Constant;

/**
 * 使用Retrofit请求数据
 */
public class WeatherSuggestionActivity extends BaseActivity {
    private static final String TAG = "zpy_WeatherSuggestionActivity";

    @Override
    public int setLayout() {
        return R.layout.activity_weather_suggestion;
    }

    @Override
    public void setTitle() {
        title_title.setText("生活指数");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_back), null, null, null);
        title_left.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        int suggestionFlag = getIntent().getIntExtra(Constant.SUGGESTION_FLAG, 0);
        Log.e(TAG, "initWidget: 28" + "行 = suggestionFlag = " + suggestionFlag);
    }

    @Override
    public void widgetClick(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
}

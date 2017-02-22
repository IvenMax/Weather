package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iven.app.R;
import com.iven.app.bean.HistoryOfTodayBean;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "zpy_HistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        HistoryOfTodayBean.ResultBean resultBean = (HistoryOfTodayBean.ResultBean) getIntent().getSerializableExtra("resultBean");
        Log.e(TAG, "onCreate: 16" + "行 = " + resultBean.toString());
    }
}

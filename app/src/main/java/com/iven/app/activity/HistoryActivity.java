package com.iven.app.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.base.BaseActivity;
import com.iven.app.bean.HistoryOfTodayBean;
import com.squareup.picasso.Picasso;

public class HistoryActivity extends BaseActivity {
    private static final String TAG = "zpy_HistoryActivity";
    private TextView tv_his_title;
    private TextView tv_his_time;
    private TextView tv_his_desc;
    private ImageView iv_his_pic;

    @Override
    public int setLayout() {
        return R.layout.activity_history;
    }

    @Override
    public void setTitle() {
        title_title.setText("历史今天");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_back), null, null, null);
        title_left.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        tv_his_title = (TextView) findViewById(R.id.tv_his_title);
        tv_his_time = (TextView) findViewById(R.id.tv_his_time);
        tv_his_desc = (TextView) findViewById(R.id.tv_his_desc);
        iv_his_pic = (ImageView) findViewById(R.id.iv_his_pic);
        HistoryOfTodayBean.ResultBean resultBean = (HistoryOfTodayBean.ResultBean) getIntent().getSerializableExtra("resultBean");
        setData(resultBean);

    }

    //数据展示
    private void setData(HistoryOfTodayBean.ResultBean resultBean) {
        if (null == resultBean) {
            return;
        }
        tv_his_title.setText(resultBean.getTitle());
        tv_his_time.setText(String.format("%d年", resultBean.getYear()));
        if (!TextUtils.isEmpty(resultBean.getPic())) {
            Picasso.with(this).load(resultBean.getPic()).into(iv_his_pic);
        } else {
            iv_his_pic.setVisibility(View.GONE);
        }
        tv_his_desc.setText(resultBean.getDes());
        tv_his_title.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_his_title:
                finish();
                break;
        }
    }
}

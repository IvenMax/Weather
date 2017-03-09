package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.news.NewsDetailBean;
import com.iven.app.retrofit.RetrofitNewsUtil;
import com.iven.app.retrofit.request.NewsServiceRequest;
import com.iven.app.utils.Constant;
import com.iven.app.utils.NewLoadingUtil;
import com.iven.app.utils.T;
import com.squareup.picasso.Picasso;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewsDetailActivity extends AppCompatActivity {

    private NewLoadingUtil mNewLoadingUtil;
    private NewsDetailBean mNewsDetailBean;
    private String postId;
    private TextView news_detail_body_tv, news_detail_from_tv;
    private ImageView news_detail_photo_iv;
    private String imgSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
        getDataFromIntent();
        init();
    }

    private void init() {
        news_detail_body_tv = (TextView) findViewById(R.id.news_detail_body_tv);
        news_detail_from_tv = (TextView) findViewById(R.id.news_detail_from_tv);
        news_detail_photo_iv = (ImageView) findViewById(R.id.news_detail_photo_iv);
    }

    /**
     * 获取传递数据
     */
    private void getDataFromIntent() {
        postId = getIntent().getStringExtra(Constant.NEWS_POST_ID);
        imgSrc = getIntent().getStringExtra(Constant.NEWS_IMG_RES);
        http_request(postId);
    }

    private void http_request(final String postId) {
        mNewLoadingUtil = NewLoadingUtil.getInstance(this);
        NewsServiceRequest newsServiceRequest = RetrofitNewsUtil.getInstance().create(NewsServiceRequest.class);
        newsServiceRequest.getNewDetail(postId).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Map<String, NewsDetailBean>>() {
            @Override
            public void onCompleted() {
                mNewLoadingUtil.stopShowLoading();
            }

            @Override
            public void onStart() {
                super.onStart();
                mNewLoadingUtil.startShowLoading();
            }

            @Override
            public void onError(Throwable e) {
                mNewLoadingUtil.stopShowLoading();
                T.showShort(NewsDetailActivity.this, e.getMessage());
            }

            @Override
            public void onNext(Map<String, NewsDetailBean> stringNewsDetailBeanMap) {
                mNewsDetailBean = stringNewsDetailBeanMap.get(postId);
                setData(mNewsDetailBean);
            }
        });
    }

    private void setData(NewsDetailBean newsDetailBean) {
        news_detail_body_tv.setText(newsDetailBean.getBody());
        news_detail_from_tv.setText(newsDetailBean.getSource() + newsDetailBean.getPtime().substring(5, newsDetailBean.getPtime().length() - 3));

        Picasso.with(NewsDetailActivity.this).load(imgSrc).error(R.drawable.ic_loading).into(news_detail_photo_iv);


    }
}

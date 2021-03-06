package com.iven.app.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
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
import com.iven.app.view.URLImageGetter;
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
    private CollapsingToolbarLayout toolbar_layout;
    private Toolbar toolbar;
    private URLImageGetter mUrlImageGetter;

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
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                //为了实现共享元素动画，使用下边的方法结束当前页面
                ActivityCompat.finishAfterTransition(NewsDetailActivity.this);
            }
        });
    }

    private void setToolBarLayout(String newsTitle) {
        toolbar_layout.setTitle(newsTitle);
        toolbar_layout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbar_layout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
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
                if (e.getMessage().contains("403")){
                    T.showShort(NewsDetailActivity.this, "接口异常");
                }
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
        news_detail_from_tv.setText(newsDetailBean.getSource() + " " + newsDetailBean.getPtime().substring(5, newsDetailBean.getPtime().length() - 3));

        Picasso.with(NewsDetailActivity.this).load(imgSrc).error(R.drawable.ic_loading).into(news_detail_photo_iv);
        setToolBarLayout(newsDetailBean.getTitle());
        setBody(newsDetailBean,newsDetailBean.getBody());
    }

    /**
     * 处理HTML文本显示
     * @param newsDetailBean
     * @param newsBody
     */
    private void setBody(NewsDetailBean newsDetailBean, String newsBody) {
        int imgTotal = newsDetailBean.getImg().size();
        if (imgTotal >=2 && null !=newsBody) {
            //              mNewsDetailBodyTv.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效,实测经常卡机崩溃
            mUrlImageGetter = new URLImageGetter(news_detail_body_tv, newsBody, imgTotal);
            news_detail_body_tv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        } else {
            news_detail_body_tv.setText(Html.fromHtml(newsBody));
        }
    }
}

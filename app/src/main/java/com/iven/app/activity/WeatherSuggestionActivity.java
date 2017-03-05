package com.iven.app.activity;

import android.view.View;
import android.widget.TextView;

import com.iven.app.MyApp;
import com.iven.app.R;
import com.iven.app.base.BaseActivity;
import com.iven.app.bean.WeatherSuggestBean;
import com.iven.app.retrofit.RetrofitUtil;
import com.iven.app.retrofit.request.SuggestionRequest;
import com.iven.app.utils.Constant;
import com.iven.app.utils.T;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 使用Retrofit请求数据
 */
public class WeatherSuggestionActivity extends BaseActivity {
    private static final String TAG = "zpy_WeatherSuggestionActivity";
    private TextView tv_suggestion_sug;
    private TextView tv_suggestion_desc;
    private int suggestionFlag;

    @Override
    public int setLayout() {
        return R.layout.activity_weather_suggestion;
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
        suggestionFlag = getIntent().getIntExtra(Constant.SUGGESTION_FLAG, 0);
        tv_suggestion_sug = (TextView) findViewById(R.id.tv_suggestion_sug);
        tv_suggestion_desc = (TextView) findViewById(R.id.tv_suggestion_desc);
        setTitleInfo();
        http_request(MyApp.currentCity);
    }

    /**
     * 获取生活指数信息
     *
     * @param currentCity 当前城市
     */
    private void http_request(String currentCity) {
        SuggestionRequest suggestionRequest = RetrofitUtil.getInstance().create(SuggestionRequest.class);
        suggestionRequest.getHeWeather(Constant.HEWEATHER_KEY, currentCity).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<WeatherSuggestBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                T.showLong(WeatherSuggestionActivity.this, e.getMessage());
            }

            @Override
            public void onNext(WeatherSuggestBean weatherSuggestBean) {
                WeatherSuggestBean.HeWeather5Bean.SuggestionBean suggestion = weatherSuggestBean.getHeWeather5().get(0).getSuggestion();
                setInfo(suggestion);
            }
        });
    }

    private void setInfo(WeatherSuggestBean.HeWeather5Bean.SuggestionBean suggestion) {
        switch (suggestionFlag) {
            case 1:
                tv_suggestion_sug.setText(suggestion.getComf().getBrf());
                tv_suggestion_desc.setText(suggestion.getComf().getTxt());
                break;
            case 2:
                tv_suggestion_sug.setText(suggestion.getCw().getBrf());
                tv_suggestion_desc.setText(suggestion.getCw().getTxt());
                break;
            case 3:
                tv_suggestion_sug.setText(suggestion.getDrsg().getBrf());
                tv_suggestion_desc.setText(suggestion.getDrsg().getTxt());
                break;
            case 4:
                tv_suggestion_sug.setText(suggestion.getFlu().getBrf());
                tv_suggestion_desc.setText(suggestion.getFlu().getTxt());
            case 5:
                tv_suggestion_sug.setText(suggestion.getSport().getBrf());
                tv_suggestion_desc.setText(suggestion.getSport().getTxt());
                break;
            case 6:
                tv_suggestion_sug.setText(suggestion.getTrav().getBrf());
                tv_suggestion_desc.setText(suggestion.getTrav().getTxt());
            case 7:
                tv_suggestion_sug.setText(suggestion.getUv().getBrf());
                tv_suggestion_desc.setText(suggestion.getUv().getTxt());
                break;
            case 8:
                tv_suggestion_sug.setText(suggestion.getAir().getBrf());
                tv_suggestion_desc.setText(suggestion.getAir().getTxt());
                break;
            default:
                break;
        }
    }

    private void setTitleInfo() {
        switch (suggestionFlag) {
            case 1:
                title_title.setText("舒适指数");
                break;
            case 2:
                title_title.setText("洗车指数");
                break;
            case 3:
                title_title.setText("穿衣指数");
                break;
            case 4:
                title_title.setText("感冒指数");
                break;
            case 5:
                title_title.setText("运动指数");
                break;
            case 6:
                title_title.setText("旅游指数");
                break;
            case 7:
                title_title.setText("紫外线指数");
                break;
            case 8:
                title_title.setText("空气指数");
                break;
            default:
                break;
        }

    }

    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }

}

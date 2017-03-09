package com.iven.app.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.iven.app.R;
import com.iven.app.base.BaseFragment;
import com.iven.app.bean.news.NewsSummaryBean;
import com.iven.app.retrofit.RetrofitNewsUtil;
import com.iven.app.retrofit.request.NewsServiceRequest;
import com.iven.app.utils.ApiConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Iven
 * @date 2017/3/9 10:56
 * @Description 关于懒加载：http://www.jianshu.com/p/311c7ffdb85b
 */

public class NewsFragment extends BaseFragment {
    private static final String TAG = "zpy_NewsFragment";
    private ArrayList<String> titles;//导航栏标题
    private String title;//加载哪个
    private View view;
    private RecyclerView rv_new_list;


    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.layout_fragment_news, null);
        rv_new_list = ((RecyclerView) view.findViewById(R.id.rv_new_list));
        return view;
    }

    @Override
    public void initData() {
        title = getArguments().getString("title", "0");
        if (!TextUtils.isEmpty(title)) {
            String id = getNewsId(title);
            http_request("list", id, 0);
        } else {
            Log.e(TAG, "initData: 32" + "行 = index = " + title);

        }
    }

    private void http_request(String type, String id, int startPage) {
        NewsServiceRequest newsServiceRequest = RetrofitNewsUtil.getInstance().create(NewsServiceRequest.class);
        newsServiceRequest.getNewsList(type, id, startPage).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Map<String, List<NewsSummaryBean>>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: 63" + "行 = ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: 67" + "行 = " + e.getMessage());
            }

            @Override
            public void onNext(Map<String, List<NewsSummaryBean>> stringListMap) {
                Log.e(TAG, "onNext: 69" + "行 = =====" + " -  " + stringListMap.get(getNewsId(title)).get(0).getTitle());
            }
        });
    }

    public static String getNewsId(String title) {
        String id = "";
        switch (title) {
            case "头条":
                id = ApiConstants.HEADLINE_ID;
                break;
            case "娱乐":
                id = ApiConstants.ENTERTAINMENT_ID;
                break;
            case "体育":
                id = ApiConstants.SPORTS_ID;
                break;
            case "财经":
                id = ApiConstants.FINANCE_ID;
                break;
            case "科技":
                id = ApiConstants.TECH_ID;
                break;
            case "电影":
                id = ApiConstants.MOVIE_ID;
                break;
            case "汽车":
                id = ApiConstants.CAR_ID;
                break;
            case "笑话":
                id = ApiConstants.JOKE_ID;
                break;
            case "游戏":
                id = ApiConstants.GAME_ID;
                break;
            case "时尚":
                id = ApiConstants.FASHION_ID;
                break;
            case "情感":
                id = ApiConstants.EMOTION_ID;
                break;
            case "精选":
                id = ApiConstants.CHOICE_ID;
                break;
            case "数码":
                id = ApiConstants.DIGITAL_ID;
                break;
            case "彩票":
                id = ApiConstants.LOTTERY_ID;
                break;
            case "教育":
                id = ApiConstants.EDUCATION_ID;
                break;
            case "旅游":
                id = ApiConstants.TOUR_ID;
                break;
            case "手机":
                id = ApiConstants.PHONE_ID;
                break;
            case "社会":
                id = ApiConstants.SOCIETY_ID;
                break;
            case "家居":
                id = ApiConstants.FURNISHING_ID;
                break;
        }
        return id;
    }
}
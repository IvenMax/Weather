package com.iven.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.iven.app.R;
import com.iven.app.activity.NewsDetailActivity;
import com.iven.app.adapter.NewsListAdapter;
import com.iven.app.base.BaseFragment;
import com.iven.app.bean.news.NewsSummaryBean;
import com.iven.app.retrofit.RetrofitNewsUtil;
import com.iven.app.retrofit.request.NewsServiceRequest;
import com.iven.app.utils.ApiConstants;
import com.iven.app.utils.Constant;
import com.iven.app.utils.NewLoadingUtil;
import com.iven.app.utils.T;

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
    private ArrayList<NewsSummaryBean> datas;
    private NewsListAdapter mNewsListAdapter;
    private NewLoadingUtil mNewLoadingUtil;
    private int startPage = 0;//起始加载索引
    private String id;
    private SwipeRefreshLayout swiperefreshlayout;


    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.layout_fragment_news, null);
        rv_new_list = ((RecyclerView) view.findViewById(R.id.rv_new_list));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_new_list.setLayoutManager(linearLayoutManager);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        datas = new ArrayList<>();
        rv_new_list.setItemAnimator(new DefaultItemAnimator());
        swiperefreshlayout.setColorSchemeColors(Color.RED, Color.BLUE);

        mNewsListAdapter = new NewsListAdapter(getActivity(), datas);
        rv_new_list.setAdapter(mNewsListAdapter);
        setListener();
        return view;
    }

    private void setListener() {
        //下拉刷新
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh: 76" + "行 = " +"下拉刷新中.......");
                startPage=0;
                http_request("list",id,startPage);
            }
        });
        mNewsListAdapter.setOnItemClickLitener(new NewsListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                T.showLong(getActivity(), "position = " + position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(Constant.NEWS_POST_ID, datas.get(position).getPostid());
                intent.putExtra(Constant.NEWS_IMG_RES, datas.get(position).getImgsrc());
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        //滚动监听,实现下拉刷新效果
        //        rv_new_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
        //            @Override
        //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //                super.onScrollStateChanged(recyclerView, newState);
        //                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        //                int visibleItemCount = layoutManager.getChildCount();
        //                int totalItemCount = layoutManager.getItemCount();
        //
        //                if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
        //                        && lastVisibleItemPosition >= totalItemCount - 1) {
        //                    startPage+=20;
        //                    Log.e(TAG, "onScrollStateChanged: 96" + "行 = " +startPage);
        //                    http_request("list",id,startPage);
        //                    mNewsListAdapter.showFooter();
        //                    rv_new_list.scrollToPosition(mNewsListAdapter.getItemCount() - 1);
        //                }
        //            }
        //
        //        });
    }

    @Override
    public void initData() {
        title = getArguments().getString("title", "0");
        if (!TextUtils.isEmpty(title)) {
            id = getNewsId(title);
            http_request("list", id, startPage);
        } else {
            Log.e(TAG, "initData: 32" + "行 = index = " + title);

        }
    }

    private void http_request(String type, String id, int startPage) {
        mNewLoadingUtil = NewLoadingUtil.getInstance(getActivity());
        NewsServiceRequest newsServiceRequest = RetrofitNewsUtil.getInstance().create(NewsServiceRequest.class);
        newsServiceRequest.getNewsList(type, id, startPage).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Map<String, List<NewsSummaryBean>>>() {
            @Override
            public void onStart() {
                super.onStart();
                mNewLoadingUtil.startShowLoading();
            }

            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: 103" + "行 = ");
                mNewLoadingUtil.stopShowLoading();
                swiperefreshlayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: 108" + "行 = ");
                T.showShort(getActivity(), e.getMessage());
                mNewLoadingUtil.stopShowLoading();
                swiperefreshlayout.setRefreshing(false);
            }


            @Override
            public void onNext(Map<String, List<NewsSummaryBean>> stringListMap) {
                List<NewsSummaryBean> newsSummaryBeen = stringListMap.get(getNewsId(title));
                datas.clear();
                datas.addAll(newsSummaryBeen);
                mNewsListAdapter.notifyDataSetChanged();
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
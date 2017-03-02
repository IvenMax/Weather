package com.iven.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iven.app.R;
import com.iven.app.adapter.TestAdapter;

import java.util.ArrayList;

/**
 * @author Iven
 * @date 2017/2/21 14:43
 * @Description
 */

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<String> mStringArrayList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_news, container, false);
        mRecyclerView = ((RecyclerView) view.findViewById(R.id.test_rrrrr));
        for (int i = 0; i < 30; i++) {
            mStringArrayList.add("sssss"+i);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new TestAdapter(mStringArrayList,getActivity()));
        return view;
    }

}

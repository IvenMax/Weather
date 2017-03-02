package com.iven.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iven.app.R;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder>{
        private ArrayList<String> datas;
    private Context mContext;

    public TestAdapter(ArrayList<String> datas, Context context) {
        this.datas = datas;
        mContext = context;
    }

    @Override
        public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_test_test, null, false);
            return new TestHolder(inflate);
        }

        @Override
        public void onBindViewHolder(TestHolder holder, int position) {
            holder.mTextView.setText(datas.get(position)+"-0");
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class TestHolder extends RecyclerView.ViewHolder{
            private TextView mTextView;

            public TestHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text_test);
            }
        }
    }
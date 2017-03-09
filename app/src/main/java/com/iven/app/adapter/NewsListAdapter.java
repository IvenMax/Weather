package com.iven.app.adapter;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.news.NewsSummaryBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Iven
 * @date 2017/3/9 13:10
 * @Description
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    private ArrayList<NewsSummaryBean> datas;
    private Context mContext;
    private static final String TAG = "zpy_NewsListAdapter";
    private OnItemClickLitener mOnItemClickLitener;
    private boolean mIsShowFooter;
    private int mLastPosition = -1;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public NewsListAdapter(Context context, ArrayList<NewsSummaryBean> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsListAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item_newslist, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (null == datas) {
            return;
        }
        holder.news_summary_title_tv.setText(datas.get(position).getTitle());
        if (null != datas.get(position).getDigest()) {
            holder.news_summary_digest_tv.setText(datas.get(position).getDigest());
        } else {
            holder.news_summary_digest_tv.setText("--");
        }
        holder.news_summary_ptime_tv.setText(datas.get(position).getPtime().substring(5, datas.get(position).getPtime().length() - 3));
        Picasso.with(mContext).load(datas.get(position).getImgsrc()).into(holder.news_summary_photo_iv);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
        setItemAppearAnimation(holder, position, R.anim.anim_bottom_in);
    }


    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    public void hideFooter() {
        mIsShowFooter = false;
        notifyItemRemoved(getItemCount());
    }

    @Override
    public int getItemCount() {
        return null != datas ? datas.size() : 0;
        //        if (datas == null) {
        //            return 0;
        //        }
        //        int itemSize = datas.size();
        //        if (mIsShowFooter) {
        //            itemSize += 1;
        //        }
        //        return itemSize;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView news_summary_photo_iv;
        private TextView news_summary_title_tv;
        private TextView news_summary_digest_tv;
        private TextView news_summary_ptime_tv;


        public MyViewHolder(View itemView) {
            super(itemView);
            news_summary_photo_iv = (ImageView) itemView.findViewById(R.id.news_summary_photo_iv);
            news_summary_title_tv = (TextView) itemView.findViewById(R.id.news_summary_title_tv);
            news_summary_digest_tv = (TextView) itemView.findViewById(R.id.news_summary_digest_tv);
            news_summary_ptime_tv = (TextView) itemView.findViewById(R.id.news_summary_ptime_tv);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    protected void setItemAppearAnimation(RecyclerView.ViewHolder holder, int position, @AnimRes int type) {
        if (position > mLastPosition/* && !isFooterPosition(position)*/) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), type);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }
}

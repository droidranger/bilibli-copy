package com.ranger.xyg.xygapp.ui.view.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.config.BCConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyg on 2017/4/9.
 */

public abstract class BCRecyclerAdapter<T> extends RecyclerView.Adapter {
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 1;
    //正在加载中
    public static final int LOADING_MORE = 2;
    // 没有更多数据了
    public static final int NO_MORE_DATA = 0;
    public static final int TYPE_NORMAL_ITEM = 1;
    public static final int TYPE_FOOTER = 2;

    protected final LayoutInflater mInflater;
    protected List<T> mData;
    //上拉加载更多状态-默认为0
    protected int mLoadMoreStatus;
    protected int mPageLimit = BCConstant.ListConstant.PAGE_LIMIT_TEN;

    public BCRecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void setData(List<T> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL_ITEM) {
            return getNormalItemHolder(parent);
        } else if (viewType == TYPE_FOOTER) {
            return getFooterItemHolder(parent);
        }
        return null;
    }

    protected RecyclerView.ViewHolder getFooterItemHolder(ViewGroup parent){
        View footView = mInflater.inflate(R.layout.layout_recycler_view_footer, parent, false);
        FootViewHolder footViewHolder = new FootViewHolder(footView);
        return footViewHolder;
    }

    protected abstract ItemViewHolder getNormalItemHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            invalidateItemByData((ItemViewHolder) holder, mData.get(position));
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.mBar.setVisibility(View.VISIBLE);
                    footViewHolder.mLoadStatusTv.setText("上拉加载更多");
                    break;
                case LOADING_MORE:
                    footViewHolder.mBar.setVisibility(View.VISIBLE);
                    footViewHolder.mLoadStatusTv.setText("正在加载更多");
                    break;
                case NO_MORE_DATA:
                    footViewHolder.mBar.setVisibility(View.GONE);
                    footViewHolder.mLoadStatusTv.setText("没有更多数据啦~");
                    break;
            }
        }
    }

    protected abstract void invalidateItemByData(ItemViewHolder holder, T t);

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mData.isEmpty() ? 0 : mData.size() + 1;
    }

    public static abstract class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_load_status)
        public TextView mLoadStatusTv;
        @BindView(R.id.pb_load_more)
        public ProgressBar mBar;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 上拉加载更多              PULLUP_LOAD_MORE=0;
     * 正在加载中               LOADING_MORE=1;
     * 加载完成已经没有更多数据了 NO_MORE_DATA=2;
     */
    public void changeFooterStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}

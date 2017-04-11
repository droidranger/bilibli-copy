package com.ranger.xyg.xygapp.ui.view.list;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.config.BCConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyg on 2017/4/9.
 */

public class BCSwipeRefreshLayout<T> extends SwipeRefreshLayout {

    @BindView(R.id.rv_live_list)
    RecyclerView mLiveListRv;
    @BindView(R.id.fl_empty_container)
    FrameLayout mEmptyContainer;
    private IRefreshListener mRefreshListener;
    private BCRecyclerAdapter<T> mAdapter;
    private int mLastVisibleItem;
    private LinearLayoutManager mLinearLayoutManager;
    private int mCurrentPage = BCConstant.ListConstant.FIRST_PAGE;
    private int mTotalPage;
    private boolean mIsRefresh = true;

    public BCSwipeRefreshLayout(Context context) {
        super(context);
        initViews(context);
    }

    public BCSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_swipe_refresh_more, this);
        ButterKnife.bind(this, this);
        setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 初始显示下拉刷新
        this.post(new Runnable() {
            @Override
            public void run() {
                setRefreshing(true);
                mCurrentPage = BCConstant.ListConstant.FIRST_PAGE;
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
        // 下拉刷新回调
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {// 下拉刷新
                mIsRefresh = true;
                mCurrentPage = BCConstant.ListConstant.FIRST_PAGE;
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mLiveListRv.setLayoutManager(mLinearLayoutManager);
        //添加分隔线
        //mLiveListRv.addItemDecoration(new AdvanceDecoration(this, OrientationHelper.VERTICAL));
    }

    public void setAdapter(BCRecyclerAdapter<T> adapter) {
        if (adapter == null) {
            return;
        }
        mAdapter = adapter;
        mLiveListRv.setAdapter(mAdapter);
        mLiveListRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mCurrentPage++;
                    mIsRefresh = false;
                    if (mCurrentPage >= mTotalPage) {
                        mAdapter.changeFooterStatus(BCRecyclerAdapter.NO_MORE_DATA);
                        return;
                    }
                    mAdapter.changeFooterStatus(BCRecyclerAdapter.LOADING_MORE);
                    if (mRefreshListener != null) {
                        mRefreshListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    public void setRefreshListener(IRefreshListener listener) {
        mRefreshListener = listener;
    }

    public void onLoadFinish(List<T> list, int totalPage) {
        onLoadFinish(list, totalPage, false, null);
    }

    public void onLoadFinish(List<T> list, int totalPage, boolean isEmpty, View emptyView) {
        this.setRefreshing(false);
        if (isEmpty) {
            mLiveListRv.setVisibility(GONE);
            if (emptyView != null) {
                mEmptyContainer.removeAllViews();
                mEmptyContainer.addView(emptyView);
            }
            return;
        }
        if (list == null || list.isEmpty()) {
            mAdapter.changeFooterStatus(BCRecyclerAdapter.NO_MORE_DATA);
            return;
        }
        mTotalPage = totalPage;
        mAdapter.setData(list, mIsRefresh);
        mAdapter.changeFooterStatus(BCRecyclerAdapter.PULLUP_LOAD_MORE);
    }


    public interface IRefreshListener {
        void onRefresh();
        void onLoadMore();
    }
}

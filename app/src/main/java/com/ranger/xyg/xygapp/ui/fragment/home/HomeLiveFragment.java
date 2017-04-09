package com.ranger.xyg.xygapp.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.fragment.BaseFragment;
import com.ranger.xyg.xygapp.ui.view.list.BCRecyclerAdapter;
import com.ranger.xyg.xygapp.ui.view.list.BCSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/8.
 */
public class HomeLiveFragment extends BaseFragment implements BCSwipeRefreshLayout.IRefreshListener {

    @BindView(R.id.srls_wipe_refresh_view)
    BCSwipeRefreshLayout<String> mSwipeRefreshView;

    public static HomeLiveFragment newInstance(Bundle bundle) {
        HomeLiveFragment fragment = new HomeLiveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentRes() {
        return R.layout.fragment_home_live;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshView.setAdapter(new LiveRecyclerAdapter(getActivity()));
        mSwipeRefreshView.setRefreshListener(this);
    }

    public class LiveRecyclerAdapter extends BCRecyclerAdapter<String> {

        public LiveRecyclerAdapter(Context context) {
            super(context);
        }

        @Override
        protected ItemViewHolder getNormalItemHolder(ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item, parent, false);
            return new StringViewHolder(view);
        }

        @Override
        protected void invalidateItemByData(ItemViewHolder holder, String s) {
            StringViewHolder stringViewHolder = (StringViewHolder) holder;
            stringViewHolder.mNameTv.setText(s);
        }

        public class StringViewHolder extends ItemViewHolder {

            @BindView(R.id.text)
            public TextView mNameTv;

            public StringViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> newDatas = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    int index = i + 1;
                    newDatas.add("new item" + index);
                }
                mSwipeRefreshView.onLoadFinish(newDatas, 10);
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadMoreData();
    }

    private void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> newDatas = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    int index = i + 1;
                    newDatas.add("more item" + index);
                }
                mSwipeRefreshView.onLoadFinish(newDatas, 10);
            }
        }, 2000);
    }
}

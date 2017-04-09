package com.ranger.xyg.xygapp.ui.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.fragment.BaseFragment;
import com.ranger.xyg.xygapp.utils.log.ToastUtils;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/8.
 */
public class HomeLiveFragment extends BaseFragment {

    @BindView(R.id.rv_live_list)
    RecyclerView mLiveListRv;
    @BindView(R.id.tv_frag_name)
    TextView mFragNameTv;
    @BindView(R.id.srls_wipe_refresh_view)
    SwipeRefreshLayout mSwipeRefreshView;

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
        mSwipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshView.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshView.setRefreshing(true);
            }
        });
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {// 下拉刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(getActivity(), "xialashuaxin");
                    }
                }, 3000);
            }
        });


    }

}

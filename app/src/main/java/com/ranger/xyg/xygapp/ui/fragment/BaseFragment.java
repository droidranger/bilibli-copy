package com.ranger.xyg.xygapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranger.xyg.xygapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyg on 2017/4/8.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    /*@Nullable
    @BindView(R.id.srls_wipe_refresh_view)
    SwipeRefreshLayout mSwipeRefreshView;*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentRes(), container, false);
        ButterKnife.bind(this, mRootView);
        initSwipeRefresh();
        return mRootView;
    }

    protected abstract int getContentRes();

    protected void initViews() {

    }

    protected void initData() {

    }

    protected void initSwipeRefresh() {
        /*if (mSwipeRefreshView == null) {
            return;
        }
        mSwipeRefreshView.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });*/
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected View findViewById(int viewId) {
        return mRootView.findViewById(viewId);
    }
}

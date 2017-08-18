package com.ranger.xyg.xygapp.ui.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranger.xyg.library.tkrefreshlayout.RefreshListenerAdapter;
import com.ranger.xyg.library.tkrefreshlayout.TwinklingRefreshLayout;
import com.ranger.xyg.library.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.bean.AppInfo;
import com.ranger.xyg.xygapp.model.process.hthread.HandlerThreadProcess;
import com.ranger.xyg.xygapp.ui.adapter.base.BaseRecyclerAdapter;
import com.ranger.xyg.xygapp.ui.adapter.base.CommonHolder;
import com.ranger.xyg.xygapp.ui.view.XygDraweeView;
import com.ranger.xyg.xygapp.utils.ExtendUtils;
import com.ranger.xyg.library.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by xyg on 2017/5/17.
 */

public class AppListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout mRefreshView;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private AppListRecyclerAdapter mAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_app_list;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupRecyclerView(mRecyclerView);
    }

    private void setupRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AppListRecyclerAdapter();
        rv.setAdapter(mAdapter);

        BezierLayout headerView = new BezierLayout(this);
        mRefreshView.setHeaderView(headerView);
        mRefreshView.setMaxHeadHeight(140);
//        refreshLayout.setFloatRefresh(true);
//        refreshLayout.setPureScrollModeOn(true);
        mRefreshView.setOverScrollBottomShow(false);
//        refreshLayout.setAutoLoadMore(true);
//        addHeader();

        mRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mRefreshView.finishRefreshing();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                mRefreshView.finishLoadmore();
            }
        });

        mRefreshView.startRefresh();
    }

    public class AppListRecyclerAdapter extends BaseRecyclerAdapter<AppInfo> {

        @Override
        public CommonHolder<AppInfo> setViewHolder(ViewGroup parent) {
            return new AppListRecyclerAdapter.ViewHolder(parent.getContext(), parent);
        }

        public class ViewHolder extends CommonHolder<AppInfo> {

            @BindView(R.id.xdv_app_list_item_header)
            XygDraweeView mAppImgView;
            @BindView(R.id.tv_app_name)
            TextView mAppNameTv;
            @BindView(R.id.tv_app_path)
            TextView mAppPathTv;
            @BindView(R.id.tv_app_version)
            TextView mAppVersionTv;
            @BindView(R.id.btn_export_apk)
            TextView mExportApkBtn;

            public ViewHolder(Context context, ViewGroup root) {
                super(context, root, R.layout.app_list_item_layout);
            }

            @Override
            public void bindData(AppInfo data) {
                mAppImgView.setImageDrawable(data.appIcon);
                mAppNameTv.setText(data.appName);
                mAppPathTv.setText(data.apkPath);
                mAppVersionTv.setText(data.appVersion);
                mExportApkBtn.setTag(data);
                mExportApkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort(AppListActivity.this, "ddddddd");
                    }
                });
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        try {
            HandlerThreadProcess<Void, List<AppInfo>> process = new HandlerThreadProcess<>(getMainLooper());
            process.registerListener(new HandlerThreadProcess.IProcessListener<Void, List<AppInfo>>() {

                @Override
                public List<AppInfo> onProcessing(Void aVoid) {
                    return ExtendUtils.getAppList(AppListActivity.this);
                }

                @Override
                public void onPostProcess(List<AppInfo> appInfos) {
                    mRefreshView.finishRefreshing();
                    mAdapter.setDataList(appInfos);
                }
            });
            process.process(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

package com.ranger.xyg.xygapp.ui.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ranger.xyg.library.tkrefreshlayout.RefreshListenerAdapter;
import com.ranger.xyg.library.tkrefreshlayout.TwinklingRefreshLayout;
import com.ranger.xyg.library.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.bean.AppInfo;
import com.ranger.xyg.xygapp.process.hthread.HandlerThreadProcess;
import com.ranger.xyg.xygapp.ui.adapter.base.BaseRecyclerAdapter;
import com.ranger.xyg.xygapp.ui.adapter.base.CommonHolder;
import com.ranger.xyg.xygapp.ui.view.XygDraweeView;
import com.ranger.xyg.xygapp.utils.ExtendUtils;
import com.ranger.xyg.xygapp.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by xyg on 2017/5/17.
 */

public class AppXListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    private AppListRecyclerAdapter mAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_app_xlist;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        //Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);

        //mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(dividerDrawable));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup) findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(header);

        mAdapter = new AppListRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.refresh();
    }

    class AppListRecyclerAdapter extends BaseRecyclerAdapter<AppInfo> {

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
                        ToastUtils.showShort(AppXListActivity.this, "ddddddd");
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
                    return ExtendUtils.getAppList(AppXListActivity.this);
                }

                @Override
                public void onPostProcess(List<AppInfo> appInfos) {
                    mAdapter.setDataList(appInfos);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete();
                }
            });
            process.process(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

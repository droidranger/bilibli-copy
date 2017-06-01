package com.ranger.xyg.xygapp.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ranger.xyg.library.tkrefreshlayout.RefreshListenerAdapter;
import com.ranger.xyg.library.tkrefreshlayout.TwinklingRefreshLayout;
import com.ranger.xyg.library.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.bean.LiveParams;
import com.ranger.xyg.xygapp.ui.adapter.base.BaseRecyclerAdapter;
import com.ranger.xyg.xygapp.ui.adapter.base.CommonHolder;
import com.ranger.xyg.xygapp.ui.fragment.BaseFragment;
import com.ranger.xyg.xygapp.ui.view.list.BCSwipeRefreshLayout;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ranger.xyg.xygapp.bean.HttpConfig.LIVE_RECOMMEND_URL;

/**
 * Created by xyg on 2017/4/8.
 */
public class HomeLiveFragment extends BaseFragment {

    @BindView(R.id.refresh)
    TwinklingRefreshLayout mRefreshView;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private LiveRecyclerAdapter mAdapter;

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
        setupRecyclerView(mRecyclerView);
    }

    private void setupRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new LiveRecyclerAdapter();
        rv.setAdapter(mAdapter);

        BezierLayout headerView = new BezierLayout(getContext());
        mRefreshView.setHeaderView(headerView);
        mRefreshView.setMaxHeadHeight(140);
//        refreshLayout.setFloatRefresh(true);
//        refreshLayout.setPureScrollModeOn(true);
        mRefreshView.setOverScrollBottomShow(false);
//        refreshLayout.setAutoLoadMore(true);

//        addHeader();
        loadData(mRefreshView);

        mRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                loadData(refreshLayout);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadMoreData(refreshLayout);
            }
        });

        mRefreshView.startRefresh();
    }

    public class LiveRecyclerAdapter extends BaseRecyclerAdapter<String> {

        @Override
        public CommonHolder<String> setViewHolder(ViewGroup parent) {
            return new ViewHolder(parent.getContext(), parent);
        }

        public class ViewHolder extends CommonHolder<String> {

            @BindView(R.id.text)
            public TextView mNameTv;

            public ViewHolder(Context context, ViewGroup root) {
                super(context, root, R.layout.item);
            }

            @Override
            public void bindData(String data) {
                mNameTv.setText(data);
            }
        }
    }

    private void loadData(final TwinklingRefreshLayout refreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> newDatas = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    int index = i + 1;
                    newDatas.add("new item" + index);
                }
                mAdapter.setDataList(newDatas);
                refreshLayout.finishRefreshing();
            }
        }, 2000);
    }

    private void loadMoreData(final TwinklingRefreshLayout refreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> newDatas = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    int index = i + 1;
                    newDatas.add("more item" + index);
                }
                mAdapter.addItems(newDatas);
                refreshLayout.finishLoadmore();
            }
        }, 2000);
    }


    @Override
    protected void initData() {
        LiveParams params = new LiveParams(LIVE_RECOMMEND_URL);


        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

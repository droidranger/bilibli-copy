package com.ranger.xyg.xygapp.demos.recycleview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/7.
 */

public class RecyclerViewDemoActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentResId() {
        return R.layout.activity_recycler_view_demo;
    }

    @Override
    protected void initViews() {
        super.initViews();
        //这里用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //这里用线性宫格显示 类似于grid view
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //这里用线性宫格显示 类似于瀑布流
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(this));
    }
}

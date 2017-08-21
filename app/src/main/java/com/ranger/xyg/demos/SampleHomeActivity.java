package com.ranger.xyg.demos;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranger.xyg.demos.glide.GlideDemoActivity;
import com.ranger.xyg.demos.recycleview.NormalRecyclerViewAdapter;
import com.ranger.xyg.demos.scroll.MyScrollActivity;
import com.ranger.xyg.demos.video.LocalVideoActivity;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.demos.dropdown.DropDownActivity;
import com.ranger.xyg.demos.jni.JniDemosActivity;
import com.ranger.xyg.demos.ldia.AidlClientActivity;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;
import com.ranger.xyg.xygapp.ui.listener.OnRecyclerViewItemClickListener;

import butterknife.BindView;

/**
 * Created by xyg on 2017/4/7.
 */

public class SampleHomeActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private NormalRecyclerViewAdapter mAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_recycler_view_demo;
    }

    @Override
    protected void initViews() {
        super.initViews();
        //这里用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NormalRecyclerViewAdapter(this);
        mAdapter.setData(getResources().getStringArray(R.array.DemosList));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<String>() {
            @Override
            public void onItemClick(View view, String data) {
                if (data.equals("VideoViewDemo")){
                    startActivity(new Intent(SampleHomeActivity.this, LocalVideoActivity.class));
                } else if (data.equals("RetrofitDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, com.ranger.xyg.demos.retrofit.RetrofitDemoActivity.class));
                } if (data.equals("ScrollerDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, MyScrollActivity.class));
                } if (data.equals("GlideDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, GlideDemoActivity.class));
                } if (data.equals("DropDownDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, DropDownActivity.class));
                } if (data.equals("JniDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, JniDemosActivity.class));
                } if (data.equals("AidlDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, AidlClientActivity.class));
                }
            }
        });
    }
}

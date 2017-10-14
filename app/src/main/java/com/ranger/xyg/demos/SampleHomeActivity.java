package com.ranger.xyg.demos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranger.xyg.demos.dropdown.DropDownActivity;
import com.ranger.xyg.demos.finger.FingerActivity;
import com.ranger.xyg.demos.glide.GlideDemoActivity;
import com.ranger.xyg.demos.jni.JniDemosActivity;
import com.ranger.xyg.demos.ldia.AidlClientActivity;
import com.ranger.xyg.demos.recycleview.NormalRecyclerViewAdapter;
import com.ranger.xyg.demos.scroll.MyScrollActivity;
import com.ranger.xyg.demos.video.LocalVideoActivity;
import com.ranger.xyg.library.utils.ToastUtils;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;
import com.ranger.xyg.xygapp.ui.listener.OnRecyclerViewItemClickListener;
import com.ranger.xyg.xygapp.zxing.CaptureActivity;

import butterknife.BindView;
import cn.campusapp.router.Router;
import cn.campusapp.router.route.ActivityRoute;

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
                if (data.equals("VideoViewDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, LocalVideoActivity.class));
                } else if (data.equals("RetrofitDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, com.ranger.xyg.demos.retrofit.RetrofitDemoActivity.class));
                } else if (data.equals("ScrollerDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, MyScrollActivity.class));
                } else if (data.equals("GlideDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, GlideDemoActivity.class));
                } else if (data.equals("DropDownDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, DropDownActivity.class));
                } else if (data.equals("JniDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, JniDemosActivity.class));
                } else if (data.equals("AidlDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, AidlClientActivity.class));
                } else if (data.equals("FingerPayDemo")) {
                    startActivity(new Intent(SampleHomeActivity.this, FingerActivity.class));
                } else if (data.equals("ZxingPayDemo")) {
                    //运行时权限
                    if (ContextCompat.checkSelfPermission(SampleHomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SampleHomeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    } else {
                        //startActivityForResult(new Intent(SampleHomeActivity.this, CaptureActivity.class), 1001);

                        ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://capture");
                        // 注解方式下，这种方式添加页面进出场动画没有效果
                        //activityRoute.setAnimation(SampleHomeActivity.this, R.anim.in_from_left, R.anim.out_to_right);
                        activityRoute.withParams("name", "xyg-hello-注解");
                        activityRoute.withOpenMethodStartForResult(SampleHomeActivity.this, 1001);
                        activityRoute.open();

                        /*ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://capture");
                        activityRoute
                                .setAnimation(SampleHomeActivity.this, R.anim.in_from_left, R.anim.out_to_right)
                                .open();*/
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1001) {
            Bundle bundle = data.getExtras();
            if (null != bundle) {
                ToastUtils.showShort(this, bundle.getString("result"));
            }
        }
    }
}

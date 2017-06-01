package com.ranger.xyg.xygapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ranger.xyg.xygapp.ui.view.SlideBackLayout;

import butterknife.ButterKnife;

/**
 * Created by xyg on 2017/4/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean mIsSlideBackEnable = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentResId());
        initButterKnife();
        if (checkSlideBack()) {
            SlideBackLayout slideBackLayout = new SlideBackLayout(this);
            slideBackLayout.bindActivity();
        }
        initViews();
        initData();
    }

    protected void initData() {

    }

    private void initButterKnife() {
        ButterKnife.bind(this);
    }

    protected void initViews() {

    }

    protected abstract int getContentResId();

    protected boolean checkSlideBack() {
        return mIsSlideBackEnable;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}

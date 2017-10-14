package com.ranger.xyg.demos.finger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ranger.xyg.library.utils.ToastUtils;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xingyugang on 2017/10/9.
 */

public class FingerActivity extends BaseActivity {

    @BindView(R.id.tv_finger_status)
    TextView mFingerStatusTv;
    @BindView(R.id.btn_finger)
    Button mFingerBtn;

    private FingerPayManager mFingerManager;

    @Override
    protected int getContentResId() {
        return R.layout.activity_demo_finger;
    }

    @Override
    protected void initViews() {
        super.initViews();
        // 1、先判断本机是否支持指纹支付，即判断有没有指纹硬件
        mFingerManager = FingerPayManager.getInstance(this);
        if (mFingerManager == null) {
            mFingerStatusTv.setText(getString(R.string.txt_no_support_finger));
            return;
        }
        if (!mFingerManager.isHardwareSupport()) {
            mFingerStatusTv.setText(getString(R.string.txt_no_finger_hardware));
        }
    }

    @OnClick(R.id.btn_finger)
    public void onViewClicked() {
        if (FingerPayManager.getInstance(this) != null && FingerPayManager.getInstance(this).isHardwareSupport()) {
            startActivity(new Intent(this, FingerStartActivity.class));
        } else {
            ToastUtils.showShort(this, R.string.txt_no_support_finger);
        }
    }
}

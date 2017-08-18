package com.ranger.xyg.xygapp.demos.jni;

import android.widget.Button;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xyg on 2017/8/7.
 */

public class JniDemosActivity extends BaseActivity {

    @BindView(R.id.btn_do_jni)
    Button btnDoJni;
    @BindView(R.id.tv_jni_result)
    TextView tvJniResult;

    @Override
    protected int getContentResId() {
        return R.layout.activity_jni_demos;
    }

    @OnClick(R.id.btn_do_jni)
    public void onViewClicked() {
        NdkJniUtils jniUtils = new NdkJniUtils();
        tvJniResult.setText(jniUtils.getSampleName());
    }
}

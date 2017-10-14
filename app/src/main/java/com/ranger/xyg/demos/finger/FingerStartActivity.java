package com.ranger.xyg.demos.finger;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranger.xyg.library.utils.SPUtils;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.config.BCConstant;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xingyugang on 2017/10/10.
 */

public class FingerStartActivity extends BaseActivity {

    @BindView(R.id.tv_finger_agreement_tips2)
    TextView mFingerAgreementTipsTv;
    @BindView(R.id.cb_start_finger_pay)
    ImageView mStartFingerPayCb;

    @Override
    protected int getContentResId() {
        return R.layout.activity_finger_pay_start;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mFingerAgreementTipsTv.setText(getString(R.string.txt_finger_start_agreement));
        boolean hasStartFinger = SPUtils.getBoolean(this, BCConstant.FingerConstant.FINGER_START_KEY, false);
        setCheckStatus(hasStartFinger);
    }

    private void saveStartStatus(boolean isStart) {
        SPUtils.putBoolean(FingerStartActivity.this, BCConstant.FingerConstant.FINGER_START_KEY, isStart);
    }

    private void setCheckStatus(boolean isCheck) {
        if (isCheck) {
            mStartFingerPayCb.setImageResource(R.drawable.radio_selected);
        } else {
            mStartFingerPayCb.setImageResource(R.drawable.radio_unselected);
        }
    }

    @OnClick(R.id.cb_start_finger_pay)
    public void onViewClicked() {
        // 1、判断是否开启锁屏，如果未开启，跳开启系统的设置页面（调研下看能不能应用内直接设置锁屏密码或图案锁屏）
        if (!FingerPayManager.getInstance(FingerStartActivity.this).isKeyguardSecure()) {
            Intent intent =  new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
            return;
        }
        // 2、判断是否录入指纹到手机，如果未录入，提示跳转到系统相关录入页面
        if (!FingerPayManager.getInstance(FingerStartActivity.this).hasEnrolledFingerprints()) {
            AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(FingerStartActivity.this);
            alertDialogBuilder.setTitle("提示");
            alertDialogBuilder.setMessage("请先到手机系统中添加指纹，再开启支付宝指纹密码");
            alertDialogBuilder.setPositiveButton("去开通", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.show();
            return;
        }
        // 3、以上做完就可以在应用内验证指纹并开通指纹支付
    }
}

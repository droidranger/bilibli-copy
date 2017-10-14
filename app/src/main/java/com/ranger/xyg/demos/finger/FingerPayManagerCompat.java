package com.ranger.xyg.demos.finger;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import com.ranger.xyg.library.utils.ToastUtils;
import com.ranger.xyg.xygapp.R;

/**
 * Created by xingyugang on 2017/10/9.
 */

public class FingerPayManagerCompat {

    private FingerprintManagerCompat mFingerprintManager;
    private KeyguardManager mKeyManager;
    private CancellationSignal mCancellationSignal;
    static FingerPayManagerCompat mInstance;
    private Context mContext;
    private int mRedoCount = 5;

    private FingerPayManagerCompat(Context context) {
        mContext = context;
        mFingerprintManager = FingerprintManagerCompat.from(context);
        mKeyManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        mCancellationSignal = new CancellationSignal();
    }

    public static FingerPayManagerCompat getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FingerPayManagerCompat(context);
        }
        return mInstance;
    }

    public void startListening(FingerprintManagerCompat.CryptoObject cryptoObject) {
        mRedoCount = 5;
        mFingerprintManager.authenticate(cryptoObject, 0, mCancellationSignal, mCallback, null);
    }

    FingerprintManagerCompat.AuthenticationCallback mCallback = new FingerprintManagerCompat.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {// 验证出错回调。指纹传感器会关闭一段时间，在下次调用authenticate时会出现禁用期（时间依厂商不同 30s或1m都有）
            ToastUtils.showShort(mContext, R.string.txt_finger_pay_failed);
            showPayKeyboard();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            // 验证帮助回调
            ToastUtils.showShort(mContext, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            // 指纹识别成功
            ToastUtils.showShort(mContext, R.string.txt_finger_pay_success);
        }

        @Override
        public void onAuthenticationFailed() {// 指纹验证失败。指纹验证失败后，指纹传感器不会立即关闭指纹验证，系统会提供5次重试的机会，即调用5次onAuthenticationFailed后，才会调用onAuthenticationError
            // 此处可以处理一些验证次数递减的显示，例如：还有3次验证机会。验证失败的次数达到5次后，会调用onAuthenticationError回调，在onAuthenticationError可以做显示密码键盘的操作。
            ToastUtils.showShort(mContext, mContext.getString(R.string.txt_finger_redo_count, String.valueOf(mRedoCount)));
        }
    };

    /**
     * 显示支付键盘或手机锁屏
     */
    private void showPayKeyboard() {

    }

    public boolean canDoFinger() {
        if (!checkSelfPermission()) {
            return false;
        }
        if (!isHardwareSupport()) {
            return false;
        }
        return true;
    }

    public boolean checkSelfPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 判断本机是否支持指纹识别
     */
    public boolean isHardwareSupport() {
        return mFingerprintManager.isHardwareDetected();
    }

    /**
     * 判断是否开启锁屏密码
     */
    public boolean isKeyguardStart() {
        if (!mKeyManager.isKeyguardSecure()) {
            ToastUtils.showShort(mContext, R.string.txt_no_launch_keyguard);
            return false;
        }
        return true;
    }

    /**
     * 判断是否录入指纹
     */
    public boolean hasEnrolledFingerprints() {
        if (!mFingerprintManager.hasEnrolledFingerprints()) {
            ToastUtils.showShort(mContext, R.string.txt_no_import_finger);
            return false;
        }
        return true;
    }
}

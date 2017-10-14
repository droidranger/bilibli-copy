package com.ranger.xyg.demos.finger;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;

import com.ranger.xyg.library.utils.SPUtils;
import com.ranger.xyg.library.utils.ToastUtils;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.XygApplication;

/**
 * Created by xingyugang on 2017/10/9.
 */

public class FingerPayManager {

    private FingerprintManager mFingerprintManager;
    private KeyguardManager mKeyManager;
    private CancellationSignal mCancellationSignal;
    private static FingerPayManager mInstance;
    private Context mContext;
    private IFingerListener mListener;

    private FingerPayManager(Context context) {
        mContext = context;
        mFingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        mCancellationSignal = new CancellationSignal();
    }

    public static FingerPayManager getInstance(Context context) {
        if (!SPUtils.getBoolean(context, XygApplication.HAS_FINGER_API, false)) {
            return null;
        }
        if (mInstance == null) {
            mInstance = new FingerPayManager(context);
        }
        return mInstance;
    }

    public void addListener(IFingerListener listener) {
        mListener = listener;
    }


    public boolean isHardwareSupport() {
        if (mFingerprintManager == null) {
            return false;
        }
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT)
                            == PackageManager.PERMISSION_GRANTED
                && mFingerprintManager.isHardwareDetected();
    }

    public boolean isKeyguardSecure() {
        return mKeyManager.isKeyguardSecure();
    }

    public boolean hasEnrolledFingerprints() {
        if (mFingerprintManager == null) {
            return false;
        }
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT)
                            == PackageManager.PERMISSION_GRANTED
                && mFingerprintManager.hasEnrolledFingerprints();
    }

    public interface IFingerListener {
        int PERMISSION_CHECK = 1001;
        int HARDWARE_SUPPORT = 1002;
        int KEYGUARD_SECURE = 1003;
        int ENROLLED_FINGER = 1004;

        void onCheckResult(int checkFlag, boolean is);
    }

}

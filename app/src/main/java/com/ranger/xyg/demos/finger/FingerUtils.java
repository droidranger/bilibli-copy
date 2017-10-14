package com.ranger.xyg.demos.finger;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Created by xingyugang on 2017/10/10.
 */

public class FingerUtils {

    public FingerUtils(Context context) {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        fingerprintManagerCompat.isHardwareDetected();
    }
}

package com.ranger.xyg.xygapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by xyg on 2017/3/18.
 */

public class XygApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initFresco();
    }

    private void initFresco() {
        Fresco.initialize(this);
    }
}

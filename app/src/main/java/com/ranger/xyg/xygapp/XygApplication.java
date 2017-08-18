package com.ranger.xyg.xygapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ranger.xyg.library.config.AppConfigLib;

import org.xutils.x;

/**
 * Created by xyg on 2017/3/18.
 */

public class XygApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initFresco();
        initXUtils();
        initAppConfig();
    }

    private void initAppConfig() {
        AppConfigLib.initScreen(this.getApplicationContext());
    }

    private void initXUtils() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    private void initFresco() {
        Fresco.initialize(this);
    }
}

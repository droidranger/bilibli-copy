package com.ranger.xyg.xygapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ranger.xyg.library.config.AppConfigLib;
import com.ranger.xyg.library.utils.SPUtils;

import org.xutils.x;

import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.campusapp.router.Router;
import cn.campusapp.router.interceptor.Interceptor;

/**
 * Created by xyg on 2017/3/18.Ø
 */

public class XygApplication extends Application {

    public static final String HAS_FINGER_API = "HAS_FINGER_API";
    private static final HashMap<String, String> INTERCEPTOR_BLACK_LIST_SET = new LinkedHashMap<>();

    static {
        INTERCEPTOR_BLACK_LIST_SET.put("activity://intercepted/login", "activity://login");
        INTERCEPTOR_BLACK_LIST_SET.put("activity://intercepted/error", "activity://error");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initFresco();
        initXUtils();
        initAppConfig();
        initFinger();
        initRouter();
    }

    private void initRouter() {
        Router.initActivityRouter(getApplicationContext());
        /*Router.initActivityRouter(getApplicationContext(), new IActivityRouteTableInitializer() {
            @Override
            public void initRouterTable(Map<String, Class<? extends Activity>> router) {
                router.put("activity://capture/:{name}", CaptureActivity.class);
            }
        }, "activity", "activity2");*/
        Router.initBrowserRouter(getApplicationContext());
        Router.setInterceptor(new Interceptor() {
            @Override
            public boolean intercept(Context context, String url) {
                if (INTERCEPTOR_BLACK_LIST_SET.keySet().contains(url)) {
                    Router.open(context, INTERCEPTOR_BLACK_LIST_SET.get(url));
                    return true;
                }
                return false;
            }
        });
    }

    private void initFinger() {
        SharedPreferences sp = SPUtils.getSharedPreference(getApplicationContext());
        if (sp.contains(HAS_FINGER_API)) {
            return;
        }
        try {
            Class.forName("android.hardware.fingerprint.FingerprintManager"); // 通过反射判断是否存在该类
            SPUtils.putBoolean(getApplicationContext(), HAS_FINGER_API, true);
        } catch (ClassNotFoundException e) {
            SPUtils.putBoolean(getApplicationContext(), HAS_FINGER_API, false);
        }
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

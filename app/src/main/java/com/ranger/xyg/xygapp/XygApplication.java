package com.ranger.xyg.xygapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ranger.xyg.demos.SampleHomeActivity;
import com.ranger.xyg.demos.scroll.MyScrollActivity;
import com.ranger.xyg.demos.shortcuts.ShortcutsId;
import com.ranger.xyg.library.config.AppConfigLib;
import com.ranger.xyg.library.utils.SPUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.xutils.x;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.campusapp.router.Router;
import cn.campusapp.router.interceptor.Interceptor;

/**
 * Created by xyg on 2017/3/18.Ø
 */

public class XygApplication extends Application {

    public static final String HAS_FINGER_API = "HAS_FINGER_API";
    public static final String HAS_SHORTCUTS_API = "HAS_SHORTCUTS_API";
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
        initShortcuts();
        initSensorAnalytics();
    }

    // 数据接收的 URL
    final String SA_SERVER_URL = "YOUR_SERVER_URL";
    // 配置分发的 URL
    final String SA_CONFIGURE_URL = "YOUR_CONFIGURE_URL";
    // Debug 模式选项
    //   SensorsDataAPI.DebugMode.DEBUG_OFF - 关闭 Debug 模式
    //   SensorsDataAPI.DebugMode.DEBUG_ONLY - 打开 Debug 模式，校验数据，但不进行数据导入
    //   SensorsDataAPI.DebugMode.DEBUG_AND_TRACK - 打开 Debug 模式，校验数据，并将数据导入到 Sensors Analytics 中
    // 注意！请不要在正式发布的 App 中使用 Debug 模式！
    final SensorsDataAPI.DebugMode SA_DEBUG_MODE = SensorsDataAPI.DebugMode.DEBUG_OFF;
    private void initSensorAnalytics() {
        SensorsDataAPI.sharedInstance(
                this,                               // 传入 Context
                SA_SERVER_URL,                      // 数据接收的 URL
                SA_CONFIGURE_URL,                   // 配置分发的 URL
                SA_DEBUG_MODE);                     // Debug 模式选项
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

    private void initShortcuts() {
        SharedPreferences sp = SPUtils.getSharedPreference(getApplicationContext());
        if (sp.contains(HAS_SHORTCUTS_API)) {
            return;
        }
        try {
            Class.forName("android.content.pm.ShortcutManager"); // 通过反射判断是否存在该类
            addShortcuts();
            SPUtils.putBoolean(getApplicationContext(), HAS_SHORTCUTS_API, true);
        } catch (ClassNotFoundException e) {
            SPUtils.putBoolean(getApplicationContext(), HAS_SHORTCUTS_API, false);
        }
    }

    private void addShortcuts() {
        ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
        List<ShortcutInfo> infoList = shortcutManager.getDynamicShortcuts();
        String shortcutId = null;
        for (ShortcutInfo shortcutInfo : infoList) {
            if (ShortcutsId.WEB_DEV_CSDN.equals(shortcutInfo.getId())) {
                shortcutId = shortcutInfo.getId();
            }
        }
        if (shortcutId == null) {
            Intent intent = new Intent(this, SampleHomeActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            ShortcutInfo shortcut = new ShortcutInfo.Builder(getApplicationContext(), ShortcutsId.WEB_DEV_CSDN)
                    .setShortLabel(getString(R.string.short_label_csdn))
                    .setLongLabel(getString(R.string.long_label_csdn))
                    .setIcon(Icon.createWithResource(this, R.drawable.csdn_logo))
                    .setIntent(intent)
                    .build();
            shortcutManager.addDynamicShortcuts(Arrays.asList(shortcut));
        }
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

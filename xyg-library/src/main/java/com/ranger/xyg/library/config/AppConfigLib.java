package com.ranger.xyg.library.config;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by xyg on 2017/6/14.
 */

public class AppConfigLib {

    public static int sScreenWidth;
    public static int sScreenHeight;

    public static void initScreen(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        sScreenWidth = outMetrics.widthPixels;
        sScreenHeight = outMetrics.heightPixels;
    }

}

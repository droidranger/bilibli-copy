package com.ranger.xyg.xygapp.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ranger.xyg.xygapp.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyg on 2017/5/17.
 */

public class ExtendUtils {

    // 获取第三方app信息
    public static List<AppInfo> getAppList(Context context) {
        PackageManager pm = context.getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        List<AppInfo> list = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) { // 非系统应用
                AppInfo info = new AppInfo();
                info.appName = packageInfo.applicationInfo.loadLabel(pm)
                        .toString();
                info.pkgName = packageInfo.packageName;
                info.apkPath = packageInfo.applicationInfo.sourceDir;
                info.appIcon = packageInfo.applicationInfo.loadIcon(pm);
                // 获取该应用安装包的Intent，用于启动该应用
                info.appIntent = pm.getLaunchIntentForPackage(packageInfo.packageName);
                info.appVersion = packageInfo.versionName;
                list.add(info);
            }
        }
        return list;
    }
}

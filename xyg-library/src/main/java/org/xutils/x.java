package org.xutils;

import android.app.Application;
import android.content.Context;

import org.xutils.common.TaskController;
import org.xutils.common.task.TaskControllerImpl;
import org.xutils.db.DbManagerImpl;
import org.xutils.http.HttpManagerImpl;
import org.xutils.image.ImageManagerImpl;
import org.xutils.view.ViewInjectorImpl;

import java.lang.reflect.Method;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;


/**
 * Created by wyouflf on 15/6/10.
 * 任务控制中心, http, image, db, view注入等接口的入口.
 * 需要在在application的onCreate中初始化: x.Ext.init(this);
 */
public final class x {

    private x() {
    }

    public static boolean isDebug() {
        return Ext.debug;
    }

    public static Application app() {
        if (Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("please invoke x.Ext.init(app) on Application#onCreate()"
                        + " and register your Application in manifest.");
            }
        }
        return Ext.app;
    }

    public static TaskController task() {
        // Ext.taskController在Application中会初始化，任务控制器TaskControllerImpl是其唯一实现
        return Ext.taskController;
    }

    // 获取http管理类实例
    public static HttpManager http() {
        if (Ext.httpManager == null) {
            // 创建HttpManager实例为HttpManagerImpl，以后的http请求通过HttpManagerImpl来管理
            HttpManagerImpl.registerInstance();
        }
        return Ext.httpManager;
    }

    // 获取图片请求管理类实例ImageManagerImpl
    public static ImageManager image() {
        if (Ext.imageManager == null) {
            ImageManagerImpl.registerInstance();
        }
        return Ext.imageManager;
    }

    // 获取view注入器实例ViewInjectorImpl
    public static ViewInjector view() {
        if (Ext.viewInjector == null) {
            ViewInjectorImpl.registerInstance();
        }
        return Ext.viewInjector;
    }

    // 获取数据库管理类实例DbManagerImpl
    public static DbManager getDb(DbManager.DaoConfig daoConfig) {
        return DbManagerImpl.getInstance(daoConfig);
    }

    public static class Ext {
        private static boolean debug;
        private static Application app;
        private static TaskController taskController;
        private static HttpManager httpManager;
        private static ImageManager imageManager;
        private static ViewInjector viewInjector;

        private Ext() {
        }

        public static void init(Application app) {
            TaskControllerImpl.registerInstance();
            if (Ext.app == null) {
                Ext.app = app;
            }
        }

        public static void setDebug(boolean debug) {
            Ext.debug = debug;
        }

        public static void setTaskController(TaskController taskController) {
            if (Ext.taskController == null) {
                Ext.taskController = taskController;
            }
        }

        public static void setHttpManager(HttpManager httpManager) {
            Ext.httpManager = httpManager;
        }

        public static void setImageManager(ImageManager imageManager) {
            Ext.imageManager = imageManager;
        }

        public static void setViewInjector(ViewInjector viewInjector) {
            Ext.viewInjector = viewInjector;
        }

        public static void setDefaultHostnameVerifier(HostnameVerifier hostnameVerifier) {
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        }
    }

    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}

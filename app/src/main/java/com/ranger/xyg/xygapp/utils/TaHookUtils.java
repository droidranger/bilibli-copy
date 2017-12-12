package com.ranger.xyg.xygapp.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by xingyugang on 2017/11/10.
 */

public class TaHookUtils {

    private static final String VIEW_CLASS = "android.view.View";

    // 该方法要在setOnClickListener之后调用
    public static void hookListener(Activity activity, TaHookUtils.OnClickListener clickListener){
        if (activity != null) {
            View decorView = activity.getWindow().getDecorView();
            getView(decorView, clickListener);
        }
    }

    private static void getView(View view, TaHookUtils.OnClickListener clickListener) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                getView(((ViewGroup) view).getChildAt(i), clickListener);
            }
        }
        viewHook(view, clickListener);
    }

    private static void viewHook(View view, TaHookUtils.OnClickListener clickListener) {
        try {
            Class viewcls = Class.forName(VIEW_CLASS);// 反射创建View
            Field listenerInfoField = viewcls.getDeclaredField("mListenerInfo");
            listenerInfoField.setAccessible(true);
            // ListenerInfo--->View对象中的mListenerInfo的实例
            Object listenerInfo = listenerInfoField.get(view);
            if (listenerInfo != null) {
                // 反射创建ListenerInfo
                Class listenerInfoCls = Class.forName("android.view.View$ListenerInfo");
                // 获取ListenerInfo属性mOnClickListener
                Field onClickListenerField = listenerInfoCls.getDeclaredField("mOnClickListener");
                onClickListenerField.setAccessible(true);
                // 获取mListenerInfo的实例中的mOnClickListener实例
                View.OnClickListener click = (View.OnClickListener) onClickListenerField.get(listenerInfo);
                if (click != null) {
                    View.OnClickListener onClickListenerProxy = new OnClickListenerProxy(click, clickListener);
                    // 设置ListenerInfo属性mOnClickListener为我们的代理listener
                    onClickListenerField.set(listenerInfo, onClickListenerProxy);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static class OnClickListenerProxy implements View.OnClickListener {
        private View.OnClickListener mObject;
        private TaHookUtils.OnClickListener mListener;

        public OnClickListenerProxy(View.OnClickListener object, TaHookUtils.OnClickListener clickListener) {
            mObject = object;
            mListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.beforeInListener(v);
            }
            if (mObject != null) {
                mObject.onClick(v);
            }
            if (mListener != null) {
                mListener.afterInListener(v);
            }
        }
    }

    public interface OnClickListener {
        void beforeInListener(View view);
        void afterInListener(View view);
    }
}

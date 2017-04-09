package com.ranger.xyg.xygapp.manager;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyg on 2017/3/23.
 */

public class JSBridge {
    private static Map<String, HashMap<String, Method>> exposedMethods = new HashMap<>();

    public static void register(String exposedName, Class<? extends IBridge> clazz) {
        if (!exposedMethods.containsKey(exposedName)) {
            exposedMethods.put(exposedName, getAllMethod(clazz));
        }
    }

    private static HashMap<String, Method> getAllMethod(Class<? extends IBridge> injectedCls) {
        HashMap<String, Method> methodMap = new HashMap<>();
        Method[] methods = injectedCls.getDeclaredMethods();
        for (Method method : methods) {
            String name;
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (name = method.getName()) == null) {
                continue;
            }
            Class[] parameters = method.getParameterTypes();
            if (null != parameters && parameters.length == 3) {
                if (parameters[0] == WebView.class && parameters[1] == JSONObject.class && parameters[2] == Callback.class) {
                    methodMap.put(name, method);
                }
            }
        }
        return methodMap;
    }

    public static String callJava(WebView webView, String uriString) {
        String methodName = "";
        String className = "";
        String param = "{}";
        String port = "";
        if (!TextUtils.isEmpty(uriString) && uriString.startsWith("JSBridge")) {
            Uri uri = Uri.parse(uriString);
            className = uri.getHost();
            param = uri.getQuery();
            port = String.valueOf(uri.getPort());
            String path = uri.getPath();
            if (!TextUtils.isEmpty(path)) {
                methodName = path.replace("/", "");
            }
        }

        if (exposedMethods.containsKey(className)) {
            HashMap<String, Method> methodHashMap = exposedMethods.get(className);
            if (methodHashMap != null && methodHashMap.size() != 0 && methodHashMap.containsKey(methodName)) {
                Method method = methodHashMap.get(methodName);
                if (method != null) {
                    try {
                        method.invoke(null, webView, new JSONObject(param), new Callback(webView, port));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }
}

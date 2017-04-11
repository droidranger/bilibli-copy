package com.ranger.xyg.xygapp.bean;

/**
 * Created by xyg on 2017/4/10.
 */

public class HttpConfig {

    static final String mBaseUrl = "http://live.bilibili.com/";

    private static String build(String path) {
        return mBaseUrl + path;
    }

    public static final String LIVE_RECOMMEND_URL = build("AppNewIndex/recommend");
}

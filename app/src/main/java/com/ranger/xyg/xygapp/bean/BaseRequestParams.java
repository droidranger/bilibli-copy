package com.ranger.xyg.xygapp.bean;

import org.xutils.http.RequestParams;

/**
 * Created by xyg on 2017/4/10.
 */

public class BaseRequestParams extends RequestParams {

    public BaseRequestParams(String url) {
        super(url);
    }

    public String _device = "android";
    public String access_key = "e4b54b4866aed1c32630622df63edd6b";
    public String appkey = "1d8b6e7d45233436";
    public String build = "502000";
    public String mobi_app = "android";
    public String platform = "android";
    public String scale = "xxhdpi";
    public String ts = "1491830845000";
    public String sign = "c200860fb105bb2f97bf8f6f77d0cac3";
}

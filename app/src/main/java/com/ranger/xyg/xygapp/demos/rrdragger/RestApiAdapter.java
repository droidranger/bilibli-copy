package com.ranger.xyg.xygapp.demos.rrdragger;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xyg on 2017/6/19.
 */

public class RestApiAdapter {

    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
            OkHttpClient okHttpClient = new OkHttpClient();
            OkHttpClient.Builder builder = okHttpClient.newBuilder();
            builder.retryOnConnectionFailure(true);
            /*retrofit = new Retrofit.Builder().client(okHttpClient)
                    .baseUrl(Con)*/
        }
        return null;
    }
}

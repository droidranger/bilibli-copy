package com.ranger.xyg.xygapp.model.http;

import android.util.Log;

import com.ranger.xyg.xygapp.bean.image.ResultData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xyg on 2017/6/14.
 */

public class RetrofitUtils {

    public void loadData() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(HttpConstant.TUNIU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TuniuTPCrossApi gitHubApi = retrofit.create(TuniuTPCrossApi.class);
        Call<ResultData> call = gitHubApi.tuniuCross();

        call.enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                ResultData data = response.body();
            }

            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {
                Log.e("ygxing", "onFailure");
            }
        });
    }
}

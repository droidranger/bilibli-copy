package com.ranger.xyg.demos.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ranger.xyg.xygapp.R;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xyg on 2017/5/5.
 */

public class RetrofitDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_retrofit);
        loadData("square", "retrofit");
    }

    private void loadData(String userName, String repo) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);
        Call<List<Contributor>> call = gitHubApi.contributorsBySimpleGetCall(userName, repo);

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                List<Contributor> data = response.body();
                for (Contributor contributor : data) {
                    Log.e("ygxing", "login--->" + contributor.getLogin());
                    Log.e("ygxing", "contributions--->" + contributor.getContributions());
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Log.e("ygxing", "onFailure");
            }
        });
    }

}

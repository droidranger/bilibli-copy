package com.ranger.xyg.xygapp.demos.glide;

import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ranger.xyg.library.config.AppConfigLib;
import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.bean.image.ResultData;
import com.ranger.xyg.xygapp.model.http.HttpConstant;
import com.ranger.xyg.xygapp.model.http.TuniuTPCrossApi;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;

import java.net.URL;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ranger.xyg.xygapp.config.TestImageUrls.URLS;

/**
 * Created by xyg on 2017/6/14.
 */

public class GlideDemoActivity extends BaseActivity {

    @BindView(R.id.iv_image_view)
    ImageView ivImageView;

    @Override
    protected int getContentResId() {
        return R.layout.activity_glide_demo;
    }

    @Override
    protected void initViews() {
        super.initViews();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivImageView.getLayoutParams();
        params.width = AppConfigLib.sScreenWidth;
        params.height = params.width * 2 / 3;
        ivImageView.setLayoutParams(params);

        Glide.with(this).load(URLS[0])
                // 设置placeholder
                .placeholder(R.drawable.image_placeholder_square_round)
                // 设置加载失败的图片
                .error(R.drawable.image_placeholder_square_round)
                // 支持缩略图
                .thumbnail(0.1f)
                .into(ivImageView);
    }

    // 显示gif图片
    private void showGif() {
        Glide.with(this).load(URLS[1])
                // 加载gif静态图片
                //.asBitmap()
                .asGif() // 加载gif动态图片
                .into(ivImageView);
    }

    private void lru() {

    }


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

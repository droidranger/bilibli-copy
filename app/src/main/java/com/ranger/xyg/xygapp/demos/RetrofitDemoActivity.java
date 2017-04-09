package com.ranger.xyg.xygapp.demos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.manager.BridgeImpl;
import com.ranger.xyg.xygapp.manager.JSBridge;
import com.ranger.xyg.xygapp.manager.JSBridgeWebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by xyg on 2017/3/23.
 */

public class RetrofitDemoActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge_test);
        ButterKnife.bind(this);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new JSBridgeWebChromeClient());
        mWebView.loadUrl("file:///android_asset/index.html");
        JSBridge.register("bridge", BridgeImpl.class);
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("").build();
    }
}





































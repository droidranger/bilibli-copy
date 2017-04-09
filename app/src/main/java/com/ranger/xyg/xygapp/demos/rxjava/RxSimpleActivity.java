package com.ranger.xyg.xygapp.demos.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.activity.BaseActivity;
import com.ranger.xyg.xygapp.utils.log.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xyg on 2017/4/7.
 */

public class RxSimpleActivity extends BaseActivity {


    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.btn_next_page)
    Button btnNextPage;


    @Override
    protected int getContentResId() {
        return R.layout.activity_rx_simple;
    }

    @Override
    protected void initViews() {
        // 注册观察活动
        Observable<String> observable = Observable.create(mObsAction);
        // 分发订阅信息
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(mTextSubscriber);
        observable.subscribe(mToastSubscriber);
    }

    // 观察者事件发生
    Observable.OnSubscribe mObsAction = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext(sayMyName());// 发送事件
            subscriber.onCompleted(); // 完成事件
        }
    };

    private String sayMyName() {
        return "Hello, I am your friend.";
    }

    // 订阅者，接收字符串，修改控件
    Subscriber<String> mTextSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            tvText.setText(s);
        }
    };

    // 订阅者，接收字符串，提示信息
    Subscriber<String> mToastSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            ToastUtils.showShort(RxSimpleActivity.this, s);
        }
    };

    @OnClick(R.id.btn_next_page)
    public void onNextPage() {
        startActivity(new Intent(this, MoreRxDemosActivity.class));
    }
}
































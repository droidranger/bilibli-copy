package com.ranger.xyg.xygapp.demos.handler;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xyg on 2017/5/15.
 */

public class HandlerDemoActivity extends AppCompatActivity {

    private HandlerThread mHandlerThread;
    private Handler mHandler;

    private UpdateService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        handlerThreadMananger();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            mService = new UpdateService("test2");
            mService.startService(new Intent());
        }
    }

    private void handlerThreadMananger() {
        mHandlerThread = new HandlerThread("test1", Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        // add task1
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // task1
            }
        });
        // add task2
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // task2
            }
        });

    }

    private class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            };
            Looper.loop();
        }
    }
}

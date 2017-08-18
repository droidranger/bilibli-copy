package com.ranger.xyg.xygapp.model.process.hthread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

/**
 * Created by xyg on 2017/5/17.
 */

public class HandlerThreadProcess<T, Result> {

    private final Handler mHandler;
    private final Handler mMainHandler;
    private final HandlerThread mHandlerThread;
    private IProcessListener<T, Result> mListener;

    public HandlerThreadProcess(Looper mainLooper) throws IllegalAccessException {
        if (mainLooper == null) {
            throw new IllegalAccessException("mainLooper can not null");
        }
        mHandlerThread = new HandlerThread("app_list", Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mMainHandler = new Handler(mainLooper);
    }

    public void registerListener(IProcessListener<T, Result> listener) {
        mListener = listener;
    }

    public void process(final T t) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    final Result result = mListener.onProcessing(t);
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onPostProcess(result);
                        }
                    });
                }

            }
        });
    }

    public interface IProcessListener<T, Result> {
        Result onProcessing(T t);
        void onPostProcess(Result result);
    }
}

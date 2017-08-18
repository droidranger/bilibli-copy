package com.ranger.xyg.xygapp.demos.glide;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by xyg on 2017/6/29.
 */

public class MyLruCache extends LruCache<String, Bitmap> {

    private static MyLruCache mInstance;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private MyLruCache(int maxSize) {
        super(maxSize);
    }

    public static MyLruCache newInstance() {
        if (mInstance == null) {
            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            int cacheSize = maxMemory / 8;
            mInstance = new MyLruCache(cacheSize);
        }
        return mInstance;
    }

    @Override
    protected Bitmap create(String key) {
        return super.create(key);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }
}

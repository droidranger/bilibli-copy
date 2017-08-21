package com.ranger.xyg.demos.handler;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

/**
 * Created by xyg on 2017/5/15.
 */

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class UpdateService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}

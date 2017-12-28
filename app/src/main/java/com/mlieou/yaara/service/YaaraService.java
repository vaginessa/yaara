package com.mlieou.yaara.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by mengdi on 12/26/17.
 */

public class YaaraService extends Service {

    private final IBinder mBinder = new YaaraServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
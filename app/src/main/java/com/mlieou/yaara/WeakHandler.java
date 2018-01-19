package com.mlieou.yaara;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by mlieou on 1/16/18.
 */

public class WeakHandler extends Handler {
    private WeakReference<HandlerCallback> mWeakRef;

    public WeakHandler(HandlerCallback handlerCallback) {
        this.mWeakRef = new WeakReference<HandlerCallback>(handlerCallback);
    }

    public WeakHandler(Looper looper, HandlerCallback handlerCallback) {
        super(looper);
        this.mWeakRef = new WeakReference<HandlerCallback>(handlerCallback);
    }

    @Override
    public void handleMessage(Message msg) {
        HandlerCallback handlerCallback = mWeakRef.get();
        if (handlerCallback != null) {
            handlerCallback.handleMessage(msg, this);
        }
    }
}

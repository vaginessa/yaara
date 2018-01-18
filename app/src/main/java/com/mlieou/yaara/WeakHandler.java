package com.mlieou.yaara;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by mengdi on 1/16/18.
 */

public class WeakHandler extends Handler {
    private WeakReference<HandlerInterface> mWeakRef;

    public WeakHandler(HandlerInterface handlerInterface) {
        this.mWeakRef = new WeakReference<HandlerInterface>(handlerInterface);
    }

    public WeakHandler(Looper looper, HandlerInterface handlerInterface) {
        super(looper);
        this.mWeakRef = new WeakReference<HandlerInterface>(handlerInterface);
    }

    @Override
    public void handleMessage(Message msg) {
        HandlerInterface handlerInterface = mWeakRef.get();
        if (handlerInterface != null) {
            handlerInterface.handleMessage(msg, this);
        }
    }
}

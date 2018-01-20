package com.mlieou.yaara.core;

import android.os.Handler;
import android.os.Message;

/**
 * Created by mlieou on 1/16/18.
 */

public interface HandlerCallback {
    void handleMessage(Message msg, Handler handler);
}

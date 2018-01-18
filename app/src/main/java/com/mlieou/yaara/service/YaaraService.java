package com.mlieou.yaara.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.mlieou.yaara.HandlerInterface;
import com.mlieou.yaara.YaaraClientManager;
import com.mlieou.yaara.ServerPreferencesManager;
import com.mlieou.yaara.WeakHandler;
import com.mlieou.yaara.model.ServerProfile;

/**
 * Created by mengdi on 12/26/17.
 */

public class YaaraService extends Service implements HandlerInterface {
    private static final String HANDLER_THREAD_NAME = "HandlerThread";

    private Handler mAPIHandler;
    private HandlerThread mAPIHandlerThread;
    private ServerPreferencesManager mServerPreferencesManager;
    private YaaraClientManager mClientManager;
    private Messenger mAPIHandlerThreadMessenger;

    @Override
    public void onCreate() {
        mAPIHandlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        mAPIHandlerThread.start();

        mAPIHandler = new WeakHandler(mAPIHandlerThread.getLooper(), this);
        mAPIHandlerThreadMessenger = new Messenger(mAPIHandler);

        mServerPreferencesManager = new ServerPreferencesManager(this);
        mClientManager = new YaaraClientManager(mServerPreferencesManager);
        ServerProfile profile = new ServerProfile(mServerPreferencesManager);
        mClientManager.initServer(profile);
    }

    @Override
    public void handleMessage(Message msg, Handler handler) {
        try {
            Messenger messenger = msg.replyTo;
            mClientManager.setHandlerMessenger(messenger);
            mClientManager.handleMessage(msg, handler);
        } catch (RemoteException e) {
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAPIHandlerThreadMessenger.getBinder();
    }

}

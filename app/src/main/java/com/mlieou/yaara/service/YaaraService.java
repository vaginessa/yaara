package com.mlieou.yaara.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.mlieou.yaara.YaaraClient;
import com.mlieou.yaara.activity.MainActivity;
import com.mlieou.yaara.model.ServerProfile;
import com.mlieou.yaara.rpc.aria2.Aria2RpcClient;
import com.mlieou.yaara.model.GlobalStatus;

/**
 * Created by mengdi on 12/26/17.
 */

public class YaaraService extends IntentService {

    private static final String SERVICE_NAME = "YaaraService";

    public static final String GLOBAL_STATUS = "global_status";

    private Handler mUpdateHandler;
    private HandlerThread handlerThread;
    private LocalBroadcastManager manager;

    private ServerProfile profile = new ServerProfile("", "10.24.233.100", 6800, "jsonrpc", "", ServerProfile.Protocol.HTTP, ServerProfile.RequestMethod.POST);
    private YaaraClient mClient = new YaaraClient(profile);

    private Runnable fetchGlobalStatus = new Runnable() {
        @Override
        public void run() {
            try {
                GlobalStatus status = mClient.getGlobalStatus();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setAction(GLOBAL_STATUS);
                intent.putExtra(GLOBAL_STATUS, status);
                manager.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mUpdateHandler.postDelayed(this , 1000);
        }
    };

    public YaaraService() {
        super(SERVICE_NAME);
        manager = LocalBroadcastManager.getInstance(this);
        handlerThread = new HandlerThread("handler");
        handlerThread.start();
        mUpdateHandler = new Handler(handlerThread.getLooper());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        switch (intent.getAction()) {
            case GLOBAL_STATUS:
                mUpdateHandler.postDelayed(fetchGlobalStatus, 1000);
        }
    }


}

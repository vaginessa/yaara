package com.mlieou.yaara.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.mlieou.yaara.activity.MainActivity;
import com.mlieou.yaara.aria2RPC.Aria2RpcClient;
import com.mlieou.yaara.aria2RPC.model.Aria2GlobalStat;

/**
 * Created by mengdi on 12/26/17.
 */

public class YaaraService extends IntentService {

    public static final String GLOBAL_STATUS = "global_status";

    public static final String GLOBAL_STATUS_DOWNLOAD = GLOBAL_STATUS + "_download";
    public static final String GLOBAL_STATUS_UPLOAD = GLOBAL_STATUS + "_upload";

    Aria2RpcClient mClient;
    Handler mUpdateHandler;
    HandlerThread handlerThread;
    LocalBroadcastManager manager;

    private Runnable fetchDownload = new Runnable() {
        @Override
        public void run() {
            try {
                Aria2GlobalStat status = mClient.getGlobalStat();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setAction(GLOBAL_STATUS);
                intent.putExtra(GLOBAL_STATUS_DOWNLOAD, status.getDownloadSpeed());
                intent.putExtra(GLOBAL_STATUS_UPLOAD, status.getUploadSpeed());
                manager.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mUpdateHandler.postDelayed(this , 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public YaaraService() {
        super("WTF");
        mClient = new Aria2RpcClient("10.24.233.100", 6800, "jsonrpc");
        manager = LocalBroadcastManager.getInstance(this);
        handlerThread = new HandlerThread("handler");
        handlerThread.start();
        mUpdateHandler = new Handler(handlerThread.getLooper());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        switch (intent.getAction()) {
            case GLOBAL_STATUS:
                mUpdateHandler.postDelayed(fetchDownload, 1000);
        }
    }


}

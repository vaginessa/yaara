package com.mlieou.yaara.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mlieou.yaara.YaaraClient;
import com.mlieou.yaara.activity.MainActivity;
import com.mlieou.yaara.model.ServerProfile;
import com.mlieou.yaara.model.GlobalStatus;

/**
 * Created by mengdi on 12/26/17.
 */

public class YaaraService extends IntentService {

    private static final String TAG = "YaaraService";

    private static final String SERVICE_NAME = "YaaraService";

    // intent filter
    public static final String UPDATE_GLOBAL_STATUS = SERVICE_NAME + ".updateGlobalStatus";

    // actions
    public static final String START_UPDATE_GLOBAL_STATUS = UPDATE_GLOBAL_STATUS + ".start";
    public static final String STOP_UPDATE_GLOBAL_STATUS = UPDATE_GLOBAL_STATUS + ".stop";

    // extra
    public static final String EXTRA_GLOBAL_STATUS = "globalStatus";



    private boolean mUpdateGlobalStatus;
    private boolean mUpdateActiveTasks;
    private boolean mUpdateStoppedTasks;
    private boolean mUpdateWaitingTasks;

    private Handler mUpdateHandler;
    private LocalBroadcastManager manager;

    private ServerProfile profile = new ServerProfile("", "10.24.233.100", 6800, "jsonrpc", "", ServerProfile.Protocol.HTTP, ServerProfile.RequestMethod.POST);
    private YaaraClient mClient = new YaaraClient(profile);

    private long updateInterval = 1000;

    private Runnable updateGlobalStatusRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                GlobalStatus status = mClient.getGlobalStatus();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setAction(UPDATE_GLOBAL_STATUS);
                intent.putExtra(EXTRA_GLOBAL_STATUS, status);
                //manager.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i(TAG, "run: running " + mUpdateGlobalStatus);
            if (mUpdateGlobalStatus)
                mUpdateHandler.postDelayed(this, updateInterval);
        }
    };

    public YaaraService() {
        super(SERVICE_NAME);
        manager = LocalBroadcastManager.getInstance(this);
        mUpdateHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        switch (intent.getAction()) {
            case START_UPDATE_GLOBAL_STATUS:
                mUpdateGlobalStatus = true;
                mUpdateHandler.postDelayed(updateGlobalStatusRunnable, updateInterval);
                break;
            case STOP_UPDATE_GLOBAL_STATUS:
                Log.i(TAG, "onHandleIntent: Stop signal");
                mUpdateGlobalStatus = false;
        }
    }


}

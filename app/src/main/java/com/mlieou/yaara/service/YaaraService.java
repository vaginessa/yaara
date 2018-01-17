package com.mlieou.yaara.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.mlieou.yaara.YaaraClient;
import com.mlieou.yaara.model.ServerProfile;

/**
 * Created by mengdi on 12/26/17.
 */

public class YaaraService extends Service {

    public static final class Request {
        public static final String GET_GLOBAL_STATUS = "get_global_status";
        public static final String GET_ACTIVE_TASK = "get_active_task";
        public static final String GET_WAITING_TASK = "get_waiting_task";
        public static final String GET_STOPPED_TASK = "get_stopped_task";
    }

    public static final class Response {
        public static final String ACTIVE_TASK = "active_task";
        public static final String STOPPED_TASK = "stopped_task";
        public static final String WAITING_TASK = "waiting_task";
    }

//    public static final String DATA_TYPE = "data_type";
//
//    public static final class DataType {
//        public static final String GLOBAL_STATUS = "global_status";
//    }
//
//    private static final String TAG = "YaaraService";
//
//
//
//    public static final String RESULT = "result";
//
//    // intent filter
//    public static final String INTENT_SERVICE_COMPLETED = SERVICE_NAME + ".completed";
//
//
//
//    private ServerProfile profile = new ServerProfile("", "10.24.233.100", 6800, "jsonrpc", "", ServerProfile.Protocol.HTTP, ServerProfile.RequestMethod.POST);
//    private YaaraClient mClient = new YaaraClient(profile);

    private static final String SERVICE_NAME = "YaaraService";

    public YaaraService() {
        super(SERVICE_NAME);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent == null ? "" : intent.getAction();
        Intent response = new Intent(INTENT_SERVICE_COMPLETED);
        switch (action) {
            case Request.GET_GLOBAL_STATUS:
                response.putExtra(DATA_TYPE, DataType.GLOBAL_STATUS);
                response.putExtra(RESULT, mClient.getGlobalStatus());
                break;
            case Request.GET_WAITING_TASK:
                break;
            case Request.GET_ACTIVE_TASK:
                break;
            case Request.GET_STOPPED_TASK:
                break;
            default:
                break;
        }
        // send broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(response);
    }

}

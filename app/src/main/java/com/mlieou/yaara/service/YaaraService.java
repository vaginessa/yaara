package com.mlieou.yaara.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.mlieou.yaara.HandlerInterface;
import com.mlieou.yaara.Aria2Manager;
import com.mlieou.yaara.ServerManager;
import com.mlieou.yaara.WeakHandler;
import com.mlieou.yaara.model.ServerProfile;

/**
 * Created by mengdi on 12/26/17.
 */

public class YaaraService extends Service implements HandlerInterface {

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
    private static final String HANDLER_THREAD_NAME = "HandlerThread";

    private Handler mHandler;
    private HandlerThread mThread;
    private ServerManager mServerManager;
    private Aria2Manager mManager;
    private Messenger mMessenger;

    @Override
    public void onCreate() {
        mThread = new HandlerThread(HANDLER_THREAD_NAME);
        mThread.start();

        mHandler = new WeakHandler(mThread.getLooper(), this);
        mMessenger = new Messenger(mHandler);

        mServerManager = new ServerManager(this);
        mManager = new Aria2Manager(mServerManager);
        ServerProfile profile = new ServerProfile(mServerManager);
        mManager.initServer(profile);
    }

    @Override
    public void handleMessage(Message msg, Handler handler) {
        Messenger messenger = msg.replyTo;
        // TODO
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


}

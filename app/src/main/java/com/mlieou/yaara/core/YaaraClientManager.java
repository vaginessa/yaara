package com.mlieou.yaara.core;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

import com.mlieou.yaara.constant.MessageCode;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.ServerProfile;
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.model.TaskType;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by mlieou on 1/16/18.
 */

public class YaaraClientManager implements MessageCode {

    private static final String TAG = "YaaraClientManager";

    private YaaraClient mClient;
    private Messenger mMessenger;
    private ServerProfileManager mServerProfileManager;
    private ServerProfile mProfile;

    public YaaraClientManager(ServerProfileManager manager) {
        this.mServerProfileManager = manager;
    }

    public void initServer() {
        mProfile = mServerProfileManager.getActiveServerProfile();
        mClient = new YaaraClient(mProfile);
    }

    public void setHandlerMessenger(Messenger messenger) {
        mMessenger = messenger;
    }

    public void handleMessage(Message msg, Handler handler) throws RemoteException {
        Message messageToSend = new Message();
        try {
            switch (msg.what) {
                case RELOAD_SERVER_PROFILE:
                    initServer();
                    break;
                case GET_GLOBAL_STATUS:
                    messageToSend.what = UPDATE_GLOBAL_STATUS;
                    messageToSend.obj = mClient.getGlobalStatus();
                    break;
                case GET_ACTIVE_TASKS:
                    messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                    messageToSend.obj = handleTaskRequest(TaskType.ACTIVE);
                    break;
                case GET_WAITING_TASKS:
                    messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                    messageToSend.obj = handleTaskRequest(TaskType.WAITING);
                    break;
                case GET_STOPPED_TASKS:
                    messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                    messageToSend.obj = handleTaskRequest(TaskType.STOPPED);
                    break;
                case ADD_HTTP_TASK:
                    messageToSend.what = HTTP_TASK_ADDED;
                    messageToSend.obj = mClient.addUri((String) msg.obj);
                    break;
                case GET_TASK_STATUS:
                    messageToSend.what = UPDATE_TASK_STATUS;
                    messageToSend.obj = mClient.getTaskStatus((String) msg.obj);
                    break;
                case GET_TASK_PEERS:
                    messageToSend.what = UPDATE_TASK_PEERS;
                    messageToSend.obj = mClient.getPeers((String) msg.obj);
                case START_TASK:
                    break;
                case PAUSE_TASK:
                    break;
                case REMOVE_TASK:
                    break;
            }
            mMessenger.send(messageToSend);
        } catch (Exception e) {
            Log.i(TAG, "handleMessage: ");
            e.printStackTrace();
        }
    }

    private Pair<List<TaskStatus>, GlobalStatus> handleTaskRequest(TaskType type) throws RemoteException, IOException, JSONException {
        Pair<List<TaskStatus>, GlobalStatus> pair;
        List<TaskStatus> list = mClient.getTaskStatusLiteList(type);
        GlobalStatus status = mClient.getGlobalStatus();
        pair = new Pair<>(list, status);
        return pair;
    }
}

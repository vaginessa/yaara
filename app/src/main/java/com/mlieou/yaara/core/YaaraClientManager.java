package com.mlieou.yaara.core;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.mlieou.yaara.constant.MessageCode;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.RefreshBundle;
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
                    messageToSend.obj = mClient.addHttpTask((String) msg.obj);
                    break;
                case START_TASK:
                    break;
                case PAUSE_TASK:
                    break;
                case REMOVE_TASK:
                    break;
            }
            mMessenger.send(messageToSend);
        } catch (Exception e) {

        }
    }

    private RefreshBundle handleTaskRequest(TaskType type) throws RemoteException, IOException, JSONException {
        RefreshBundle bundle;
        List<TaskStatus> list = mClient.getTaskStatusLiteList(type);
        GlobalStatus status = mClient.getGlobalStatus();
        bundle = new RefreshBundle(list, status);
        return bundle;
    }
}

package com.mlieou.yaara.core;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.mlieou.yaara.constant.MessageCode;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.RefreshBundle;
import com.mlieou.yaara.model.ServerProfile;
import com.mlieou.yaara.model.TaskStatusLite;
import com.mlieou.yaara.model.TaskType;

import java.util.List;

/**
 * Created by mlieou on 1/16/18.
 */

public class YaaraClientManager implements MessageCode {
    private YaaraClient mClient;
    private Messenger mMessenger;
    private ServerPreferencesManager mServerPreferencesManager;
    private ServerProfile mProfile;

    public YaaraClientManager(ServerPreferencesManager manager) {
        this.mServerPreferencesManager = manager;
        // TODO
        mClient = new YaaraClient(mServerPreferencesManager.getDefaultServerProfile());
    }

    public void initServer(ServerProfile profile) {
        mProfile = profile;
        mClient = new YaaraClient(mProfile);
    }

    public void setHandlerMessenger(Messenger messenger) {
        mMessenger = messenger;
    }

    public void handleMessage(Message msg, Handler handler) throws RemoteException {
        Message messageToSend = new Message();
        switch (msg.what) {
            case GET_GLOBAL_STATUS:
                messageToSend.what = UPDATE_GLOBAL_STATUS;
                messageToSend.obj = mClient.getGlobalStatus();
                mMessenger.send(messageToSend);
                break;
            case GET_ACTIVE_TASKS:
                messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                messageToSend.obj = handleTaskRequest(TaskType.ACTIVE);
                mMessenger.send(messageToSend);
                break;
            case GET_WAITING_TASKS:
                messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                messageToSend.obj = handleTaskRequest(TaskType.WAITING);
                mMessenger.send(messageToSend);
                break;
            case GET_STOPPED_TASKS:
                messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                messageToSend.obj = handleTaskRequest(TaskType.STOPPED);
                mMessenger.send(messageToSend);
                break;
            case ADD_HTTP_TASK:
                break;
            case START_TASK:
                break;
            case PAUSE_TASK:
                break;
            case REMOVE_TASK:
                break;
        }
    }

    private RefreshBundle handleTaskRequest(TaskType type) throws RemoteException {
        RefreshBundle bundle;
        List<TaskStatusLite> list = mClient.getTaskStatusLiteList(type);
        GlobalStatus status = mClient.getGlobalStatus();
        bundle = new RefreshBundle(list, status);
        return bundle;
    }
}

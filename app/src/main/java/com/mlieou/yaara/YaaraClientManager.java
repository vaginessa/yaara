package com.mlieou.yaara;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mengdi on 1/16/18.
 */

public class YaaraClientManager implements MessageCode {
    private YaaraClient mClient;
    private Messenger mMessenger;
    private ServerPreferencesManager mServerPreferencesManager;
    private ServerProfile mProfile;

    private boolean mUpdatingStatus = false;

    public YaaraClientManager(ServerPreferencesManager manager) {
        this.mServerPreferencesManager = manager;
    }

    public void initServer(ServerProfile profile) {
        mProfile = profile;
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
                messageToSend.obj = test(TaskType.ACTIVE);
                mMessenger.send(messageToSend);
                //handleTaskRequest(TaskType.ACTIVE);
                break;
            case GET_WAITING_TASKS:
                messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                messageToSend.obj = test(TaskType.WAITING);
                mMessenger.send(messageToSend);
                //handleTaskRequest(TaskType.WAITING);
                break;
            case GET_STOPPED_TASKS:
                messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
                messageToSend.obj = test(TaskType.STOPPED);
                mMessenger.send(messageToSend);
                //handleTaskRequest(TaskType.STOPPED);
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

    private void handleTaskRequest(TaskType type) throws RemoteException {
        Message messageToSend = new Message();
        GlobalStatus status = mClient.getGlobalStatus();
        List<TaskStatusLite> list = mClient.getTaskStatusLiteList(type);
        RefreshBundle bundle = new RefreshBundle(list, status);
        messageToSend.what = UPDATE_TASK_LIST_AND_GLOBAL_STATUS;
        messageToSend.obj = bundle;
        mMessenger.send(messageToSend);
    }

    private String test(TaskType taskType) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss z");
        return taskType + formatter.format(currentTime);
    }
}

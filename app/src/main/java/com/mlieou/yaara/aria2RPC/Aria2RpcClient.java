package com.mlieou.yaara.aria2RPC;

import com.mlieou.yaara.aria2RPC.model.ActiveTask;
import com.mlieou.yaara.aria2RPC.model.StoppedTask;
import com.mlieou.yaara.aria2RPC.model.WaitingTask;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by mengdi on 12/26/17.
 */

public class Aria2RpcClient {

    private OkHttpClient mClient;

    private List<ActiveTask> mActiveTaskList;
    private List<WaitingTask> mWaitingTaskList;
    private List<StoppedTask> mStoppedTaskList;

    public Aria2RpcClient(OkHttpClient mClient) {
        this.mClient = mClient;
    }

    public List<ActiveTask> getActiveTaskList() {
        return mActiveTaskList;
    }

    public List<WaitingTask> getWaitingTaskList() {
        return mWaitingTaskList;
    }

    public List<StoppedTask> getStoppedTaskList() {
        return mStoppedTaskList;
    }
}

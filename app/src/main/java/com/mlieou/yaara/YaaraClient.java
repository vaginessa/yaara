package com.mlieou.yaara;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mlieou.yaara.model.TaskType;
import com.mlieou.yaara.rpc.aria2.Aria2RpcClient;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.ServerProfile;
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.model.TaskStatusLite;
import com.mlieou.yaara.rpc.aria2.constant.Aria2RpcKey;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mlieou on 1/7/18.
 */

public class YaaraClient {

    private Aria2RpcClient mClient;
    private ServerProfile mProfile;
    private Gson mGson;

    public YaaraClient(ServerProfile profile) {
        mProfile = profile;
        mGson = new Gson();
        mClient = new Aria2RpcClient(mProfile.getHost(), mProfile.getPort(), mProfile.getRequestPath());
    }

    public List<TaskStatusLite> getTaskStatusLiteList(TaskType state) {
        List<TaskStatusLite> list;
        try {
            String jsonStr;
            List<String> keys = Arrays.asList(TaskStatusLite.REQUEST_KEY);
            switch (state) {
                case ACTIVE:
                    jsonStr = mClient.tellActive(keys);
                    break;
                case STOPPED:
                    jsonStr = mClient.tellStopped(0, 64, keys);
                    break;
                case WAITING:
                    jsonStr = mClient.tellWaiting(0, 64, keys);
                    break;
                default:
                    jsonStr = "";
            }
            Type collectionType = new TypeToken<List<TaskStatusLite>>(){}.getType();
            list = mGson.fromJson(jsonStr, collectionType);
        } catch (Exception e) {
            list = new ArrayList<>(0);
        }
        return list;
    }

    public TaskStatus getTaskStatus(String gid) {
        return null;
    }

    public GlobalStatus getGlobalStatus() {
        String jsonStr;
        try {
            jsonStr = mClient.getGlobalStat();
        } catch (Exception e) {
            jsonStr = "";
        }
        return mGson.fromJson(jsonStr, GlobalStatus.class);
    }

    public String unpause(String gid) {
        return null;
    }

    public String pause(String gid) {
        return null;
    }

    public String remove(String gid) {
        return null;
    }
}

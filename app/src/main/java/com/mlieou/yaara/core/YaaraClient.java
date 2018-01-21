package com.mlieou.yaara.core;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.mlieou.yaara.model.TaskType;
import com.mlieou.yaara.rpc.aria2.Aria2RpcClient;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.ServerProfile;
import com.mlieou.yaara.model.TaskStatus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mlieou on 1/7/18.
 */

public class YaaraClient {

    private Aria2RpcClient mClient;
    private ServerProfile mProfile;
    private Gson mGson;
    private HashMap<String, String> mTaskNameMap;
    private HashSet<TaskType> mFullUpdatePerformed;

    public YaaraClient(ServerProfile profile) {
        mProfile = profile;
        mGson = new Gson();
        mClient = new Aria2RpcClient(mProfile.getHost(), mProfile.getPort(), mProfile.getRequestPath());
        mTaskNameMap = new HashMap<>();
        mFullUpdatePerformed = new HashSet<>();
    }

    public List<TaskStatus> getTaskStatusLiteList(TaskType state) {
        List<TaskStatus> list;
        try {
            String jsonStr;
            List<String> keys;
            if (!mFullUpdatePerformed.contains(state)) {
                mFullUpdatePerformed.add(state);
                keys = Arrays.asList(TaskStatus.REQUEST_FULL_UPDATE);
            } else {
                keys = Arrays.asList(TaskStatus.REQUEST_DELTA_UPDATE);
            }
            switch (state) {
                case ACTIVE:
                    jsonStr = mClient.tellActive(keys);
                    break;
                case STOPPED:
                    jsonStr = mClient.tellStopped(-1, 64, keys);
                    break;
                case WAITING:
                    jsonStr = mClient.tellWaiting(-1, 64, keys);
                    break;
                default:
                    jsonStr = "";
            }
            Type collectionType = new TypeToken<List<TaskStatus>>(){}.getType();
            list = mGson.fromJson(jsonStr, collectionType);
            setNameForAllTasks(list);
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<>(0);
        }
        return list;
    }

    private void setNameForAllTasks(List<TaskStatus> list) {
        for (TaskStatus status : list) {
            String gid = status.getGid();
            if (mTaskNameMap.containsKey(gid)) {
                status.setTaskName(mTaskNameMap.get(gid));
            } else {
                String taskName = getTaskName(status);
                if (taskName.equals("")) {
                    TaskStatus fullStatus = getTaskStatus(gid);
                    taskName = getTaskName(fullStatus);
                }
                if (taskName.length() == 0)
                    taskName = "UNKNOWN";

                status.setTaskName(taskName);
                mTaskNameMap.put(gid, taskName);
            }
        }
    }

    private static String getTaskName(TaskStatus status) {
        if (status == null) return "";
        String taskName;
        // check if it's a bittorrent task, then use info name as taskName
        if (status.getBittorrent() != null && status.getBittorrent().getInfo() != null) {
            taskName = status.getBittorrent().getInfo().name;
        } else if (status.getFiles() != null && status.getFiles().size() > 0) {
            // if it has a file list, use the first file name as taskName
            String dir = status.getDir();
            String path = status.getFiles().get(0).getPath();

            // if path start with [METADATA], then it is a magnet link, use path as taskName
            if (path.startsWith("[METADATA]"))
                taskName = path;
            else
                // otherwise, taskName should be the file name
                taskName = path.substring(dir.length() + 1);
        }
        else
            taskName = "";
        return taskName;
    }

    public TaskStatus getTaskStatus(String gid) {
        TaskStatus status;
        try {
             String str = mClient.tellStatus(gid, Arrays.asList(TaskStatus.REQUEST_FULL_UPDATE));
             status = mGson.fromJson(str, TaskStatus.class);
        } catch (Exception e) {
            status = null;
        }
        return status;
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

    public String addHttpTask(String url) {
        List<String> urlList = new ArrayList<>();
        urlList.add(url);
        String gid;
        try {
            gid = mClient.addUri(urlList, new HashMap<>(), 0);
        } catch (Exception e) {
            gid = "";
        }
        return gid;
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

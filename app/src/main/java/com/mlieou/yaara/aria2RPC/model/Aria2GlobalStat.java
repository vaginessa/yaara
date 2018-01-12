package com.mlieou.yaara.aria2RPC.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by mengdi on 1/4/18.
 */

public class Aria2GlobalStat {
    private long downloadSpeed;
    private long uploadSpeed;
    private int numActive;
    private int numWaiting;
    private int numStopped;
    private int numStoppedTotal;

    public long getDownloadSpeed() {
        return downloadSpeed;
    }

    public long getUploadSpeed() {
        return uploadSpeed;
    }

    public int getNumActive() {
        return numActive;
    }

    public int getNumWaiting() {
        return numWaiting;
    }

    public int getNumStopped() {
        return numStopped;
    }

    public int getNumStoppedTotal() {
        return numStoppedTotal;
    }

    public static Aria2GlobalStat deserialize(JSONObject jsonObject) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), Aria2GlobalStat.class);
    }
}

package com.mlieou.yaara.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by mlieou on 1/4/18.
 */

public class GlobalStatus implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(downloadSpeed);
        dest.writeLong(uploadSpeed);
        dest.writeInt(numActive);
        dest.writeInt(numWaiting);
        dest.writeInt(numStopped);
        dest.writeInt(numStoppedTotal);
    }

    public static final Parcelable.Creator<GlobalStatus> CREATOR = new Parcelable.Creator<GlobalStatus>() {
        @Override
        public GlobalStatus createFromParcel(Parcel source) {
            GlobalStatus globalStatus = new GlobalStatus();
            globalStatus.downloadSpeed = source.readLong();
            globalStatus.uploadSpeed = source.readLong();
            globalStatus.numActive = source.readInt();
            globalStatus.numWaiting = source.readInt();
            globalStatus.numStopped = source.readInt();
            globalStatus.numStoppedTotal = source.readInt();
            return globalStatus;
        }

        @Override
        public GlobalStatus[] newArray(int size) {
            return new GlobalStatus[size];
        }
    };
}

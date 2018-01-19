package com.mlieou.yaara.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mlieou.yaara.rpc.aria2.constant.Aria2RpcKey;

/**
 * Created by mlieou on 1/7/18.
 */

public class TaskStatusLite implements Parcelable {

    public static final String[] REQUEST_KEY = {
            Aria2RpcKey.GID,
            Aria2RpcKey.TOTAL_LENGTH,
            Aria2RpcKey.COMPLETED_LENGTH,
            Aria2RpcKey.DOWNLOAD_SPEED,
            Aria2RpcKey.UPLOAD_SPEED,
            Aria2RpcKey.STATUS
    };


    private String gid;
    private long downloadSpeed;
    private long uploadSpeed;
    private long completedLength;
    private long totalLength;
    private String status;

    protected TaskStatusLite(Parcel in) {
        gid = in.readString();
        downloadSpeed = in.readLong();
        uploadSpeed = in.readLong();
        completedLength = in.readLong();
        totalLength = in.readLong();
        status = in.readString();
    }

    public String getGid() {
        return gid;
    }

    public long getDownloadSpeed() {
        return downloadSpeed;
    }

    public long getUploadSpeed() {
        return uploadSpeed;
    }

    public long getCompletedLength() {
        return completedLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gid);
        dest.writeLong(downloadSpeed);
        dest.writeLong(uploadSpeed);
        dest.writeLong(completedLength);
        dest.writeLong(totalLength);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskStatusLite> CREATOR = new Creator<TaskStatusLite>() {
        @Override
        public TaskStatusLite createFromParcel(Parcel in) {
            return new TaskStatusLite(in);
        }

        @Override
        public TaskStatusLite[] newArray(int size) {
            return new TaskStatusLite[size];
        }
    };
}

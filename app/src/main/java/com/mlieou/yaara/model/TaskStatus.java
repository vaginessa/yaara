package com.mlieou.yaara.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mengdi on 1/7/18.
 */

public class TaskStatus extends TaskStatusLite implements Parcelable {

    protected TaskStatus(Parcel in) {
        super(in);
    }
}

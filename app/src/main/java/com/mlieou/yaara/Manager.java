package com.mlieou.yaara;

import android.os.Messenger;
import android.preference.PreferenceManager;

import com.mlieou.yaara.rpc.aria2.Aria2RpcClient;

/**
 * Created by mengdi on 1/16/18.
 */

public class Manager {
    private Aria2RpcClient mClient;
    private Messenger mMessenger;
    private PreferenceManager mPreferenceManager;

    private boolean mUpdatingStatus = false;

    public Manager(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
    }
}

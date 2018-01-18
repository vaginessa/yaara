package com.mlieou.yaara;

import android.os.Messenger;
import android.preference.PreferenceManager;

import com.mlieou.yaara.model.ServerProfile;
import com.mlieou.yaara.rpc.aria2.Aria2RpcClient;

/**
 * Created by mengdi on 1/16/18.
 */

public class Aria2Manager {
    private Aria2RpcClient mClient;
    private Messenger mMessenger;
    private ServerManager mServerManager;

    private boolean mUpdatingStatus = false;

    public Aria2Manager(ServerManager manager) {
        this.mServerManager = manager;
    }

    public void initServer(ServerProfile profile) {

    }

    public void setHandlerMessenger(Messenger messenger) {
        mMessenger = messenger;
    }
}

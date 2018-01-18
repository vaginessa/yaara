package com.mlieou.yaara;

import android.content.Context;

import com.mlieou.yaara.model.ServerProfile;

/**
 * Created by mengdi on 1/16/18.
 */

public class ServerPreferencesManager {
    Context mContext;
    public ServerPreferencesManager(Context context) {
        mContext = context;
    }

    public ServerProfile getDefaultServerProfile() {
        return new ServerProfile("Jarvis", "10.24.233.100", 6800, "jsonrpc", null, ServerProfile.Protocol.HTTP, ServerProfile.RequestMethod.POST);
    }
}

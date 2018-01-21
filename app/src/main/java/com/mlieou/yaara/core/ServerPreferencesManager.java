package com.mlieou.yaara.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mlieou.yaara.model.ServerProfile;

import java.util.List;

/**
 * Created by mlieou on 1/16/18.
 */

public class ServerPreferencesManager {

    Context mContext;
    SharedPreferences mPreference;

    public ServerPreferencesManager(Context context) {
        mContext = context;
        mPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public ServerProfile getDefaultServerProfile() {
        String name = mPreference.getString("pref_aria2_alias", "");
        String host = mPreference.getString("pref_aria2_host", "");
        int post = mPreference.getInt("pref_aria2_port", 6800);
        return new ServerProfile(name, host, post, "jsonrpc", null, ServerProfile.Protocol.HTTP, ServerProfile.RequestMethod.POST);
    }

    public List<ServerProfile> getAllServerProfile() {
        return null;
    }

    public boolean saveServerProfile(ServerProfile profile) {
        return true;
    }

    public ServerProfile getServerProfileByKey(String key) {
        return null;
    }

    public void removeServerProfile(String key) {

    }

    public boolean isServerProfileExist() {
        return !mPreference.getString("pref_aria2_host", "").equals("");
    }

    public int getUpdateInterval() {
        String interval = mPreference.getString("pref_yaara_update_interval", "1");
        return Integer.valueOf(interval);
    }
}

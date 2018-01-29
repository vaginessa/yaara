package com.mlieou.yaara.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

import com.mlieou.yaara.data.YaaraDataStore;
import com.mlieou.yaara.model.ServerProfile;

/**
 * Created by mlieou on 1/16/18.
 */

public class ServerProfileManager {

    public static final String[] PROJECTION = {
            YaaraDataStore.Servers._ID,
            YaaraDataStore.Servers.NAME,
            YaaraDataStore.Servers.HOSTNAME,
            YaaraDataStore.Servers.PORT,
            YaaraDataStore.Servers.SECRET_TOKEN
    };

    public static final int ID_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int HOSTNAME_INDEX = 2;
    public static final int PORT_INDEX = 3;
    public static final int SECRET_TOKEN_INDEX = 4;


    Context mContext;
    SharedPreferences mPreference;

    public ServerProfileManager(Context context) {
        mContext = context;
        mPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public boolean isServerProfileExist() {
        return getActiveServerProfile() != null;
    }

    public int getUpdateInterval() {
        String interval = mPreference.getString("pref_yaara_update_interval", "1");
        return Integer.valueOf(interval);
    }

    public long getActiveServerId() {
        return mPreference.getLong("pref_active_server", -1L);
    }

    public void setActiveServerId(long id) {
        mPreference.edit().putLong("pref_active_server", id).apply();
    }

    public ServerProfile getActiveServerProfile() {
        Cursor cursor = mContext.getContentResolver().query(
                YaaraDataStore.Servers.CONTENT_URI,
                PROJECTION,
                YaaraDataStore.Servers._ID + " = ?",
                new String[]{Long.toString(getActiveServerId())},
                null);
        if (cursor!= null && cursor.moveToNext()) {
            String hostname = cursor.getString(HOSTNAME_INDEX);
            int port = cursor.getInt(PORT_INDEX);
            String secretToken = cursor.getString(SECRET_TOKEN_INDEX);
            cursor.close();
            return new ServerProfile(hostname, port, secretToken);
        }
        return null;
    }
}

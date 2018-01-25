package com.mlieou.yaara.fragment;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.mlieou.yaara.R;
import com.mlieou.yaara.data.YaaraDataStore;
import com.mlieou.yaara.widget.NumberPickerPreference;

/**
 * Created by mlieou on 1/24/18.
 */

public class ServerEditor extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener{

    private static final String[] sProjection = {
            YaaraDataStore.Servers._ID,
            YaaraDataStore.Servers.NAME,
            YaaraDataStore.Servers.HOSTNAME,
            YaaraDataStore.Servers.PORT,
            YaaraDataStore.Servers.SECRET_TOKEN
    };

    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int HOSTNAME_INDEX = 2;
    private static final int PORT_INDEX = 3;
    private static final int SECRET_TOKEN_INDEX = 4;

    private static final String KEY_SERVER_NAME = "pref_aria2_alias";
    private static final String KEY_SERVER_HOSTNAME = "pref_aria2_host";
    private static final String KEY_SERVER_PORT = "pref_aria2_port";
    private static final String KEY_SERVER_REQUEST_PATH = "pref_aria2_request_path";
    private static final String KEY_SERVER_PROTOCOL = "pref_aria2_protocol";
    private static final String KEY_SERVER_REQUEST_METHOD = "pref_aria2_request_method";
    private static final String KEY_SERVER_SECRET_TOKEN = "pref_aria2_secret_token";

    private EditTextPreference mAliasName;
    private EditTextPreference mHostname;
    private NumberPickerPreference mPort;
    private EditTextPreference mRequestPath;
    private ListPreference mProtocol;
    private ListPreference mRequestMethod;
    private EditTextPreference mSecretToken;

    private static String sNotSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.server_editor);

        sNotSet = getResources().getString(R.string.preference_not_set);
        mAliasName = (EditTextPreference) findPreference(KEY_SERVER_NAME);
        mHostname = (EditTextPreference) findPreference(KEY_SERVER_HOSTNAME);
        mPort = (NumberPickerPreference) findPreference(KEY_SERVER_PORT);
        mRequestPath = (EditTextPreference) findPreference(KEY_SERVER_REQUEST_PATH);
        mProtocol = (ListPreference) findPreference(KEY_SERVER_PROTOCOL);
        mRequestMethod = (ListPreference) findPreference(KEY_SERVER_REQUEST_METHOD);
        mSecretToken = (EditTextPreference) findPreference(KEY_SERVER_SECRET_TOKEN);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}
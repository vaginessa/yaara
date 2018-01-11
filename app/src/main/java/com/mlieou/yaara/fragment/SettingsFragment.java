package com.mlieou.yaara.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mlieou.yaara.R;
import com.mlieou.yaara.widget.NumberPickerPreference;

import java.util.Map;

/**
 * Created by mengdi on 1/10/18.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        initSummary(preferenceScreen);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof EditTextPreference) {
            setSummary((EditTextPreference) preference);
        } else if (preference instanceof NumberPickerPreference) {
            String summary = String.valueOf(((NumberPickerPreference) preference).getValue());
            preference.setSummary(summary);
        }
    }

    private void setSummary(EditTextPreference preference) {
        String summary = preference.getText();
        if (summary.length() == 0)
            summary = getString(R.string.preference_not_set);
        preference.setSummary(summary);
    }

    private void initSummary(PreferenceScreen preferenceScreen) {
        Map<String, ?> preferenceEntries = preferenceScreen.getSharedPreferences().getAll();
        for (Map.Entry<String, ?> entry : preferenceEntries.entrySet()) {
            Preference preference = findPreference(entry.getKey());
            if (preference instanceof EditTextPreference) {
                setSummary((EditTextPreference) preference);
            } else if (preference instanceof NumberPickerPreference) {
                String summary = String.valueOf(((NumberPickerPreference) preference).getValue());
                preference.setSummary(summary);
            }
        }
    }
}

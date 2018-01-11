package com.mlieou.yaara.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mlieou.yaara.R;
import com.mlieou.yaara.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}

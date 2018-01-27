package com.mlieou.yaara.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mlieou.yaara.R;
import com.mlieou.yaara.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.settings);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}

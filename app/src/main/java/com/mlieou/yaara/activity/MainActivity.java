package com.mlieou.yaara.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskPagerAdapter;
import com.mlieou.yaara.fragment.SimpleNewTaskFragment;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.service.YaaraService;
import com.mlieou.yaara.util.NetworkSpeedParser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String NEW_TASK_DIALOG = "new_task_dialog";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            GlobalStatus status = intent.getParcelableExtra(YaaraService.GLOBAL_STATUS);
            getSupportActionBar().setSubtitle(buildSubtitle(status));
        }
    };

    private String buildSubtitle(GlobalStatus status) {
        return "Download: " + NetworkSpeedParser.parse(status.getDownloadSpeed()) + " Upload: " + NetworkSpeedParser.parse(status.getUploadSpeed());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tab = findViewById(R.id.tab);
        TaskPagerAdapter adapter = new TaskPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.view_pager_container);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(YaaraService.GLOBAL_STATUS));
        Intent intent = new Intent(this, YaaraService.class);
        intent.setAction(YaaraService.GLOBAL_STATUS);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void newTask(View view) {
        SimpleNewTaskFragment fragment = new SimpleNewTaskFragment();
        fragment.show(getFragmentManager(), NEW_TASK_DIALOG);
    }
}

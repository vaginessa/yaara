package com.mlieou.yaara.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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

    public void newTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }
}

package com.mlieou.yaara.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskDetailPagerAdapter;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_GID = "gid";
    public static final String INTENT_EXTRA_TASK_NAME = "task_name";

    private ViewPager mPager;
    private TaskDetailPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mPager = findViewById(R.id.view_pager_container);
        mPagerAdapter = new TaskDetailPagerAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }
}

package com.mlieou.yaara.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskDetailPagerAdapter;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_GID = "gid";
    public static final String INTENT_EXTRA_TASK_NAME = "task_name";

    private ViewPager mPager;
    private TaskDetailPagerAdapter mPagerAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mPager = findViewById(R.id.view_pager_container);
        mToolbar = findViewById(R.id.toolbar);

        mPagerAdapter = new TaskDetailPagerAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}

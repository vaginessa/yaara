package com.mlieou.yaara.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mlieou.yaara.R;
import com.mlieou.yaara.fragment.TaskDetailOverviewFragment;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_GID = "gid";
    public static final String INTENT_EXTRA_TASK_NAME = "task_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        TaskDetailOverviewFragment fragment = new TaskDetailOverviewFragment();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .commit();
    }
}

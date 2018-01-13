package com.mlieou.yaara.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskAdapter;
import com.mlieou.yaara.rpc.aria2.Aria2RpcClient;
import com.mlieou.yaara.model.Aria2TaskStatus;

import java.util.List;

/**
 * Created by mengdi on 1/6/18.
 */

public class TaskPagerFragment extends Fragment {

    private static final String TAG = "TaskPagerFragment";

    public static final String TASK_TYPE = "take_type";

    public enum TaskType {
        ACTIVE,
        WAITING,
        STOPPED
    }

    TaskAdapter mAdapter;

    public TaskPagerFragment() {}

    public static TaskPagerFragment newInstance(TaskType taskType) {

        Bundle args = new Bundle();
        args.putSerializable(TASK_TYPE, taskType);

        TaskPagerFragment fragment = new TaskPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_task_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TaskAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}

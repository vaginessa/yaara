package com.mlieou.yaara.fragment;

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
import com.mlieou.yaara.adapter.TaskDetailFileAdapter;
import com.mlieou.yaara.model.TaskStatus;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mlieou on 2/4/18.
 */

public class TaskDetailFileFragment extends Fragment implements Observer {

    TaskDetailFileAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TaskDetailFileAdapter();
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void update(Observable o, Object arg) {
        TaskStatus status = (TaskStatus) arg;
        mAdapter.swapData(status);
    }
}

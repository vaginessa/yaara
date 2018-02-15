package com.mlieou.yaara.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskDetailTrackerAdapter;
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.widget.PlaceholderRecyclerView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mlieou on 2/4/18.
 */

public class TaskDetailTrackerFragment extends Fragment implements Observer {

    TaskDetailTrackerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker_list, container, false);
        PlaceholderRecyclerView recyclerView = view.findViewById(R.id.recycler_view_tracker_list);
        LinearLayout placeHolder = view.findViewById(R.id.empty_list_placeholder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setEmptyView(placeHolder);
        mAdapter = new TaskDetailTrackerAdapter();
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void update(Observable o, Object arg) {
        TaskStatus status = (TaskStatus) arg;
        mAdapter.swapData(status);
    }
}

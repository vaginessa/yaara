package com.mlieou.yaara.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlieou.yaara.R;
import com.mlieou.yaara.activity.MainActivity;
import com.mlieou.yaara.adapter.TaskAdapter;
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.model.TaskType;
import com.mlieou.yaara.widget.PlaceholderRecyclerView;

import java.util.List;

/**
 * Created by mlieou on 1/6/18.
 */

public class TaskPagerFragment extends Fragment implements TaskFragmentCallback {

    public static final String TASK_TYPE = "take_type";
    private static final String TAG = "TaskPagerFragment";
    TaskType mTaskType;

    TaskAdapter mAdapter;

    MainActivity mActivity;

    SwipeRefreshLayout mSwipeRefreshLayout;
    PlaceholderRecyclerView mTaskList;

    public TaskPagerFragment() {
    }

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
        mTaskList = view.findViewById(R.id.recycler_view_task_list);
        View placeholder = view.findViewById(R.id.empty_list_placeholder);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TaskAdapter(getContext());
        mTaskList.setAdapter(mAdapter);
        mTaskList.setEmptyView(placeholder);
        if (getArguments() != null) {
            mTaskType = (TaskType) getArguments().getSerializable(TASK_TYPE);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof MainActivity) {
            mActivity = (MainActivity) getActivity();
        } else {
            mActivity = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        mActivity.startUpdateGlobalStatusAndTaskList(mTaskType);
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
            onResume();
    }

    @Override
    public void swapData(List<TaskStatus> list) {
        mAdapter.swapData(list);
        mSwipeRefreshLayout.setRefreshing(false);
    }

}

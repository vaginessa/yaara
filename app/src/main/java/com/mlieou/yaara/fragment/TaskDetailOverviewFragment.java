package com.mlieou.yaara.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlieou.yaara.R;
import com.mlieou.yaara.activity.TaskDetailActivity;

/**
 * Created by mlieou on 2/4/18.
 */

public class TaskDetailOverviewFragment extends Fragment {

    TextView mGID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_overview, container, false);
        bindViews(view);
        if (getActivity() != null && getActivity().getIntent() != null) {
            mGID.setText(getActivity().getIntent().getStringExtra(TaskDetailActivity.INTENT_EXTRA_GID));
        }
        return view;
    }

    private void bindViews(View view) {
        mGID = view.findViewById(R.id.tv_gid);
    }
}

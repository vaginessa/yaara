package com.mlieou.yaara.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlieou.yaara.R;
import com.mlieou.yaara.model.TaskStatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mlieou on 2/11/18.
 */

public class TaskDetailTrackerAdapter extends RecyclerView.Adapter<TaskDetailTrackerAdapter.TaskDetailTrackerViewHolder> {

    List<List<String>> mTrackerList;

    @Override
    public TaskDetailTrackerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tracker_list_item, parent, false);
        return new TaskDetailTrackerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskDetailTrackerViewHolder holder, int position) {
        List<String> list = mTrackerList.get(position);
        String tracker = list.get(0);
        holder.tracker.setText(tracker);
    }

    @Override
    public int getItemCount() {
        if (mTrackerList == null)
            return 0;
        return mTrackerList.size();
    }

    public void swapData(TaskStatus status) {
        if (status != null && status.getBittorrent() != null) {
            mTrackerList = status.getBittorrent().getAnnounceList();
            notifyDataSetChanged();
        }
    }

    public class TaskDetailTrackerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tracker)
        TextView tracker;

        public TaskDetailTrackerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

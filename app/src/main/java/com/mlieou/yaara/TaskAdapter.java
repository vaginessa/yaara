package com.mlieou.yaara;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mlieou.yaara.aria2RPC.model.Aria2TaskStatus;

import java.util.List;

/**
 * Created by mengdi on 1/7/18.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder> {

    private static final String TAG = "TaskAdapter";

    Context mContext;
    List<Aria2TaskStatus> mTaskList;

    public TaskAdapter(Context context) {
        mContext = context;
    }

    @Override
    public TaskAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_task, parent, false);
        view.setFocusable(true);
        return new TaskAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapterViewHolder holder, int position) {
        Aria2TaskStatus status = mTaskList.get(position);
        holder.taskTitle.setText(status.getGid());
        holder.taskProgress.setProgress((int)(status.getCompletedLength() * 100 / status.getTotalLength()));
        holder.taskDownloadSpeed.setText("" + status.getDownloadSpeed());
        holder.taskRemainTime.setText("N/A");
    }

    @Override
    public int getItemCount() {
        if (mTaskList == null)  return 0;
        return mTaskList.size();
    }

    public void swapData(List<Aria2TaskStatus> list) {
        mTaskList = list;
        notifyDataSetChanged();
    }

    public class TaskAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        ProgressBar taskProgress;
        TextView taskDownloadSpeed;
        TextView taskRemainTime;

        public TaskAdapterViewHolder(View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.tv_task_title);
            taskProgress = itemView.findViewById(R.id.pb_task_progress);
            taskDownloadSpeed = itemView.findViewById(R.id.tv_task_download_speed);
            taskRemainTime = itemView.findViewById(R.id.tv_task_time_remain);
        }
    }
}

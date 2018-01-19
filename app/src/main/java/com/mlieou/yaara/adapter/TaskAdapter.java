package com.mlieou.yaara.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mlieou.yaara.R;
import com.mlieou.yaara.model.TaskStatusLite;
import com.mlieou.yaara.rpc.aria2.constant.Aria2TaskStatus;
import com.mlieou.yaara.util.NetworkSpeedParser;
import com.mlieou.yaara.util.UIUtil;

import java.util.List;

/**
 * Created by mlieou on 1/7/18.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder> implements Aria2TaskStatus {

    private static final String TAG = "TaskAdapter";

    Context mContext;
    List<TaskStatusLite> mTaskList;

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
        TaskStatusLite status = mTaskList.get(position);

        holder.taskTitle.setText(status.getGid());

        if (status.getTotalLength() != 0)
            holder.taskProgress.setProgress((int)(status.getCompletedLength() * 100 / status.getTotalLength()));
        else
            holder.taskProgress.setProgress(0);

        switch (status.getStatus()) {
            case ACTIVE:
                holder.taskDownloadSpeed.setText(NetworkSpeedParser.parse(status.getDownloadSpeed()));
                // set remain time
                if (status.getDownloadSpeed() == 0)
                    holder.taskRemainTime.setText("--:--:--");
                else
                    holder.taskRemainTime.setText(UIUtil.secondsToTime(
                            (status.getTotalLength() - status.getCompletedLength())
                                    / status.getDownloadSpeed()));
                break;
            case WAITING:case PAUSED:
                holder.taskDownloadSpeed.setText(status.getStatus());
                break;
            case COMPLETE:case ERROR:case REMOVED:
                holder.taskDownloadSpeed.setText(status.getStatus());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mTaskList == null)  return 0;
        return mTaskList.size();
    }

    public void swapData(List<TaskStatusLite> list) {
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

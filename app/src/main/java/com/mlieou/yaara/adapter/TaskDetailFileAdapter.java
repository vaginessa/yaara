package com.mlieou.yaara.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mlieou.yaara.R;
import com.mlieou.yaara.model.File;
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.util.ParserUtil;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mlieou on 2/11/18.
 */

public class TaskDetailFileAdapter extends RecyclerView.Adapter<TaskDetailFileAdapter.TaskDetailFileViewHolder> {

    private List<File> mFileList;
    private int pathOffset;

    @Override
    public TaskDetailFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_file_list_item, parent, false);
        return new TaskDetailFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskDetailFileViewHolder holder, int position) {
        File file = mFileList.get(position);
        holder.filename.setText(file.getPath().substring(pathOffset));
        holder.isSelected.setChecked(file.isSelected());
        holder.progress.setText(String.format(Locale.getDefault() ,"%.2f%%", file.getCompletedLength() * 100.0 / file.getLength()));
        holder.fileSize.setText(ParserUtil.parseSize(file.getLength()));
    }

    @Override
    public int getItemCount() {
        if (mFileList == null)
            return 0;
        return mFileList.size();
    }

    public void swapData(TaskStatus status) {
        mFileList = status.getFiles();
        pathOffset = status.getDir().length() + 1;
        notifyDataSetChanged();
    }

    public class TaskDetailFileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_selected)
        CheckBox isSelected;
        @BindView(R.id.tv_filename)
        TextView filename;
        @BindView(R.id.tv_progress)
        TextView progress;
        @BindView(R.id.tv_file_size)
        TextView fileSize;

        public TaskDetailFileViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

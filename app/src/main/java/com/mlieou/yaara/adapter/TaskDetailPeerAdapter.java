package com.mlieou.yaara.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlieou.yaara.R;
import com.mlieou.yaara.model.Peer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mlieou on 2/12/18.
 */

public class TaskDetailPeerAdapter extends RecyclerView.Adapter<TaskDetailPeerAdapter.TaskDetailPeerViewHolder> {

    List<Peer> peerList;

    @Override
    public TaskDetailPeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_peer_list_item, parent, false);
        return new TaskDetailPeerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskDetailPeerViewHolder holder, int position) {
        Peer peer = peerList.get(position);
        holder.peerName.setText(peer.getIp() + ":" + peer.getPort());
        holder.download.setText("" + peer.getDownloadSpeed());
        holder.upload.setText("" + peer.getUploadSpeed());
        holder.progress.setText("TODO");
        // TODO
    }

    @Override
    public int getItemCount() {
        if (peerList == null)
            return 0;
        return peerList.size();
    }

    public void swapData(List<Peer> peerList) {
        this.peerList = peerList;
        notifyDataSetChanged();
    }

    public class TaskDetailPeerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_peer_name)
        TextView peerName;
        @BindView(R.id.tv_peer_progress)
        TextView progress;
        @BindView(R.id.tv_peer_download)
        TextView download;
        @BindView(R.id.tv_peer_upload)
        TextView upload;

        public TaskDetailPeerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

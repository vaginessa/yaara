package com.mlieou.yaara.fragment;

import android.content.Context;
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
import com.mlieou.yaara.adapter.TaskDetailPeerAdapter;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mlieou on 2/4/18.
 */

public class TaskDetailPeerFragment extends Fragment implements Observer {

    public interface PeerListUpdater {
        void startUpdate();
        void stopUpdate();
    }

    TaskDetailPeerAdapter mAdapter;
    PeerListUpdater mUpdater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peer_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_peer_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TaskDetailPeerAdapter();
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PeerListUpdater)
            mUpdater = (PeerListUpdater) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        mUpdater.startUpdate();
    }

    @Override
    public void onStop() {
        super.onStop();
        mUpdater.stopUpdate();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

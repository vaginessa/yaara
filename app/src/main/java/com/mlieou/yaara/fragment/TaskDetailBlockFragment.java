package com.mlieou.yaara.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlieou.yaara.R;
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.widget.BitFieldView;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mlieou on 2/10/18.
 */

public class TaskDetailBlockFragment extends Fragment implements Observer {

    @BindView(R.id.bfv_block_map)
    BitFieldView blockMap;
    int pieces = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocks, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void update(Observable o, Object arg) {
        TaskStatus status = (TaskStatus) arg;
        if (status.getNumPieces() != pieces) {
            pieces = status.getNumPieces();
            blockMap.setPieces(pieces);
        }
        blockMap.setBitField(status.getBitfield());
    }
}

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
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.util.ParserUtil;
import com.mlieou.yaara.util.UIUtil;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mlieou on 2/4/18.
 */

public class TaskDetailOverviewFragment extends Fragment implements Observer {

    @BindView(R.id.tv_path)
    TextView mPath;
    @BindView(R.id.tv_gid)
    TextView mGID;
    @BindView(R.id.tv_status)
    TextView mStatus;
    @BindView(R.id.tv_remaining_time)
    TextView mRemainingTime;
    @BindView(R.id.tv_total_length)
    TextView mTotalLength;
    @BindView(R.id.tv_completed_length)
    TextView mCompletedLength;
    @BindView(R.id.tv_upload_length)
    TextView mUploadLength;
    @BindView(R.id.tv_download_speed)
    TextView mDownloadSpeed;
    @BindView(R.id.tv_upload_speed)
    TextView mUploadSpeed;
    @BindView(R.id.tv_info_hash)
    TextView mInfoHash;
    @BindView(R.id.tv_connection)
    TextView mConnection;
    @BindView(R.id.tv_seeder)
    TextView mSeeder;
    @BindView(R.id.tv_error_code)
    TextView mErrorCode;
    @BindView(R.id.tv_error_message)
    TextView mErrorMessage;
    @BindView(R.id.tv_pieces)
    TextView mPieces;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_overview, container, false);
        ButterKnife.bind(this, view);
        if (getActivity() != null && getActivity().getIntent() != null) {
            mGID.setText(getActivity().getIntent().getStringExtra(TaskDetailActivity.INTENT_EXTRA_GID));
        }
        return view;
    }

    private void fillUI(TaskStatus status) {
        mPath.setText(status.getDir());
        mStatus.setText(status.getStatus());
        mRemainingTime.setText(
                UIUtil.secondsToTime(
                        (status.getTotalLength() - status.getCompletedLength()) / status.getDownloadSpeed()));
        mTotalLength.setText(ParserUtil.parseSize(status.getTotalLength()));
        mCompletedLength.setText(ParserUtil.parseSize(status.getCompletedLength()));
        mUploadLength.setText(ParserUtil.parseSize(status.getUploadLength()));
        mDownloadSpeed.setText(ParserUtil.parseSpeed(status.getDownloadSpeed()));
        mUploadSpeed.setText(ParserUtil.parseSpeed(status.getUploadSpeed()));
        mInfoHash.setText(status.getInfoHash());
        mConnection.setText("" + status.getConnections());
        mSeeder.setText("" + status.getNumSeeders());
        //TODO
    }

    @Override
    public void update(Observable o, Object arg) {
        TaskStatus status = (TaskStatus) arg;
        fillUI(status);
    }
}

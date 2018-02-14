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
import com.mlieou.yaara.rpc.aria2.constant.Aria2TaskStatus;
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

    // Common
    @BindView(R.id.tv_path)
    TextView mPath;
    @BindView(R.id.tv_gid)
    TextView mGID;
    @BindView(R.id.tv_status)
    TextView mStatus;
    @BindView(R.id.tv_total_length)
    TextView mTotalLength;
    @BindView(R.id.tv_completed_length)
    TextView mCompletedLength;
    @BindView(R.id.tv_download_speed)
    TextView mDownloadSpeed;
    @BindView(R.id.tv_connection)
    TextView mConnection;

    // active task only
    @BindView(R.id.tv_remaining_time_title)
    TextView mRemaingTimeTitle;
    @BindView(R.id.tv_remaining_time)
    TextView mRemainingTime;

    // BT Only
    @BindView(R.id.tv_upload_length_title)
    TextView mUploadLengthTitle;
    @BindView(R.id.tv_upload_length)
    TextView mUploadLength;

    // BT Only
    @BindView(R.id.tv_upload_speed_title)
    TextView mUploadSpeedTitle;
    @BindView(R.id.tv_upload_speed)
    TextView mUploadSpeed;

    // BT Only
    @BindView(R.id.tv_seeders_title)
    TextView mSeederTitle;
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
        mTotalLength.setText(ParserUtil.parseSize(status.getTotalLength()));
        mCompletedLength.setText(ParserUtil.parseSize(status.getCompletedLength()));
        mDownloadSpeed.setText(ParserUtil.parseSpeed(status.getDownloadSpeed()));
        mConnection.setText("" + status.getConnections());

        // Active task only
        if (Aria2TaskStatus.ACTIVE.equals(status.getStatus())) {
            displayActiveTaskInfo();
            if (status.getDownloadSpeed() != 0)
                mRemainingTime.setText(
                        UIUtil.secondsToTime(
                                (status.getTotalLength() - status.getCompletedLength()) / status.getDownloadSpeed()));
            else
                mRemainingTime.setText("--:--:--");
        } else {
            hideActiveTaskInfo();
        }

        // BT only
        if (status.getInfoHash() != null && status.getInfoHash().length() > 0) {
            displayBTInfo();
            mUploadLength.setText(ParserUtil.parseSize(status.getUploadLength()));
            mUploadSpeed.setText(ParserUtil.parseSpeed(status.getUploadSpeed()));
            mSeeder.setText("" + status.getNumSeeders());
        } else {
            hideBTInfo();
        }

        //TODO
    }

    private void displayActiveTaskInfo() {
        mRemaingTimeTitle.setVisibility(View.VISIBLE);
        mRemainingTime.setVisibility(View.VISIBLE);
    }

    private void hideActiveTaskInfo() {
        mRemaingTimeTitle.setVisibility(View.GONE);
        mRemainingTime.setVisibility(View.GONE);
    }

    private void displayBTInfo() {
        mUploadSpeed.setVisibility(View.VISIBLE);
        mUploadLength.setVisibility(View.VISIBLE);
        mSeeder.setVisibility(View.VISIBLE);
        mUploadSpeedTitle.setVisibility(View.VISIBLE);
        mUploadLengthTitle.setVisibility(View.VISIBLE);
        mSeederTitle.setVisibility(View.VISIBLE);
    }

    private void hideBTInfo() {
        mUploadSpeed.setVisibility(View.GONE);
        mUploadLength.setVisibility(View.GONE);
        mSeeder.setVisibility(View.GONE);
        mUploadSpeedTitle.setVisibility(View.GONE);
        mUploadLengthTitle.setVisibility(View.GONE);
        mSeederTitle.setVisibility(View.GONE);
    }

    @Override
    public void update(Observable o, Object arg) {
        TaskStatus status = (TaskStatus) arg;
        fillUI(status);
    }
}

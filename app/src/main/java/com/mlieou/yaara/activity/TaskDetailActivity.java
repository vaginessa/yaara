package com.mlieou.yaara.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskDetailPagerAdapter;
import com.mlieou.yaara.constant.MessageCode;
import com.mlieou.yaara.core.HandlerCallback;
import com.mlieou.yaara.core.WeakHandler;
import com.mlieou.yaara.fragment.TaskDetailPeerFragment;
import com.mlieou.yaara.model.TaskStatus;
import com.mlieou.yaara.service.YaaraService;

import java.util.Timer;
import java.util.TimerTask;

public class TaskDetailActivity extends AppCompatActivity implements HandlerCallback, TaskDetailPeerFragment.PeerListUpdater {

    private static final String TAG = "TaskDetailActivity";

    public static final String INTENT_EXTRA_GID = "gid";
    public static final String INTENT_EXTRA_TASK_NAME = "task_name";



    private String mGid;
    private TaskStatus mTaskStatus;

    private ViewPager mPager;
    private TaskDetailPagerAdapter mPagerAdapter;
    private Toolbar mToolbar;


    private Messenger mServiceMessenger;
    private Messenger mMessenger;
    private boolean mIsServiceBound;
    private Timer mRefreshTimer;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceMessenger = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceMessenger = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mPager = findViewById(R.id.view_pager_container);
        mToolbar = findViewById(R.id.toolbar);

        mMessenger = new Messenger(new WeakHandler(this));

        mPagerAdapter = new TaskDetailPagerAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        String taskName = "";
        if (getIntent() != null) {
            mGid = getIntent().getStringExtra(INTENT_EXTRA_GID);
            taskName = getIntent().getStringExtra(INTENT_EXTRA_TASK_NAME);
        }

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(taskName);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        doBindService();
        startUpdateTaskStatus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRefreshTimer != null) {
            mRefreshTimer.cancel();
            mRefreshTimer = null;
        }
        doUnbindService();
    }

    private void doBindService() {
        bindService(new Intent(this, YaaraService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsServiceBound = true;
    }

    private void doUnbindService() {
        if (mIsServiceBound) {
            unbindService(mConnection);
            mIsServiceBound = false;
        }
    }

    public void startUpdateTaskStatus() {
        if (mRefreshTimer != null) {
            mRefreshTimer.cancel();
            mRefreshTimer = null;
        }

        mRefreshTimer = new Timer();
        mRefreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mServiceMessenger == null) {
                    return;
                }
                Message message = new Message();
                message.what = MessageCode.GET_TASK_STATUS;
                message.obj = mGid;
                message.replyTo = mMessenger;
                try {
                    mServiceMessenger.send(message);
                } catch (RemoteException e) {
                }
            }
        }, 0, 1000);
    }

    @Override
    public void handleMessage(Message msg, Handler handler) {
        switch (msg.what) {
            case MessageCode.UPDATE_TASK_STATUS:
                mPagerAdapter.updateFragments(msg.obj);
        }
    }

    @Override
    public void startUpdate() {

    }

    @Override
    public void stopUpdate() {

    }
}

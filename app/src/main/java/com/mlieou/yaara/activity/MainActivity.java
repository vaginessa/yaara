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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.TaskPagerAdapter;
import com.mlieou.yaara.constant.MessageCode;
import com.mlieou.yaara.core.HandlerCallback;
import com.mlieou.yaara.core.ServerPreferencesManager;
import com.mlieou.yaara.core.WeakHandler;
import com.mlieou.yaara.fragment.SimpleNewTaskFragment;
import com.mlieou.yaara.fragment.TaskFragmentCallback;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.RefreshBundle;
import com.mlieou.yaara.model.TaskType;
import com.mlieou.yaara.service.YaaraService;
import com.mlieou.yaara.util.UIUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements HandlerCallback {

    public static final String NEW_TASK_DIALOG = "new_task_dialog";
    private static final String TAG = "MainActivity";
    private TaskPagerAdapter mAdapter;
    private Handler mUpdateHandler;
    private Messenger mMessenger;
    private Messenger mServiceMessenger;
    private boolean mIsServiceBound;
    private ServerPreferencesManager mServerPreferencesManager;
    private Timer mRefreshTimer;
    private int mUpdateInterval;
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
        setContentView(R.layout.activity_main);
        mServerPreferencesManager = new ServerPreferencesManager(this);

        mUpdateHandler = new WeakHandler(this);
        mMessenger = new Messenger(mUpdateHandler);

        TabLayout tab = findViewById(R.id.tab);
        mAdapter = new TaskPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.view_pager_container);
        pager.setAdapter(mAdapter);
        tab.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mServerPreferencesManager.isServerProfileExist()) {
            displayMainContent();
            doBindService();
        } else {
            hideMainContent();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopUIUpdate();
        doUnbindService();
    }

    private void hideMainContent() {
        TextView notice = findViewById(R.id.tv_no_server_notice);
        FloatingActionButton fab = findViewById(R.id.fab_new_task);
        LinearLayout mainContent = findViewById(R.id.main_content);
        notice.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
        mainContent.setVisibility(View.INVISIBLE);
    }

    private void displayMainContent() {
        TextView notice = findViewById(R.id.tv_no_server_notice);
        FloatingActionButton fab = findViewById(R.id.fab_new_task);
        LinearLayout mainContent = findViewById(R.id.main_content);
        notice.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);
        mainContent.setVisibility(View.VISIBLE);
    }

    public void showNewTaskDialog(View view) {
        SimpleNewTaskFragment fragment = new SimpleNewTaskFragment();
        fragment.show(getFragmentManager(), NEW_TASK_DIALOG);
    }

    public void sumbitTask(String url) {
        Message message = new Message();
        message.what = MessageCode.ADD_HTTP_TASK;
        message.obj = url;
        message.replyTo = mMessenger;
        try {
            mServiceMessenger.send(message);
        } catch (RemoteException e) {

        }
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

    public void startUpdateGlobalStatusAndTaskList(TaskType taskType) {
        // requesting new task type, stop old ui update
        stopUIUpdate();

        if (!mServerPreferencesManager.isServerProfileExist())
            return;

        mUpdateInterval = mServerPreferencesManager.getUpdateInterval() * 1000;

        mRefreshTimer = new Timer();
        mRefreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mServiceMessenger == null) {
                    return;
                }
                Message message = new Message();
                switch (taskType) {
                    case ACTIVE:
                        message.what = MessageCode.GET_ACTIVE_TASKS;
                        break;
                    case STOPPED:
                        message.what = MessageCode.GET_STOPPED_TASKS;
                        break;
                    case WAITING:
                        message.what = MessageCode.GET_WAITING_TASKS;
                        break;
                }
                message.replyTo = mMessenger;
                try {
                    mServiceMessenger.send(message);
                } catch (RemoteException e) {
                }
            }
        }, 0, mUpdateInterval);
    }

    public void stopUIUpdate() {
        if (mRefreshTimer != null) {
            mRefreshTimer.cancel();
            mRefreshTimer = null;
        }
    }

    @Override
    public void handleMessage(Message msg, Handler handler) {
        switch (msg.what) {
            case MessageCode.UPDATE_TASK_LIST_AND_GLOBAL_STATUS:
                // list received and global status received, update subtitle and list
                RefreshBundle bundle = (RefreshBundle) msg.obj;
                GlobalStatus globalStatus = bundle.getGlobalStatus();
                getSupportActionBar().setSubtitle(UIUtil.buildSubtitle(globalStatus));
                TaskFragmentCallback callback = (TaskFragmentCallback) mAdapter.getCurrentFragment();
                callback.swapData(bundle.getTaskList());
        }
    }
}

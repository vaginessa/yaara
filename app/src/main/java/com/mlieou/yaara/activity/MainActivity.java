package com.mlieou.yaara.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mlieou.yaara.HandlerInterface;
import com.mlieou.yaara.constant.MessageCode;
import com.mlieou.yaara.R;
import com.mlieou.yaara.WeakHandler;
import com.mlieou.yaara.adapter.TaskPagerAdapter;
import com.mlieou.yaara.fragment.SimpleNewTaskFragment;
import com.mlieou.yaara.model.TaskType;
import com.mlieou.yaara.service.YaaraService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity implements HandlerInterface {

    private TaskPagerAdapter mAdapter;

    private Handler mUpdateHandler;
    private Messenger mMessenger;
    private Messenger mServiceMessenger;
    private boolean mIsServiceBound;

    private Timer mRefreshTimer;
    private CountDownLatch mCountdownLatch;

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

    private static final String TAG = "MainActivity";

    public static final String NEW_TASK_DIALOG = "new_task_dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .build();

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
        doBindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopUIUpdate();
        doUnbindService();
    }

    public void newTask(View view) {
        SimpleNewTaskFragment fragment = new SimpleNewTaskFragment();
        fragment.show(getFragmentManager(), NEW_TASK_DIALOG);
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

    public void startUpdateGlobalStatusWithTaskType(TaskType taskType) {
        // requesting new task type, stop old ui update
        stopUIUpdate();

        int updateInterval = 1000;
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
                    // TODO
                }
            }
        }, 0, updateInterval);
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
                getSupportActionBar().setSubtitle((String) msg.obj);
        }
    }
}

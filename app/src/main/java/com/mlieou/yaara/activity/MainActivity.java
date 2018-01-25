package com.mlieou.yaara.activity;

import android.app.LoaderManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.database.Cursor;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mlieou.yaara.R;
import com.mlieou.yaara.adapter.ServerAdapter;
import com.mlieou.yaara.adapter.TaskPagerAdapter;
import com.mlieou.yaara.constant.MessageCode;
import com.mlieou.yaara.core.HandlerCallback;
import com.mlieou.yaara.core.ServerPreferencesManager;
import com.mlieou.yaara.core.WeakHandler;
import com.mlieou.yaara.data.YaaraDataStore;
import com.mlieou.yaara.fragment.AboutDialogFragment;
import com.mlieou.yaara.fragment.SimpleNewTaskFragment;
import com.mlieou.yaara.fragment.TaskFragmentCallback;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.RefreshBundle;
import com.mlieou.yaara.model.TaskType;
import com.mlieou.yaara.service.YaaraService;
import com.mlieou.yaara.util.UIUtil;
import com.mlieou.yaara.widget.ServerDrawerItem;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements HandlerCallback, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String NEW_TASK_DIALOG = "new_task_dialog";
    private static final int ID_SERVER_LOADER = 1000;
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private AccountHeader mHeader;
    private Drawer mDrawer;

    private TaskPagerAdapter mTaskPagerAdapter;
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

        mToolbar = findViewById(R.id.toolbar);
        mHeader = buildHeader();
        mDrawer = buildDrawer();

        TabLayout tab = findViewById(R.id.tab);
        mTaskPagerAdapter = new TaskPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.view_pager_container);
        pager.setAdapter(mTaskPagerAdapter);
        tab.setupWithViewPager(pager);

        getLoaderManager().restartLoader(ID_SERVER_LOADER, null, this);
    }

    private AccountHeader buildHeader() {
        AccountHeaderBuilder builder = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_header)
                .withSelectionFirstLine("YAARA")
                .withSelectionSecondLine("Aria2 Remote for Android");
        return builder.build();
    }

    private Drawer buildDrawer() {

        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mHeader)

                // settings item
                .addStickyDrawerItems(new PrimaryDrawerItem()
                        .withName(R.string.settings)
                        .withIcon(R.drawable.ic_settings)
                        .withOnDrawerItemClickListener((view, position, drawerItem) ->
                                startSettings())
                        .withSelectable(false))

                // about item
                .addStickyDrawerItems(new PrimaryDrawerItem()
                        .withName(R.string.about)
                        .withIcon(R.drawable.ic_info_outline)
                        .withOnDrawerItemClickListener(((view, position, drawerItem) ->
                                displayAboutDialog()))
                        .withSelectable(false));

        return builder.build();
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

    private boolean startSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
        return true;
    }

    private boolean displayAboutDialog() {
        AboutDialogFragment fragment = new AboutDialogFragment();
        fragment.show(getFragmentManager(), "ABOUT");
        return true;
    }

    private void hideMainContent() {
        TextView notice = findViewById(R.id.tv_no_server_notice);
        FloatingActionButton fab = findViewById(R.id.fab_new_task);
        TabLayout tab = findViewById(R.id.tab);
        ViewPager pager = findViewById(R.id.view_pager_container);
        notice.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
        tab.setVisibility(View.INVISIBLE);
        pager.setVisibility(View.INVISIBLE);
    }

    private void displayMainContent() {
        TextView notice = findViewById(R.id.tv_no_server_notice);
        FloatingActionButton fab = findViewById(R.id.fab_new_task);
        TabLayout tab = findViewById(R.id.tab);
        ViewPager pager = findViewById(R.id.view_pager_container);
        notice.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);
        tab.setVisibility(View.VISIBLE);
        pager.setVisibility(View.VISIBLE);
    }

    public void showNewTaskDialog(View view) {
        SimpleNewTaskFragment fragment = new SimpleNewTaskFragment();
        fragment.show(getFragmentManager(), NEW_TASK_DIALOG);
    }

    public void submitTask(String url) {
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
                mToolbar.setSubtitle(UIUtil.buildSubtitle(globalStatus));
                TaskFragmentCallback callback = (TaskFragmentCallback) mTaskPagerAdapter.getCurrentFragment();
                callback.swapData(bundle.getTaskList());
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case ID_SERVER_LOADER:
                return new CursorLoader(this,
                        YaaraDataStore.Servers.CONTENT_URI,
                        ServerAdapter.PROJECTION,
                        "1",
                        null,
                        null);
            default:
                throw new UnsupportedOperationException("Not implemented!");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // add header
        mDrawer.addItem((new SectionDrawerItem()
                .withName(R.string.drawer_section_server).withDivider(false)));

        while (cursor.moveToNext()) {
            String name = cursor.getString(ServerAdapter.NAME_INDEX);
            mDrawer.addItem(new ServerDrawerItem(name));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDrawer.removeAllItems();
    }
}

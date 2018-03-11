package com.mlieou.yaara.activity;

import android.app.LoaderManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.HapticFeedbackConstants;
import android.view.View;

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
import com.mlieou.yaara.core.ServerProfileManager;
import com.mlieou.yaara.core.WeakHandler;
import com.mlieou.yaara.data.YaaraDataStore;
import com.mlieou.yaara.fragment.AboutDialogFragment;
import com.mlieou.yaara.fragment.SimpleNewTaskFragment;
import com.mlieou.yaara.fragment.TaskFragmentCallback;
import com.mlieou.yaara.model.GlobalStatus;
import com.mlieou.yaara.model.RefreshBundle;
import com.mlieou.yaara.model.TaskType;
import com.mlieou.yaara.service.YaaraService;
import com.mlieou.yaara.util.ClipboardUtil;
import com.mlieou.yaara.util.UIUtil;
import com.mlieou.yaara.widget.ServerDrawerItem;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements HandlerCallback, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String NEW_TASK_DIALOG = "new_task_dialog";
    private static final int ID_SERVER_LOADER = 1000;
    private static final String TAG = "MainActivity";

    private static final String SAVE_CURRENT_FRAGMENT = "current_fragment";
    private static final String SAVE_SCROLL_POSITION = "scroll_position";

    private Toolbar mToolbar;
    private AccountHeader mHeader;
    private Drawer mDrawer;
    private FloatingActionButton mFab;
    private TabLayout mTab;
    private ViewPager mPager;

    private Context mContext;
    private TaskPagerAdapter mTaskPagerAdapter;
    private Messenger mMessenger;
    private Messenger mServiceMessenger;
    private boolean mIsServiceBound;
    private ServerProfileManager mServerProfileManager;
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
        setContentView(R.layout.activity_main);

        mContext = this;

        mFab = findViewById(R.id.fab_new_task);
        mTab = findViewById(R.id.tab);
        mPager = findViewById(R.id.view_pager_container);
        mToolbar = findViewById(R.id.toolbar);

        mServerProfileManager = new ServerProfileManager(this);

        Handler updateHandler = new WeakHandler(this);
        mMessenger = new Messenger(updateHandler);

        mHeader = buildHeader();
        mDrawer = buildDrawer();

        mTaskPagerAdapter = new TaskPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mTaskPagerAdapter);
        mPager.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(mPager);

        getLoaderManager().restartLoader(ID_SERVER_LOADER, null, this);

        if (savedInstanceState != null) {
            mPager.setCurrentItem(savedInstanceState.getInt(SAVE_CURRENT_FRAGMENT, 0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_CURRENT_FRAGMENT, mPager.getCurrentItem());
    }

    private AccountHeader buildHeader() {
        AccountHeaderBuilder builder = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_header)
                .withSelectionFirstLine(getString(R.string.app_name))
                .withSelectionSecondLine(getString(R.string.app_subtitle));
        return builder.build();
    }

    private Drawer buildDrawer() {

        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mHeader)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    long id = drawerItem.getIdentifier();
                    if (id > 0) {
                        mServerProfileManager.setActiveServerId(id);
                        displayMainContent();
                        reloadServerProfile();
                    }
                    return false;
                })
                .withOnDrawerItemLongClickListener((view, position, drawerItem) -> {
                    long id = drawerItem.getIdentifier();
                    if (id > 0) {
                        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        Uri serverUri = Uri.withAppendedPath(YaaraDataStore.Servers.CONTENT_URI, Long.toString(id));
                        PopupMenu popupMenu = new PopupMenu(mContext, view);
                        popupMenu.inflate(R.menu.menu_server_popup);
                        popupMenu.setOnMenuItemClickListener(item -> {
                            switch (item.getItemId()) {
                                case R.id.action_edit:
                                    Intent intent = new Intent(mContext, ServerEditorActivity.class);
                                    intent.setData(serverUri);
                                    startActivity(intent);
                                    return true;
                                case R.id.action_delete:
                                    if (mServerProfileManager.getActiveServerId() == id) {
                                        mServerProfileManager.setActiveServerId(-1L);
                                        reloadServerProfile();
                                    }
                                    getContentResolver().delete(
                                            serverUri,
                                            null,
                                            null);
                                    return true;
                            }
                            return false;
                        });
                        popupMenu.show();
                    }
                    return false;
                })

                // settings item
                .addStickyDrawerItems(new PrimaryDrawerItem()
                        .withName(R.string.settings)
                        .withIcon(R.drawable.ic_settings)
                        .withOnDrawerItemClickListener((view, position, drawerItem) ->
                                startSettings())
                        .withSelectable(false))

                // fragment_about item
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
        doBindService();
        if (mServerProfileManager.isServerProfileExist()) {
            displayMainContent();
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
        fragment.show(getSupportFragmentManager(), "ABOUT");
        return true;
    }

    private void reloadServerProfile() {
        if (mServiceMessenger == null)  return;
        Message message = new Message();
        message.what = MessageCode.RELOAD_SERVER_PROFILE;
        try {
            mServiceMessenger.send(message);
        } catch (RemoteException e) {

        }
    }

    private void hideMainContent() {
        mFab.setVisibility(View.INVISIBLE);
        mTab.setVisibility(View.GONE);
        mPager.setVisibility(View.INVISIBLE);
        mDrawer.openDrawer();
    }

    private void displayMainContent() {
        mFab.setVisibility(View.VISIBLE);
        mTab.setVisibility(View.VISIBLE);
        mPager.setVisibility(View.VISIBLE);
    }

    public void showNewTaskDialog(View view) {
        SimpleNewTaskFragment fragment = new SimpleNewTaskFragment();

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager != null && clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);
            CharSequence charSequence = item.getText();
            if (ClipboardUtil.isValidDownloadLink(charSequence)) {
                Bundle bundle = new Bundle();
                bundle.putString(SimpleNewTaskFragment.BUNDLE_DOWNLOAD_LINK, charSequence.toString());
                fragment.setArguments(bundle);
            }
        }
        fragment.show(getSupportFragmentManager(), NEW_TASK_DIALOG);
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

        int updateInterval = mServerProfileManager.getUpdateInterval() * 1000;

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

        mDrawer.removeAllItems();

        // add header
        mDrawer.addItem((new SectionDrawerItem()
                .withName(R.string.drawer_section_server).withDivider(false)));

        while (cursor.moveToNext()) {
            String name = cursor.getString(ServerAdapter.NAME_INDEX);
            String hostname = cursor.getString(ServerAdapter.HOSTNAME_INDEX);
            int port = cursor.getInt(ServerAdapter.PORT_INDEX);
            long id = cursor.getLong(ServerAdapter.ID_INDEX);
            mDrawer.addItem(new ServerDrawerItem(name, hostname, port).withSelectable(true).withIdentifier(id));
        }

        // add new server action
        Drawable icon = getDrawable(R.drawable.ic_add);
        if (icon != null) {
            icon = icon.mutate();
            icon.setColorFilter(getResources().getColor(R.color.color_drawer_icon), PorterDuff.Mode.SRC_ATOP);
        }
        mDrawer.addItem(new PrimaryDrawerItem()
                .withName(R.string.drawer_item_title_new_server)
                .withIcon(icon)
                .withSelectable(false)
        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
            startActivity(new Intent(getBaseContext(), ServerEditorActivity.class));
            return true;
        }));

        // select active server
        mDrawer.setSelection(mServerProfileManager.getActiveServerId());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDrawer.removeAllItems();
    }
}

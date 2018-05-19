package com.mlieou.yaara.activity

/**
 * Created by mlieou on 5/19/18.
 */


import android.app.LoaderManager
import android.content.*
import android.database.Cursor
import android.graphics.PorterDuff
import android.net.Uri
import android.os.*
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.util.Pair
import android.view.HapticFeedbackConstants
import android.view.View
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mlieou.yaara.R
import com.mlieou.yaara.adapter.ServerAdapter
import com.mlieou.yaara.adapter.TaskPagerAdapter
import com.mlieou.yaara.constant.MessageCode
import com.mlieou.yaara.core.HandlerCallback
import com.mlieou.yaara.core.ServerProfileManager
import com.mlieou.yaara.core.WeakHandler
import com.mlieou.yaara.data.YaaraDataStore
import com.mlieou.yaara.fragment.AboutDialogFragment
import com.mlieou.yaara.fragment.SimpleNewTaskFragment
import com.mlieou.yaara.fragment.TaskFragmentCallback
import com.mlieou.yaara.model.GlobalStatus
import com.mlieou.yaara.model.TaskStatus
import com.mlieou.yaara.model.TaskType
import com.mlieou.yaara.service.YaaraService
import com.mlieou.yaara.util.ClipboardUtil
import com.mlieou.yaara.util.UIUtil
import com.mlieou.yaara.widget.ServerDrawerItem
import java.util.*

class MainActivity : AppCompatActivity(), HandlerCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private var mToolbar: Toolbar? = null
    private var mHeader: AccountHeader? = null
    private var mDrawer: Drawer? = null
    private var mFab: FloatingActionButton? = null
    private var mTab: TabLayout? = null
    private var mPager: ViewPager? = null

    private var mContext: Context? = null
    private var mTaskPagerAdapter: TaskPagerAdapter? = null
    private var mMessenger: Messenger? = null
    private var mServiceMessenger: Messenger? = null
    private var mIsServiceBound: Boolean = false
    private var mServerProfileManager: ServerProfileManager? = null
    private var mRefreshTimer: Timer? = null
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            mServiceMessenger = Messenger(iBinder)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mServiceMessenger = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = this

        mFab = findViewById(R.id.fab_new_task)
        mTab = findViewById(R.id.tab)
        mPager = findViewById(R.id.view_pager_container)
        mToolbar = findViewById(R.id.toolbar)

        mServerProfileManager = ServerProfileManager(this)

        val updateHandler = WeakHandler(this)
        mMessenger = Messenger(updateHandler)

        mHeader = buildHeader()
        mDrawer = buildDrawer()

        mTaskPagerAdapter = TaskPagerAdapter(supportFragmentManager)
        mPager!!.adapter = mTaskPagerAdapter
        mPager!!.offscreenPageLimit = 3
        mTab!!.setupWithViewPager(mPager)

        loaderManager.restartLoader(ID_SERVER_LOADER, null, this)

        if (savedInstanceState != null) {
            mPager!!.currentItem = savedInstanceState.getInt(SAVE_CURRENT_FRAGMENT, 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVE_CURRENT_FRAGMENT, mPager!!.currentItem)
    }

    private fun buildHeader(): AccountHeader {
        val builder = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.drawer_header)
                .withSelectionFirstLine(getString(R.string.app_name))
                .withSelectionSecondLine(getString(R.string.app_subtitle))
        return builder.build()
    }

    private fun buildDrawer(): Drawer {

        val builder = DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar!!)
                .withAccountHeader(mHeader!!)
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    val id = drawerItem.identifier
                    if (id > 0) {
                        mServerProfileManager!!.activeServerId = id
                        displayMainContent()
                        reloadServerProfile()
                    }
                    false
                }
                .withOnDrawerItemLongClickListener { view, position, drawerItem ->
                    val id = drawerItem.identifier
                    if (id > 0) {
                        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                        val serverUri = Uri.withAppendedPath(YaaraDataStore.Servers.CONTENT_URI, java.lang.Long.toString(id))
                        val popupMenu = PopupMenu(mContext!!, view)
                        popupMenu.inflate(R.menu.menu_server_popup)
                        popupMenu.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.action_edit -> {
                                    val intent = Intent(mContext, ServerEditorActivity::class.java)
                                    intent.data = serverUri
                                    startActivity(intent)
                                    true
                                }
                                R.id.action_delete -> {
                                    if (mServerProfileManager!!.activeServerId == id) {
                                        mServerProfileManager!!.activeServerId = -1L
                                        reloadServerProfile()
                                    }
                                    contentResolver.delete(
                                            serverUri, null, null)
                                    true
                                }
                            }
                            false
                        }
                        popupMenu.show()
                    }
                    false
                }

                // settings item
                .addStickyDrawerItems(PrimaryDrawerItem()
                        .withName(R.string.settings)
                        .withIcon(R.drawable.ic_settings)
                        .withOnDrawerItemClickListener { view, position, drawerItem -> startSettings() }
                        .withSelectable(false))

                // fragment_about item
                .addStickyDrawerItems(PrimaryDrawerItem()
                        .withName(R.string.about)
                        .withIcon(R.drawable.ic_info_outline)
                        .withOnDrawerItemClickListener { view, position, drawerItem -> displayAboutDialog() }
                        .withSelectable(false))

        return builder.build()
    }

    override fun onStart() {
        super.onStart()
        doBindService()
        if (mServerProfileManager!!.isServerProfileExist) {
            displayMainContent()
        } else {
            hideMainContent()
        }
    }

    override fun onStop() {
        super.onStop()
        stopUIUpdate()
        doUnbindService()
    }

    private fun startSettings(): Boolean {
        startActivity(Intent(this, SettingsActivity::class.java))
        return true
    }

    private fun displayAboutDialog(): Boolean {
        val fragment = AboutDialogFragment()
        fragment.show(supportFragmentManager, "ABOUT")
        return true
    }

    private fun reloadServerProfile() {
        if (mServiceMessenger == null) return
        val message = Message()
        message.what = MessageCode.RELOAD_SERVER_PROFILE
        try {
            mServiceMessenger!!.send(message)
        } catch (e: RemoteException) {

        }

    }

    private fun hideMainContent() {
        mFab!!.visibility = View.INVISIBLE
        mTab!!.visibility = View.GONE
        mPager!!.visibility = View.INVISIBLE
        mDrawer!!.openDrawer()
    }

    private fun displayMainContent() {
        mFab!!.visibility = View.VISIBLE
        mTab!!.visibility = View.VISIBLE
        mPager!!.visibility = View.VISIBLE
    }

    fun showNewTaskDialog(view: View) {
        val fragment = SimpleNewTaskFragment()

        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboardManager != null && clipboardManager.hasPrimaryClip()) {
            val clipData = clipboardManager.primaryClip
            val item = clipData.getItemAt(0)
            val charSequence = item.text
            if (ClipboardUtil.isValidDownloadLink(charSequence)) {
                val bundle = Bundle()
                bundle.putString(SimpleNewTaskFragment.BUNDLE_DOWNLOAD_LINK, charSequence.toString())
                fragment.arguments = bundle
            }
        }
        fragment.show(supportFragmentManager, NEW_TASK_DIALOG)
    }

    fun submitTask(url: String) {
        val message = Message()
        message.what = MessageCode.ADD_HTTP_TASK
        message.obj = url
        message.replyTo = mMessenger
        try {
            mServiceMessenger!!.send(message)
        } catch (e: RemoteException) {

        }

    }

    private fun doBindService() {
        bindService(Intent(this, YaaraService::class.java), mConnection, Context.BIND_AUTO_CREATE)
        mIsServiceBound = true
    }

    private fun doUnbindService() {
        if (mIsServiceBound) {
            unbindService(mConnection)
            mIsServiceBound = false
        }
    }

    fun startUpdateGlobalStatusAndTaskList(taskType: TaskType) {
        // requesting new task type, stop old ui update
        stopUIUpdate()

        val updateInterval = mServerProfileManager!!.updateInterval * 1000

        mRefreshTimer = Timer()
        mRefreshTimer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (mServiceMessenger == null) {
                    return
                }
                val message = Message()
                when (taskType) {
                    TaskType.ACTIVE -> message.what = MessageCode.GET_ACTIVE_TASKS
                    TaskType.STOPPED -> message.what = MessageCode.GET_STOPPED_TASKS
                    TaskType.WAITING -> message.what = MessageCode.GET_WAITING_TASKS
                }
                message.replyTo = mMessenger
                try {
                    mServiceMessenger!!.send(message)
                } catch (e: RemoteException) {
                }

            }
        }, 0, updateInterval.toLong())
    }

    fun stopUIUpdate() {
        if (mRefreshTimer != null) {
            mRefreshTimer!!.cancel()
            mRefreshTimer = null
        }
    }

    override fun handleMessage(msg: Message, handler: Handler) {
        when (msg.what) {
            MessageCode.UPDATE_TASK_LIST_AND_GLOBAL_STATUS -> {
                // list received and global status received, update subtitle and list
                val pair = msg.obj as Pair<List<TaskStatus>, GlobalStatus>
                val globalStatus = pair.second
                mToolbar!!.subtitle = UIUtil.buildSubtitle(globalStatus)
                val callback = mTaskPagerAdapter!!.currentFragment as TaskFragmentCallback
                callback.swapData(pair.first)
            }
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle): Loader<Cursor> {
        when (i) {
            ID_SERVER_LOADER -> return CursorLoader(this,
                    YaaraDataStore.Servers.CONTENT_URI,
                    ServerAdapter.PROJECTION,
                    "1", null, null)
            else -> throw UnsupportedOperationException("Not implemented!")
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {

        mDrawer!!.removeAllItems()

        // add header
        mDrawer!!.addItem(SectionDrawerItem()
                .withName(R.string.drawer_section_server).withDivider(false))

        while (cursor.moveToNext()) {
            val name = cursor.getString(ServerAdapter.NAME_INDEX)
            val hostname = cursor.getString(ServerAdapter.HOSTNAME_INDEX)
            val port = cursor.getInt(ServerAdapter.PORT_INDEX)
            val id = cursor.getLong(ServerAdapter.ID_INDEX)
            mDrawer!!.addItem(ServerDrawerItem(name, hostname, port).withSelectable(true).withIdentifier(id))
        }

        // add new server action
        var icon = getDrawable(R.drawable.ic_add)
        if (icon != null) {
            icon = icon.mutate()
            icon!!.setColorFilter(resources.getColor(R.color.color_drawer_icon), PorterDuff.Mode.SRC_ATOP)
        }
        mDrawer!!.addItem(PrimaryDrawerItem()
                .withName(R.string.drawer_item_title_new_server)
                .withIcon(icon)
                .withSelectable(false)
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    startActivity(Intent(baseContext, ServerEditorActivity::class.java))
                    true
                })

        // select active server
        mDrawer!!.setSelection(mServerProfileManager!!.activeServerId)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mDrawer!!.removeAllItems()
    }

    companion object {

        val NEW_TASK_DIALOG = "new_task_dialog"
        private val ID_SERVER_LOADER = 1000
        private val TAG = "MainActivity"

        private val SAVE_CURRENT_FRAGMENT = "current_fragment"
        private val SAVE_SCROLL_POSITION = "scroll_position"
    }
}

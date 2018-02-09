package com.mlieou.yaara.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mlieou.yaara.fragment.TaskDetailFileFragment;
import com.mlieou.yaara.fragment.TaskDetailOverviewFragment;
import com.mlieou.yaara.fragment.TaskDetailPeerFragment;
import com.mlieou.yaara.fragment.TaskDetailTrackerFragment;

/**
 * Created by mlieou on 2/9/18.
 */

public class TaskDetailPagerAdapter extends FragmentPagerAdapter {

    private Class<?>[] mFragmentClasses = {
            TaskDetailOverviewFragment.class,
            TaskDetailFileFragment.class,
            TaskDetailTrackerFragment.class,
            TaskDetailPeerFragment.class
    };

    private static final String[] mTitle = {
            "Overview",
            "Files",
            "Trackers",
            "Peers"
    };

    private Context mContext;

    public TaskDetailPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(mContext, mFragmentClasses[position].getName());
    }

    @Override
    public int getCount() {
        return mFragmentClasses.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}

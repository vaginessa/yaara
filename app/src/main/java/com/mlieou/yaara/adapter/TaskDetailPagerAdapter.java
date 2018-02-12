package com.mlieou.yaara.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mlieou.yaara.fragment.TaskDetailBlockFragment;
import com.mlieou.yaara.fragment.TaskDetailFileFragment;
import com.mlieou.yaara.fragment.TaskDetailOverviewFragment;
import com.mlieou.yaara.fragment.TaskDetailPeerFragment;
import com.mlieou.yaara.fragment.TaskDetailTrackerFragment;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mlieou on 2/9/18.
 */

public class TaskDetailPagerAdapter extends FragmentPagerAdapter {

    private Class<?>[] mFragmentClasses = {
            TaskDetailOverviewFragment.class,
            TaskDetailFileFragment.class,
            TaskDetailBlockFragment.class,
            TaskDetailTrackerFragment.class,
            TaskDetailPeerFragment.class
    };

    private static final String[] mTitle = {
            "Overview",
            "Files",
            "Blocks",
            "Trackers",
            "Peers"
    };

    private Context mContext;

    private Observable mObservers = new FragmentObserver();

    public TaskDetailPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = Fragment.instantiate(mContext, mFragmentClasses[position].getName());
        if (fragment instanceof Observer)
            mObservers.addObserver((Observer) fragment);
        return fragment;
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

    public void updateFragments(Object status) {
        mObservers.notifyObservers(status);
    }

    private class FragmentObserver extends Observable {
        @Override
        public void notifyObservers(Object object) {
            setChanged();
            super.notifyObservers(object);
        }
    }
}

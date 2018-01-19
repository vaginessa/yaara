package com.mlieou.yaara.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mlieou.yaara.fragment.TaskPagerFragment;
import com.mlieou.yaara.model.TaskType;

/**
 * Created by mlieou on 1/6/18.
 */

public class TaskPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = new String[]{"Active", "Waiting", "Stopped"};

    private Fragment mCurrentFragment;

    public TaskPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TaskPagerFragment.newInstance(TaskType.ACTIVE);
            case 1:
                return TaskPagerFragment.newInstance(TaskType.WAITING);
            case 2:
                return TaskPagerFragment.newInstance(TaskType.STOPPED);
            default:
                // fallback
                return new Fragment();
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}

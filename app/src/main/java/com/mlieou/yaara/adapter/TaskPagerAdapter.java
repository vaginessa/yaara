package com.mlieou.yaara.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mlieou.yaara.fragment.TaskPagerFragment;

/**
 * Created by mengdi on 1/6/18.
 */

public class TaskPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = new String[]{"Active", "Waiting", "Stopped"};

    public TaskPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TaskPagerFragment.newInstance(TaskPagerFragment.TaskType.ACTIVE);
            case 1:
                return TaskPagerFragment.newInstance(TaskPagerFragment.TaskType.WAITING);
            case 2:
                return TaskPagerFragment.newInstance(TaskPagerFragment.TaskType.STOPPED);
            default:
                // fallback
                return new Fragment();
        }
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

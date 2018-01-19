package com.mlieou.yaara.fragment;

import com.mlieou.yaara.model.TaskStatusLite;

import java.util.List;

/**
 * Created by mlieou on 1/19/18.
 */

public interface TaskFragmentCallback {
    void swapData(List<TaskStatusLite> list);
}

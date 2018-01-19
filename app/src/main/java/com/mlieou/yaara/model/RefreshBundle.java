/*
 *         YAARA - Aria2 Remote for Android
 *
 * Copyright 2017-2018 Mlieou <ifddd@outlook.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mlieou.yaara.model;

import java.util.List;

public class RefreshBundle {
    private List<TaskStatusLite> taskList;
    private GlobalStatus globalStatus;

    public RefreshBundle(List<TaskStatusLite> taskList, GlobalStatus globalStatus) {
        this.taskList = taskList;
        this.globalStatus = globalStatus;
    }

    public List<TaskStatusLite> getTaskList() {
        return taskList;
    }

    public GlobalStatus getGlobalStatus() {
        return globalStatus;
    }
}
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

package com.mlieou.yaara.constant;

public interface MessageCode {

    int GET_ACTIVE_TASKS = 0;
    int GET_WAITING_TASKS = 1;
    int GET_STOPPED_TASKS = 2;
    int GET_GLOBAL_STATUS = 3;
    int GET_TASK_STATUS = 4;

    int ADD_HTTP_TASK = 16;

    int START_TASK = 32;
    int PAUSE_TASK = 33;
    int REMOVE_TASK = 34;

    int UPDATE_TASK_LIST_AND_GLOBAL_STATUS = 128;
    int UPDATE_GLOBAL_STATUS = 129;
    int HTTP_TASK_ADDED = 130;
    int UPDATE_TASK_STATUS = 131;

    int RELOAD_SERVER_PROFILE = 192;
}

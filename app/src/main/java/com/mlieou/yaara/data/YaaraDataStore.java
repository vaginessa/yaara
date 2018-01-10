/**
 * YAARA - Aria2 Remote for Android
 * <p>
 * Copyright 2017-2018 Mlieou <ifddd@outlook.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mlieou.yaara.data;

import android.content.ContentResolver;
import android.net.Uri;

public interface YaaraDataStore {
    String AUTHORITY = "yaara";

    String TYPE_PRIMARY_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";
    String TYPE_INT = "INTEGER";
    String TYPE_INT_UNIQUE = "INTEGER UNIQUE";
    String TYPE_BOOLEAN = "INTEGER(1)";
    String TYPE_BOOLEAN_DEFAULT_TRUE = "INTEGER(1) DEFAULT 1";
    String TYPE_BOOLEAN_DEFAULT_FALSE = "INTEGER(1) DEFAULT 0";
    String TYPE_TEXT = "TEXT";
    String TYPE_DOUBLE_NOT_NULL = "DOUBLE NOT NULL";
    String TYPE_TEXT_NOT_NULL = "TEXT NOT NULL";
    String TYPE_TEXT_NOT_NULL_UNIQUE = "TEXT NOT NULL UNIQUE";

    String CONTENT_PATH_NULL = "null_content";
    String CONTENT_PATH_EMPTY = "empty_content";
    String CONTENT_PATH_RAW_QUERY = "raw_query";
    String CONTENT_PATH_DATABASE_PREPARE = "database_prepare";

    Uri BASE_CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);

    Uri CONTENT_URI_NULL = Uri.withAppendedPath(BASE_CONTENT_URI, CONTENT_PATH_NULL);
    Uri CONTENT_URI_EMPTY = Uri.withAppendedPath(BASE_CONTENT_URI, CONTENT_PATH_EMPTY);
    Uri CONTENT_URI_RAW_QUERY = Uri.withAppendedPath(BASE_CONTENT_URI, CONTENT_PATH_RAW_QUERY);
    Uri CONTENT_URI_DATABASE_PREPARE = Uri.withAppendedPath(BASE_CONTENT_URI, CONTENT_PATH_DATABASE_PREPARE);

}

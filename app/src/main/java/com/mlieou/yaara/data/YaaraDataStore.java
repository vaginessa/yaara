package com.mlieou.yaara.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by mlieou on 1/21/18.
 */

@SuppressWarnings("unused")
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

    interface Servers extends BaseColumns {

        String TABLE_NAME = "servers";

        String NAME = "name";
        String HOSTNAME = "hostname";
        String PORT = "port";
        String SECRET_TOKEN = "secret_token";

        String[] COLUMNS = {_ID, NAME, HOSTNAME, PORT, SECRET_TOKEN};
        String[] TYPES = {TYPE_PRIMARY_KEY, TYPE_TEXT, TYPE_TEXT_NOT_NULL, TYPE_INT, TYPE_TEXT};
    }

}
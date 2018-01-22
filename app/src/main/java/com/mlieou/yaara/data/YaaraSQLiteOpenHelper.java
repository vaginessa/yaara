package com.mlieou.yaara.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mlieou.yaara.constant.YaaraConstants;

/**
 * Created by mlieou on 1/22/18.
 */

public class YaaraSQLiteOpenHelper extends SQLiteOpenHelper {

    public YaaraSQLiteOpenHelper(Context context) {
        super(context, YaaraConstants.DATABASES_NAME, null, YaaraConstants.DATABASES_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_SERVER_TABLE = "CREATE TABLE " + YaaraDataStore.Servers.TABLE_NAME +
                " (" + YaaraDataStore.Servers._ID + " " + YaaraDataStore.TYPE_PRIMARY_KEY + ", " +
                YaaraDataStore.Servers.NAME + " " + YaaraDataStore.TYPE_TEXT + ", " +
                YaaraDataStore.Servers.HOSTNAME + " " + YaaraDataStore.TYPE_TEXT_NOT_NULL + ", " +
                YaaraDataStore.Servers.PORT + " " + YaaraDataStore.TYPE_INT + ", " +
                YaaraDataStore.Servers.SECRET_TOKEN + " " + YaaraDataStore.TYPE_TEXT +
                ");";
        db.execSQL(CREATE_SERVER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException("Not supported");
    }

}

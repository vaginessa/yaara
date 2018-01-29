package com.mlieou.yaara.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.mlieou.yaara.constant.YaaraConstants;

/**
 * Created by mlieou on 1/22/18.
 */

public class YaaraSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public YaaraSQLiteOpenHelper(Context context) {
        super(context, YaaraConstants.DATABASES_NAME, null, YaaraConstants.DATABASES_VERSION);
        mContext = context;
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
        migrateFromSharedPreference(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException("Not supported");
    }


    /**
     * On version 0.0.1, the server profile was saved in shared preferences,
     * Do the migration
     */
    private void migrateFromSharedPreference(SQLiteDatabase db) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (sharedPreferences.contains("pref_aria2_host")) {
            String name = sharedPreferences.getString("pref_aria2_alias", "Server");
            String hostname = sharedPreferences.getString("pref_aria2_host", "");
            int port = sharedPreferences.getInt("pref_aria2_port", 6800);
            ContentValues contentValues = new ContentValues();
            contentValues.put(YaaraDataStore.Servers.NAME, name);
            contentValues.put(YaaraDataStore.Servers.HOSTNAME, hostname);
            contentValues.put(YaaraDataStore.Servers.PORT, port);
            long id = db.insert(YaaraDataStore.Servers.TABLE_NAME,
                    null,
                    contentValues);
            sharedPreferences.edit()
                    .putLong("pref_active_server", id)
                    .remove("pref_aria2_alias")
                    .remove("pref_aria2_host")
                    .remove("pref_aria2_port")
                    .apply();
        }
    }
}

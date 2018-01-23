package com.mlieou.yaara.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by mlieou on 1/21/18.
 */

public class YaaraDataProvider extends ContentProvider {

    private static final int CODE_SERVER = 100;
    private static final int CODE_SERVER_WITH_ID = 101;

    private YaaraSQLiteOpenHelper mDBOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(YaaraDataStore.AUTHORITY, YaaraDataStore.Servers.PATH, CODE_SERVER);
        matcher.addURI(YaaraDataStore.AUTHORITY,
                YaaraDataStore.Servers.PATH + "/#",
                CODE_SERVER_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDBOpenHelper = new YaaraSQLiteOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CODE_SERVER:
                cursor = mDBOpenHelper.getReadableDatabase().query(
                        YaaraDataStore.Servers.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CODE_SERVER_WITH_ID:
                cursor = mDBOpenHelper.getReadableDatabase().query(
                        YaaraDataStore.Servers.TABLE_NAME,
                        projection,
                        YaaraDataStore.Servers._ID + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Not implemented");
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not Implemented!");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri insertedUri;
        switch (sUriMatcher.match(uri)) {
            case CODE_SERVER:
                long id = mDBOpenHelper.getWritableDatabase().insert(
                        YaaraDataStore.Servers.TABLE_NAME,
                        null,
                        values
                );
                insertedUri = uri.buildUpon().appendPath(Long.toString(id)).build();
                break;
            default:
                throw new UnsupportedOperationException("Not Implemented!");
        }
        if (insertedUri != null)
            getContext().getContentResolver().notifyChange(uri, null);
        return insertedUri;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int rowDeleted;
        switch (sUriMatcher.match(uri)) {
            case CODE_SERVER:
                rowDeleted = mDBOpenHelper.getWritableDatabase().delete(
                        YaaraDataStore.Servers.TABLE_NAME,
                        "1",
                        null
                );
                break;
            case CODE_SERVER_WITH_ID:
                rowDeleted = mDBOpenHelper.getWritableDatabase().delete(
                        YaaraDataStore.Servers.TABLE_NAME,
                        YaaraDataStore.Servers._ID + " = ?",
                        new String[]{uri.getLastPathSegment()}
                );
                break;
            default:
                throw new UnsupportedOperationException("Not Implemented!");
        }
        if (rowDeleted > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int rowUpdated;
        switch (sUriMatcher.match(uri)) {
            case CODE_SERVER_WITH_ID:
                rowUpdated = mDBOpenHelper.getWritableDatabase().update(
                        YaaraDataStore.Servers.TABLE_NAME,
                        values,
                        YaaraDataStore.Servers._ID + " = ?",
                        new String[]{uri.getLastPathSegment()}
                );
                break;
            default:
                throw new UnsupportedOperationException("Not Implemented!");
        }
        if (rowUpdated > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }
}


package com.la.runners.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.la.runners.util.AppLogger;
import com.la.runners.util.network.NetworkService;

/**
 * @author luigi.agosti
 */
public class Provider extends ContentProvider {

    private static final UriManager urlMatcher = new UriManager();

    private DatabaseManager databaseManager;

    private SQLiteDatabase database;
    
    private SQLiteDatabase getDataBase() {
        if(database == null) {
            databaseManager = new DatabaseManager(getContext(), NetworkService.getSchema(getContext()));
            database = databaseManager.getWritableDatabase();
        }
        return database;
    }

    /**
     * Can delete only channel the consistency of the data is held by triggers.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rows = 0;
        switch (urlMatcher.match(uri)) {
            case Model.Run.INCOMING_COLLECTION: {
                rows = getDataBase().delete(Model.Run.NAME, selection, selectionArgs);
                break;
            }
            case Model.Location.INCOMING_COLLECTION: {
                rows = getDataBase().delete(Model.Location.NAME, selection, selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return rows;
    }

    @Override
    public String getType(Uri uri) {
        String type = null;
        switch (urlMatcher.match(uri)) {
            case Model.Run.INCOMING_COLLECTION: {
                type = Model.Run.COLLECTION_TYPE;
                break;
            }
            case Model.Run.INCOMING_ITEM: {
                type = Model.Run.ITEM_TYPE;
                break;
            }
            case Model.Location.INCOMING_COLLECTION: {
                type = Model.Location.COLLECTION_TYPE;
                break;
            }
            case Model.Location.INCOMING_ITEM: {
                type = Model.Location.ITEM_TYPE;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;
        switch (urlMatcher.match(uri)) {
            case Model.Run.INCOMING_COLLECTION: {
                try {
                    long id = getDataBase().insertOrThrow(Model.Run.NAME, null, values);
                    if(AppLogger.isDebugEnabled()) {
                        AppLogger.debug("insert : " + values + " _id = " + id);
                        AppLogger.debug(values.toString());
                    }
                    result = ContentUris.withAppendedId(Model.Run.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(result, null);
                } catch (SQLException e) {
                    if (AppLogger.isErrorEnabled()) {
                        AppLogger.error("Problem inserting Run", values, e);
                    }
                }
                break;
            }
            case Model.Location.INCOMING_COLLECTION: {
                try {
                    long id = getDataBase().insertOrThrow(Model.Location.NAME, null, values);
                    if(AppLogger.isDebugEnabled()) {
                        AppLogger.debug("insert : " + values + " _id = " + id);
                        AppLogger.debug(values.toString());
                    }
                    result = ContentUris.withAppendedId(Model.Location.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(result, null);
                } catch (SQLException e) {
                    if (AppLogger.isErrorEnabled()) {
                        AppLogger.error("Problem inserting Location", values, e);
                    }
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return result;
    }

    @Override
    public boolean onCreate() {
        if (AppLogger.isDebugEnabled()) {
            AppLogger.debug("Creating Provider");
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Cursor cursor;
        switch (urlMatcher.match(uri)) {
            case Model.Run.INCOMING_COLLECTION: {
                cursor = getDataBase().query(Model.Run.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case Model.Location.INCOMING_COLLECTION: {
                cursor = getDataBase().query(Model.Location.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            default: {
                throw new IllegalArgumentException("The URI '" + uri + "' is not implemented.");
            }
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (urlMatcher.match(uri)) {
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
    }

}

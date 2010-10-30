
package com.la.runners.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import com.la.runners.util.AppLogger;

/**
 * @author luigi.agosti
 */
public class Provider extends SyncProvider {

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
                super.getType(uri);
            }
        }
        return type;
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rows = 0;
        switch (urlMatcher.match(uri)) {
            case Model.Run.INCOMING_COLLECTION: {
                deleteSyncable(Model.Run.NAME, selection, selectionArgs);
                rows = getDataBase().delete(Model.Run.NAME, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(Model.Run.CONTENT_URI, null);
                break;
            }
            case Model.Location.INCOMING_COLLECTION: {
                deleteSyncable(Model.Location.NAME, selection, selectionArgs);
                rows = getDataBase().delete(Model.Location.NAME, selection, selectionArgs);
                break;
            }
            default: {
                super.delete(uri, selection, selectionArgs);
            }
        }
        return rows;
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
                    getContext().getContentResolver().notifyChange(Model.Location.CONTENT_URI, null);
                } catch (SQLException e) {
                    if (AppLogger.isErrorEnabled()) {
                        AppLogger.error("Problem inserting Location", values, e);
                    }
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("The URI '" + uri + "' is not implemented.");
            }
        }
        return result;
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
                cursor = super.query(uri, projection, selection, selectionArgs, sortOrder);
            }
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rows = 0;
        switch (urlMatcher.match(uri)) {
            case Model.Run.INCOMING_COLLECTION: {
                updateSyncable(Model.Run.NAME, values, selection, selectionArgs);
                rows = getDataBase().update(Model.Run.NAME, values, selection,
                        selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return rows;
    }

}

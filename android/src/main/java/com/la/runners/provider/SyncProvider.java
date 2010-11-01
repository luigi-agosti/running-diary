
package com.la.runners.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.la.runners.util.AppLogger;

public abstract class SyncProvider extends ContentProvider {

    public static class Syncable {

        public static final String ID = "_id";

        public static final String REMOTE_ID = "id";
        
        public static final String[] SELECTION = new String[]{ID, REMOTE_ID};

        public static final String id(Cursor c) {
            return c.getString(c.getColumnIndex(ID));
        }
        
        public static final String remoteId(Cursor c) {
            return c.getString(c.getColumnIndex(REMOTE_ID));
        }

    }

    public static class Sync extends Syncable {
        
        public static final int DELETE = 3;
        
        public static final int UPDATE = 2;
        
        public static final String NAME = "Sync";
        
        public static final Uri CONTENT_URI = Uri.parse(Model.CONTENT_PREFIX + Model.AUTHORITY + "/" + NAME);

        public static final String CRUD = "crud";

        public static final String TABLE_NAME = "name";
        
        public static final String CREATE_STM = "create table if not exists Sync(_id integer, id integer, name text, crud integer);";
        
        public static final String DROP_STM = "drop table if exists Sync;";

        public static final int INCOMING_COLLECTION = 0;

        public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.runners.sync";

        public static final String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.runners.sync";
        
    }
    
    private static final class SyncUriManager extends UriMatcher {

        public SyncUriManager(int code) {
            super(code);
            setUp();
        }

        public SyncUriManager() {
            super(UriMatcher.NO_MATCH);
            setUp();
        }

        public void setUp() {
            add(Sync.NAME, Sync.INCOMING_COLLECTION);
        }

        public void add(String path, int code) {
            super.addURI(Model.AUTHORITY, path, code);
        }
        
    }

    protected static final UriManager urlMatcher = new UriManager();
    
    private static final SyncUriManager syncUrlMatcher = new SyncUriManager();

    private DatabaseManager databaseManager;

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        if (AppLogger.isDebugEnabled()) {
            AppLogger.debug("Creating Provider");
        }
        return false;
    }

    protected SQLiteDatabase getDataBase() {
        if (database == null) {
            databaseManager = new DatabaseManager(getContext());
            database = databaseManager.getWritableDatabase();
        }
        return database;
    }

    protected void updateSyncable(String name, ContentValues values, String selection,
            String[] selectionArgs) {
        if (values.containsKey(Syncable.REMOTE_ID) && !selection.contains(Syncable.ID)) {
            AppLogger.debug("updateSyncable is coming from remote... skipping it");
            return;
        }
        AppLogger.debug("updateSyncable storing information about the field");
        insertCrud(name, selection, selectionArgs, Sync.UPDATE);
    }

    protected void deleteSyncable(String name, String selection, String[] selectionArgs) {
        if (selection.contains(Syncable.REMOTE_ID) && !selection.contains(Syncable.ID)) {
            AppLogger.debug("deleteSyncable is coming from remote... skipping it");
            return;
        }
        AppLogger.debug("deleteSyncable storing information about the field");
        insertCrud(name, selection, selectionArgs, Sync.DELETE);
    }
    
    private void insertCrud(String name, String selection, String[] selectionArgs, int crud) {
        Cursor c = null;
        try {
            c = getDataBase().query(name, Syncable.SELECTION, selection, selectionArgs, null, null, null);
            if(c.moveToNext()) {
                String remoteId = Syncable.remoteId(c);
                AppLogger.debug("Remote id is : " + remoteId);
                if(remoteId != null) {
                    ContentValues cv = new ContentValues();
                    cv.put(Syncable.ID, Syncable.id(c));
                    cv.put(Syncable.REMOTE_ID, remoteId);
                    cv.put(Sync.TABLE_NAME, name);
                    cv.put(Sync.CRUD, crud);
                    AppLogger.debug("Adding : " + cv);
                    getDataBase().insert(Sync.NAME, null, cv);
                }
            }
        } finally {
            c.close();
        }
    }
    
    @Override
    public String getType(Uri uri) {
        String type = null;
        switch (syncUrlMatcher.match(uri)) {
            case Sync.INCOMING_COLLECTION: {
                type = Sync.COLLECTION_TYPE;
                break;
            }
        }
        return type;
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rows = 0;
        switch (syncUrlMatcher.match(uri)) {
            case Sync.INCOMING_COLLECTION: {
                rows = getDataBase().delete(Sync.NAME, selection, selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return rows;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Cursor cursor;
        switch (syncUrlMatcher.match(uri)) {
            case Sync.INCOMING_COLLECTION: {
                cursor = getDataBase().query(Sync.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            default: {
                throw new IllegalArgumentException("The URI '" + uri + "' is not implemented.");
            }
        }
        return cursor;
    }
    
}

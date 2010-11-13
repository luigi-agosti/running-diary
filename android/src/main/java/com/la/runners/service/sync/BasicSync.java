
package com.la.runners.service.sync;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.la.runners.Runners;
import com.la.runners.parser.IdsParser;
import com.la.runners.parser.JsonParserIterator;
import com.la.runners.provider.Model;
import com.la.runners.provider.SyncProvider.Sync;
import com.la.runners.util.AppLogger;

public abstract class BasicSync implements Syncable {

    private static final String IS_NULL = " is null";
    
    public static final String PARAMETER = "= ?";

    private Uri uri;

    private String url;

    private int syncUpReourceId;

    public BasicSync(Uri uri, String url) {
        this.uri = uri;
        this.url = url;
    }

    @Override
    public void sync(Context context, SyncNotifier syncEventListener) {
        AppLogger.debug("1. sync up for : " + uri);
        syncUp(context, syncEventListener);
        AppLogger.debug("2. sync up updates : " + uri);
        syncUpUpdates(context, syncEventListener);
        AppLogger.debug("3. sync up deletes : " + uri);
        syncUpDeletes(context, syncEventListener);
//        AppLogger.debug("4. clean : " + uri);
//        clean(context, syncEventListener);
        AppLogger.debug("4. sync down : " + uri);
        syncDown(context, syncEventListener);
        AppLogger.debug("5. sync end : " + uri);
    }

    @Override
    public void syncUp(Context context, SyncNotifier syncEventListener) {
        syncEventListener.message(context, syncUpReourceId);
        syncUp(context, Sync.REMOTE_ID + IS_NULL);
    }
    
    private void syncUp(Context context, String selection) {
        Cursor c = null;
        try {
            c = context.getContentResolver().query(uri, null, selection, null, Sync.ID + " limit 10");
            if(c.getCount() > 0) {
                String result = convert(c);
                AppLogger.debug("Result : " + result);
                if(!"[]".equals(result)) {
                    AppLogger.debug("posting result");
                    IdsParser parser = new IdsParser(Runners.getHttpManager(context).post(context, url, result));
                    while(parser.hasNext()) {
                        ContentValues cv = parser.next();
                        String id = cv.getAsString(Sync.ID);
                        cv.remove(Sync.ID);
                        context.getContentResolver().update(uri, cv, Sync.ID + Model.PARAMETER, new String[]{id});
                        handleRelations(context, id, cv.getAsString(Sync.REMOTE_ID));
                    }
                }
                c.close();
                syncUp(context, selection);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    protected abstract String convert(Cursor c);

    @Override
    public void syncUpUpdates(Context context, SyncNotifier syncEventListener) {
        StringBuilder selection = new StringBuilder(Sync.ID + " in (");
        Cursor c = null;
        try {
            c = context.getContentResolver().query(Sync.CONTENT_URI, null, Sync.CRUD + PARAMETER, new String[]{"" + Sync.UPDATE}, null);
            while(c.moveToNext()) {
                selection.append(Sync.id(c));
                if(!c.isLast()) {
                    selection.append(",");
                }
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        selection.append(")");
        syncUp(context, selection.toString());
    }

    @Override
    public void syncUpDeletes(Context context, SyncNotifier syncEventListener) {
        Cursor c = null;
        try {
        	AppLogger.debug("getting list of deletes to send");
            c = context.getContentResolver().query(Sync.CONTENT_URI, null, Sync.CRUD + PARAMETER, new String[]{"" + Sync.DELETE}, null);
            AppLogger.debug("query done");
            while(c.moveToNext()) {
            	AppLogger.debug("deleting");
            	try {
            		String remoteId = Sync.remoteId(c);
            		Runners.getHttpManager(context).delete(context, url + "?remoteId=" + remoteId);
            		//context.getContentResolver().delete(Sync.CONTENT_URI, Sync.REMOTE_ID + PARAMETER + " and " + Sync.CRUD + PARAMETER, new String[]{remoteId, "" + Sync.DELETE});
            		AppLogger.debug("delete of the Sync row");
            	} catch (Throwable e) {
            		AppLogger.error(e);
            	}
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    @Override
    public void clean(Context context, SyncNotifier syncEventListener) {
        context.getContentResolver().delete(uri, Sync.REMOTE_ID + IS_NULL, null);
    }

    @Override
    public void syncDown(Context context, SyncNotifier syncEventListener) {
    	AppLogger.debug("syncDown");
        JsonParserIterator parser = instanziateParser(Runners.getHttpManager(context)
                .getUrlAsStream(url, context));
        AppLogger.debug("syncDown after parser");
        List<Long> ids = new ArrayList<Long>();
        Cursor c = null; 
        try {
            c = context.getContentResolver().query(uri, new String[]{Sync.REMOTE_ID}, null, null, null);
            while(c.moveToNext()) {
                ids.add(c.getLong(c.getColumnIndex(Sync.REMOTE_ID)));
            }
        } finally {
            if(c!=null) {
                c.close();
            }
        }
        AppLogger.debug("while");
        while (parser.hasNext()) {
        	AppLogger.debug("Next");
            ContentValues cv = parser.next();
            AppLogger.debug("Next : " + cv);
            boolean hasRemoteId = cv.containsKey(Sync.REMOTE_ID);
            if(hasRemoteId) {
                Long remoteId = cv.getAsLong(Sync.REMOTE_ID);
                if(ids.contains(remoteId)) {
                	AppLogger.debug("Update start");
                    context.getContentResolver().update(uri, cv, Sync.REMOTE_ID + PARAMETER, 
                            new String[]{"" + remoteId});
                    AppLogger.debug("Update end");
                } else {
                	AppLogger.debug("Insert start");
                    context.getContentResolver().insert(uri, cv);
                    AppLogger.debug("Insert end");
                }
            } else {
                AppLogger.warn("There is no remote id!");
            }
        }
    }

    protected abstract JsonParserIterator instanziateParser(InputStream urlAsStream);
    
    protected abstract void handleRelations(Context context, String id, String rid);

    protected void setSyncUpReourceId(int syncUpReourceId) {
        this.syncUpReourceId = syncUpReourceId;
    }

}

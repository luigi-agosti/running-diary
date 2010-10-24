
package com.la.runners.service.sync;

import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.la.runners.parser.JsonParserIterator;
import com.la.runners.provider.SyncProvider.Sync;
import com.la.runners.util.AppLogger;
import com.la.runners.util.network.NetworkService;

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
        AppLogger.debug("4. clean : " + uri);
        clean(context, syncEventListener);
        AppLogger.debug("5. sync down : " + uri);
        syncDown(context, syncEventListener);
        AppLogger.debug("6. sync end : " + uri);
    }

    @Override
    public void syncUp(Context context, SyncNotifier syncEventListener) {
        syncEventListener.message(context, syncUpReourceId);
        syncUp(context, Sync.REMOTE_ID + IS_NULL);
    }
    
    private void syncUp(Context context, String selection) {
        Cursor c = null;
        try {
            c = context.getContentResolver().query(uri, null, selection, null, null);
            String result = convert(c);
            AppLogger.debug("Result : " + result);
            if(!"[]".equals(result)) {
                AppLogger.debug("posting result");
                NetworkService.getHttpManager(context).post(context, url, result);
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
            c = context.getContentResolver().query(Sync.CONTENT_URI, null, Sync.CRUD + PARAMETER, new String[]{"" + Sync.DELETE}, null);
            while(c.moveToNext()) {
                NetworkService.getHttpManager(context).delete(context, url + "/" + Sync.remoteId(c));
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
        JsonParserIterator parser = instanziateParser(NetworkService.getHttpManager(context)
                .getUrlAsStream(url, context));
        while (parser.hasNext()) {
            context.getContentResolver().insert(uri, parser.next());
        }
    }

    protected abstract JsonParserIterator instanziateParser(InputStream urlAsStream);

    protected void setSyncUpReourceId(int syncUpReourceId) {
        this.syncUpReourceId = syncUpReourceId;
    }

}

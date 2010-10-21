package com.la.runners.service.sync.custom;

import android.content.Context;
import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.parser.RunParser;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.util.network.NetworkService;

public class RunSync extends BasicSync {

    @Override
    public void syncUp(Context context, SyncNotifier syncNotifier) {
        syncNotifier.message(context, R.string.syncService_run);
        Cursor c = null;
        try {
            c = Model.Run.notSync(context);
            NetworkService.postRun(context, Model.Run.convertAll(c));
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    @Override
    public void clean(Context context, SyncNotifier syncNotifier) {
        context.getContentResolver().delete(Model.Run.CONTENT_URI, null, null);
    }

    @Override
    public void syncDown(Context context, SyncNotifier syncNotifier) {
        RunParser parser = NetworkService.getRunParser(context);
        while (parser.hasNext()) {
            context.getContentResolver().insert(Model.Run.CONTENT_URI, parser.next());
        }
    }

}

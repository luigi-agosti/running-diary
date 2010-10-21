package com.la.runners.service.sync.custom;

import android.content.Context;
import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.util.network.NetworkService;

public class LocationSync extends BasicSync {

    @Override
    public void syncUp(Context context, SyncNotifier syncNotifier) {
        syncNotifier.message(context, R.string.syncService_location);
        Cursor c = null;
        try {
            c = Model.Location.notSync(context);
            NetworkService.postLocation(context, Model.Location.convertAll(c));
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    @Override
    public void clean(Context context, SyncNotifier syncNotifier) {
        context.getContentResolver().delete(Model.Location.CONTENT_URI, null, null);
    }

    @Override
    public void syncDown(Context context, SyncNotifier syncNotifier) {
        //Do not store stuff for now
    }
    
    
}

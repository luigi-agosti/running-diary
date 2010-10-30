package com.la.runners.service.sync.custom;

import android.content.Context;

import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.util.Notifier;

public class CustomSyncEventListener implements SyncNotifier {

    @Override
    public void message(Context context, int resId, Object...objects) {
        Notifier.notify(context, resId, objects);
    }

    @Override
    public void failure(Context context, int resId, Object...objects) {
        Notifier.notify(context, resId, objects);
    }

}

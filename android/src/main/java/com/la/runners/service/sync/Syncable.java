package com.la.runners.service.sync;

import android.content.Context;

public interface Syncable {
    
    public void sync(Context context, SyncNotifier syncEventListener);

    void syncDown(Context context, SyncNotifier syncEventListener);

    void clean(Context context, SyncNotifier syncEventListener);

    void syncUp(Context context, SyncNotifier syncEventListener);

    void syncUpUpdates(Context context, SyncNotifier syncEventListener);

    void syncUpDeletes(Context context, SyncNotifier syncEventListener);

}

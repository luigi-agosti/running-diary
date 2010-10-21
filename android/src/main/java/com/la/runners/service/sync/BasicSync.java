package com.la.runners.service.sync;

import android.content.Context;

public abstract class BasicSync implements Syncable {
    
    @Override
    public void sync(Context context, SyncNotifier syncEventListener) {
        syncUp(context, syncEventListener);
        clean(context, syncEventListener);
        syncDown(context, syncEventListener);
    }

    @Override
    public abstract void syncUp(Context context, SyncNotifier syncEventListener);
    
    @Override
    public abstract void clean(Context context, SyncNotifier syncEventListener);
    
    @Override
    public abstract void syncDown(Context context, SyncNotifier syncEventListener);

}

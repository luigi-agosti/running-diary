package com.la.runners.service.sync;

import android.content.Context;

public interface SyncNotifier {
    
    void message(Context context, int resId, Object...objects);
    
    void failure(Context context, int resId, Object...objects);

}

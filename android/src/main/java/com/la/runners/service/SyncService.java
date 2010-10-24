
package com.la.runners.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.la.runners.R;
import com.la.runners.activity.Preferences;
import com.la.runners.exception.ConnectionException;
import com.la.runners.exception.ResourceException;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.service.sync.custom.CustomSyncEventListener;
import com.la.runners.service.sync.custom.LocationSync;
import com.la.runners.service.sync.custom.ProfileSync;
import com.la.runners.service.sync.custom.RunSync;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;
import com.la.runners.util.network.GoogleAuth;

public class SyncService extends IntentService {

    private static final String ACTION_SYNC = "com.la.runners.service.SyncService.ACTION_SYNC";
    private static final String ACTION_SAVE_PROFILE = "com.la.runners.service.SyncService.ACTION_SAVE_PROFILE";
    private static final String ACTION_SYNC_PROFILE = "com.la.runners.service.SyncService.ACTION_SYNC_PROFILE";

    public SyncService() {
        super(SyncService.class.getSimpleName());
    }

    public SyncService(String name) {
        super(name);
    }

    public static final void startDataSync(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_SYNC);
        context.startService(intent);
    }

    public static final void startSaveProfile(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_SAVE_PROFILE);
        context.startService(intent);
    }

    public static final void startSyncProfile(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_SYNC_PROFILE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context c = getApplicationContext();
        Notifier.notify(c, R.string.syncService_start);
        try {
            String account = Preferences.getAccount(c);
            if(account == null) {
                Notifier.notify(c, R.string.error_9);
                return;
            }
            GoogleAuth googleAuth = GoogleAuth.getInstance();
            int loginAttempts = 0;
            while(!googleAuth.isLoggedIn(c)) {
                if(loginAttempts == 0) {
                    googleAuth.login(c, account, intent);
                }
                if(loginAttempts > 3) {
                    throw new ConnectionException(R.string.error_13);
                }
                loginAttempts++;
            }
            SyncNotifier sel = new CustomSyncEventListener();
            if (ACTION_SYNC.equals(intent.getAction())) {
                new RunSync().sync(c, sel);
                new LocationSync().sync(c, sel);
            } else if(ACTION_SAVE_PROFILE.equals(intent.getAction())) {
                new ProfileSync().syncUp(c, sel);
            } else if(ACTION_SYNC_PROFILE.equals(intent.getAction())) {
                new ProfileSync().syncDown(c, sel);
            }
            Notifier.notify(c, R.string.syncService_end);
        } catch (ResourceException ce) {
            Notifier.notify(c, ce.getResourceId(), ce.getObjects());
        } catch (Throwable e) {
            Notifier.notify(c, R.string.error_8, e.getMessage());
            AppLogger.error(e);
        }
    }
    
}

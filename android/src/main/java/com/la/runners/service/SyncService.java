
package com.la.runners.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.activity.Preferences;
import com.la.runners.exception.ConnectionException;
import com.la.runners.exception.ParserException;
import com.la.runners.parser.ProfileParser;
import com.la.runners.parser.RunParser;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.service.sync.custom.CustomSyncEventListener;
import com.la.runners.service.sync.custom.LocationSync;
import com.la.runners.service.sync.custom.ProfileSync;
import com.la.runners.service.sync.custom.RunSync;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;
import com.la.runners.util.network.GoogleAuth;
import com.la.runners.util.network.NetworkService;

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

    private void notify(int resourceId, Object...objects) {
        String message = String.format(getApplicationContext().getString(resourceId), objects);
        AppLogger.debug(message);
        Notifier.notify(getApplicationContext(), message);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = getApplicationContext();
        notify(R.string.syncService_start);
        try {
            String account = Preferences.getAccount(context);
            if(account == null) {
                notify(R.string.error_9);
                return;
            }
            GoogleAuth googleAuth = GoogleAuth.getInstance();
            int loginAttempts = 0;
            while(!googleAuth.isLoggedIn(context) || !(loginAttempts < 5)) {
                if(loginAttempts == 0) {
                    googleAuth.login(context, account, intent);
                }
                loginAttempts++;
            }
            SyncNotifier sel = new CustomSyncEventListener();
            if (ACTION_SYNC.equals(intent.getAction())) {
                new RunSync().sync(context, sel);
                new LocationSync().sync(context, sel);
            } else if(ACTION_SAVE_PROFILE.equals(intent.getAction())) {
                new ProfileSync().syncUp(context, sel);
            } else if(ACTION_SYNC_PROFILE.equals(intent.getAction())) {
                new ProfileSync().syncDown(context, sel);
            }
        } catch (ConnectionException ce) {
            notify(ce.getResourceId(), ce.getObjects());
        } catch (ParserException ce) {
            notify(ce.getResourceId(), ce.getObjects());
        } catch (Throwable e) {
            notify(R.string.error_8, e.getMessage());
        }
        notify(R.string.syncService_end);
    }
    
}


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
            if (!googleAuth.isLoggedIn(context)) {
                googleAuth.login(context, account, intent);
            }
            if (ACTION_SYNC.equals(intent.getAction())) {
                syncRun(context);
                notify(R.string.syncService_run);
                syncLocation(context);
                notify(R.string.syncService_location);
            } else if(ACTION_SAVE_PROFILE.equals(intent.getAction())) {
                saveProfile(context);
            } else if(ACTION_SYNC_PROFILE.equals(intent.getAction())) {
                syncProfile(context);
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

    private void syncRun(Context context) {
        Cursor c = null;
        try {
            c = Model.Run.notSync(context);
            NetworkService.postRun(context, Model.Run.convertAll(c));
        } finally {
            if (c != null) {
                c.close();
            }
        }
        context.getContentResolver().delete(Model.Run.CONTENT_URI, null, null);
        RunParser parser = NetworkService.getRunParser(context);
        while (parser.hasNext()) {
            getContentResolver().insert(Model.Run.CONTENT_URI, parser.next());
        }
    }
    
    private void syncLocation(Context context) {
        Cursor c = null;
        try {
            c = Model.Location.notSync(context);
            NetworkService.postLocation(context, Model.Location.convertAll(c));
        } finally {
            if (c != null) {
                c.close();
            }
        }
        context.getContentResolver().delete(Model.Location.CONTENT_URI, null, null);
    }
    
    private void saveProfile(Context context) {           
        NetworkService.postProfile(context, Model.Profile.convert(context));
    }
    
    private void syncProfile(Context context) {
        AppLogger.debug("saving profile");
        ProfileParser parser = NetworkService.getProfileParser(context);
        AppLogger.debug("parser was successful");
        while (parser.hasNext()) {
            ContentValues cv = parser.next();
            AppLogger.debug("value of the parser are : " + cv);
            if(cv.containsKey(Model.Profile.SHOES)) {
                Preferences.setShoes(context, cv.getAsBoolean(Model.Profile.SHOES));
            }
            if(cv.containsKey(Model.Profile.WEATHER)) {
                Preferences.setWeather(context, cv.getAsBoolean(Model.Profile.WEATHER));
            }
            if(cv.containsKey(Model.Profile.WEIGHT)) {
                Preferences.setWeight(context, cv.getAsBoolean(Model.Profile.WEIGHT));
            }
            if(cv.containsKey(Model.Profile.HEART_RATE)) {
                Preferences.setHeartRate(context, cv.getAsBoolean(Model.Profile.HEART_RATE));
            }
            if(cv.containsKey(Model.Profile.NICKNAME)) {
                Preferences.setNickname(context, cv.getAsString(Model.Profile.NICKNAME));
            }
            if(cv.containsKey(Model.Profile.CREATED)) {
                Preferences.setCreated(context, cv.getAsLong(Model.Profile.CREATED));
            }
            if(cv.containsKey(Model.Profile.MODIFIED)) {
                Preferences.setModified(context, cv.getAsLong(Model.Profile.MODIFIED));
            }
        }
    }
    
}

package com.la.runners.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.la.runners.activity.Preferences;
import com.la.runners.parser.RunParser;
import com.la.runners.provider.Model;
import com.la.runners.provider.Query;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;
import com.la.runners.util.network.GoogleAuth;
import com.la.runners.util.network.NetworkService;

public class SyncService extends IntentService {

	private static final String ACTION_SYNC = "com.la.runners.service.SyncService.ACTION_SYNC";
	
	public SyncService() {
		super(SyncService.class.getSimpleName());
	}
	
	public SyncService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	    Context context = getApplicationContext();
	    notify("Synchronizing data");
	    try {
	        GoogleAuth googleAuth = GoogleAuth.getInstance();
	        if(!googleAuth.isLoggedIn(context)) {
	            googleAuth.login(context, Preferences.getAccount(context), intent);
	        }
	        if(ACTION_SYNC.equals(intent.getAction())) {
	            if(!syncUp(context)) {
	                notify("Connection problem while sending data to the server");
	                return;
	            }
	            cleanUp(context);
	            syncDown(context);
	        }	        
	    } catch(Exception e) {
	        notify("Synchronizing failed");
	        if(AppLogger.isErrorEnabled()) {
	            AppLogger.error(e);
	        }
	        Notifier.notifyBlockingProblemFromBackGround(getApplicationContext(), 
	                "Connection problem during the sync operation, please try again");
	    }
	    notify("Sync successful");
	}
	
	private void cleanUp(Context context) {
        context.getContentResolver().delete(Model.Run.CONTENT_URI, null, null);
    }

    private boolean syncUp(Context context) {
	    Cursor c = null; 
	    try {
	        c = Query.Run.notSync(context);
	        return NetworkService.postRun(context, Model.Run.convertAll(c));
	    } finally {
	        if(c != null) {
	            c.close();
	        }
	    }
	}

	private void syncDown(Context context) {
    	RunParser parser = NetworkService.getRunParser(context);
    	if(parser == null) {
    	    AppLogger.error("the parser was null");
    	    return;
    	}
    	while(parser.hasNext()) {
    	    AppLogger.debug("inserting new run");
    		getContentResolver().insert(Model.Run.CONTENT_URI, parser.next());
    	}
	}

	public static final void startIntent(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_SYNC);
        context.startService(intent);
    }

    private void notify(String message) {
        Notifier.notifyBlockingProblemFromBackGround(getApplicationContext(), message);
    }
}

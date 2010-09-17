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
        GoogleAuth googleAuth = GoogleAuth.getInstance();
        if(!googleAuth.isLoggedIn(context)) {
            googleAuth.login(context, Preferences.getAccount(context), intent);
        }
	    if(ACTION_SYNC.equals(intent.getAction())) {
	        syncUpRun(context);
			syncDownRun(context);
		}
	}
	
	private void syncUpRun(Context context) {
	    Cursor c = null; 
	    try {
	        c = Query.Run.notSync(context);
	        NetworkService.postRun(context, Model.Run.convertAll(c));
	    } finally {
	        if(c != null) {
	            c.close();
	        }
	    }
	}

	private void syncDownRun(Context context) {
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

}

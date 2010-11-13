
package com.la.runners;

import android.app.Application;
import android.content.Context;

import com.la.runners.util.AppLogger;
import com.la.runners.util.network.HttpManager;

/**
 * @author luigi.agosti
 */
public class Runners extends Application {

    private static HttpManager httpManager;

    private static Runners instance;
    
    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppLogger.isInfoEnabled()) {
            AppLogger.info("============================================");
            AppLogger.info("Create event : Start up");
        }
        instance = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (AppLogger.isInfoEnabled()) {
            AppLogger.info("Low memory waring.");
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (AppLogger.isInfoEnabled()) {
            AppLogger.info("On terminate : Shutting down");
            AppLogger.info("============================================");
        }
    }

    public static final HttpManager getHttpManager(Context c) {
    	AppLogger.debug("getHttpManager");
        if (httpManager == null) {
        	AppLogger.debug("New instance of HttpManager");
            httpManager = new HttpManager(c);
        }
        return httpManager;
    }

    public static Runners getInstance() {
        return instance;
    }

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}


package com.la.runners;

import android.app.Application;

import com.la.runners.util.AppLogger;
import com.la.runners.util.network.HttpManager;

/**
 * @author luigi.agosti
 */
public class Runners extends Application {

    private HttpManager httpManager;

    private static Runners instance;

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

    public final HttpManager getHttpManager() {
        if (httpManager == null) {
            httpManager = new HttpManager(getApplicationContext());
        }
        return httpManager;
    }

    public static Runners getInstance() {
        return instance;
    }

}

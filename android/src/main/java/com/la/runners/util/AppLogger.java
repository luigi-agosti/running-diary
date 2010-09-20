
package com.la.runners.util;

import android.content.ContentValues;
import android.util.Log;

/**
 * This is another version of Logger implementation, the difference is that
 * everything is static and is using a TAG that is the name of the application
 * class. On my personal opinion loosing the name of the class that is logging
 * in android is a good compromise between information and performance
 * 
 * @author luigi.agosti
 */
public class AppLogger {

    private static final String TAG = "Runners";
    
    public interface LoggerConfig {

        boolean IS_DEBUG_ENABLED = true;

        boolean IS_INFO_ENABLED = true;

        boolean IS_WARNING_ENABLED = true;

        boolean IS_ERROR_ENABLED = true;
    }
    
    public static final void warn(String message) {
        Log.w(TAG, message);
    }

    public static final void warn(String message, Throwable t) {
        Log.w(TAG, message);
        if(t != null) {
            Log.w(TAG, t.getMessage());
        }
    }

    public static final void debug(String message) {
        Log.d(TAG, message);
    }
    
    public static final void debug(String message, Throwable t) {
        Log.d(TAG, message);
        if(t != null) {
            Log.d(TAG, t.getMessage());
        }
    }

    public static final void info(String message) {
        Log.i(TAG, message);
    }
    
    public static final void info(String message, Throwable t) {
        Log.i(TAG, message);
        if(t != null) {
            Log.i(TAG, t.getMessage());
        }
    }
    
    public static final void error(String message) {
        Log.e(TAG, message);
    }
    
    public static final void error(String message, Throwable t) {
        Log.e(TAG, message);
        t.printStackTrace();
    }

    public static final void error(String message, ContentValues cv, Throwable t) {
        error("===========================================");
        error(message);
        error(cv.toString());                        
        error(t);
        error("===========================================");
    }
    
    public static final void logVisibly(String message){
        debug("===========================================");
        debug("===========================================");
        debug(message);
        debug("===========================================");
        debug("===========================================");
    }

    public static final void error(Throwable t) {
        t.printStackTrace();
    }
    
    public static final boolean isDebugEnabled() {
        return LoggerConfig.IS_DEBUG_ENABLED;
    }
    
    public static final boolean isInfoEnabled() {
        return LoggerConfig.IS_INFO_ENABLED;
    }
    
    public static final boolean isErrorEnabled() {
        return LoggerConfig.IS_ERROR_ENABLED;
    }
    
    public static final boolean isWarnEnabled() {
        return LoggerConfig.IS_WARNING_ENABLED;
    }
    
}

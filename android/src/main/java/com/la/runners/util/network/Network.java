
package com.la.runners.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.la.runners.util.AppLogger;

/**
 * Utility class to verify the availability of networks will be useful when we
 * implements the automatic sync. Change methods to static final
 * 
 * @author fabio.testolin, luigi.agosti
 */
public class Network {

    private static final String WIFI = "wifi";

    private static final String MOBILE = "mobile";

    /**
     * Return null is the there is a network available and connected.
     * 
     * @param context
     * @return
     */
    public static final boolean isNetworkAvailable(Context context) {
        NetworkInfo ni = getConnectivityManager(context).getActiveNetworkInfo();
        if (ni == null) {
            AppLogger.warn("Active network info is not available!");
            return false;
        }
        return ni.isConnected();
    }

    /**
     * Check if the active network is Wi-Fi.
     * 
     * @param context
     * @return
     */
    public static final boolean isOnWifi(Context context) {
        return getActiveNetworkInfo(context).getTypeName().equals(WIFI);
    }

    /**
     * Check if the active network is mobile.
     * 
     * @param context
     * @return
     */
    public static final boolean isOnMobile(Context context) {
        return getActiveNetworkInfo(context).getTypeName().equals(MOBILE);
    }

    private static final NetworkInfo getActiveNetworkInfo(Context context) {
        NetworkInfo info = getConnectivityManager(context).getActiveNetworkInfo();
        AppLogger.logVisibly("Network info requested are : " + info.getTypeName());
        return info;
    }

    private static final ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}

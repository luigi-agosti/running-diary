
package com.la.runners.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.la.runners.provider.Model;
import com.la.runners.service.track.AvarageTrackManager;
import com.la.runners.service.track.StoreManager;
import com.la.runners.service.track.TrackManager;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;

public class RunTrackingService extends Service implements LocationListener, StoreManager {

    private static final int MIN_REQUIRED_ACCURACY = 200;
    
    private static final double MULTIPLIER = 1000000D;

    private TrackManager trackManager = new AvarageTrackManager(this, 5);

    private LocationManager locationManager;

    private final Handler handler = new Handler();

    private final TimerTask checkLocationListener = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    AppLogger.debug("Re-registering location listener.");
                    unregisterLocationListener();
                    registerLocationListener();
                }
            });
        }
    };

    private final Timer timer = new Timer();

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        boolean isGgpsEnabled = false;
        try {
            isGgpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        if (!isGgpsEnabled) {
            Notifier.toastMessage(this, "Gps is not enabled! you have to enable it");
            return;
        }
        registerLocationListener();
        timer.schedule(checkLocationListener, 1000 * 60 * 5, 1000 * 60);
        trackManager.start();
    }

    @Override
    public void onDestroy() {
        unregisterLocationListener();
        checkLocationListener.cancel();
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        AppLogger.debug("Location change");
        try {
            if (location == null) {
                AppLogger.warn("Location changed, but location is null.");
                return;
            }
            if (location.getAccuracy() > MIN_REQUIRED_ACCURACY) {
                AppLogger.debug("Not recording. Bad accuracy.");
                return;
            }
            trackManager.updateLocation(location);
        } catch (Error e) {
            AppLogger.error("Error in onLocationChanged", e);
            throw e;
        } catch (RuntimeException e) {
            AppLogger.error("Trapping exception in onLocationChanged", e);
            throw e;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public boolean stopService(Intent name) {
        AppLogger.debug("Stopping tracking service");
        trackManager.stop();
        unregisterLocationListener();
        return super.stopService(name);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        AppLogger.debug("Starting tracking service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onProviderDisabled(String provider) {
        AppLogger.debug("onProviderDisabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        AppLogger.debug("onProviderEnabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        AppLogger.debug("Provider is enabled");
    }

    @Override
    public void store(double latitude, double longitude, double altitude, long time, long timestamp, 
            double speed, double distance, double totalDistance) {
        ContentValues cv = new ContentValues();
        cv.put(Model.Location.LATITUDE, (long)(latitude * MULTIPLIER));
        cv.put(Model.Location.LONGITUDE, (long)(longitude * MULTIPLIER));
        cv.put(Model.Location.ALTITUDE, (long)(altitude * MULTIPLIER));
        cv.put(Model.Location.SPEED, (long)(speed * MULTIPLIER));
        cv.put(Model.Location.TIME, time);
        cv.put(Model.Location.TIMESTAMP, timestamp);
        cv.put(Model.Location.DISTANCE, (long)distance);
        cv.put(Model.Location.TOTAL_DISTANCE, (long)totalDistance);
        getContentResolver().insert(Model.Location.CONTENT_URI, cv);
    }

    private void registerLocationListener() {
        if (locationManager == null) {
            AppLogger.error("Do not have any location manager.");
            return;
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            AppLogger.debug("Location listener registered");
        } catch (RuntimeException e) {
            AppLogger.error("Could not register location listener", e);
        }
    }

    private void unregisterLocationListener() {
        if (locationManager == null) {
            AppLogger.error("Do not have any location manager.");
            return;
        }
        locationManager.removeUpdates(this);
        AppLogger.debug("Location listener unregistered");
    }

}

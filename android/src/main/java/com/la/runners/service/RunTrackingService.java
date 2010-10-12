package com.la.runners.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.la.runners.util.AppLogger;

public class RunTrackingService extends Service implements LocationListener {

	private static final String GPS_PROVIDER = "gps";

	private static final int MIN_REQUIRED_ACCURACY = 200;

	private Location lastValidLocation = null;

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
		AppLogger.debug("TrackRecordingService.onCreate");
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		registerLocationListener();
		
		timer.schedule(checkLocationListener, 1000 * 60 * 5, 1000 * 60);
	}

	@Override
	public void onDestroy() {
		AppLogger.debug("TrackRecordingService.onDestroy");
		unregisterLocationListener();
		checkLocationListener.cancel();
		timer.cancel();
		super.onDestroy();
	}

	public void registerLocationListener() {
		if (locationManager == null) {
			AppLogger.error("Do not have any location manager.");
			return;
		}
		try {
			// 30 sec and 5 meters
			locationManager
					.requestLocationUpdates(GPS_PROVIDER, 0, 0, this);
			AppLogger.debug("...location listener now registered");
		} catch (RuntimeException e) {
			AppLogger.error("Could not register location listener", e);
		}
	}

	public void unregisterLocationListener() {
		if (locationManager == null) {
			AppLogger.error("Do not have any location manager.");
			return;
		}
		locationManager.removeUpdates(this);
		AppLogger.debug("Location listener now unregistered.");
	}

	@Override
	public void onLocationChanged(Location location) {
		AppLogger.debug("TrackRecordingService.onLocationChanged");
		try {
			if (location == null) {
				AppLogger.warn("Location changed, but location is null.");
				return;
			}
			if (location.getAccuracy() > MIN_REQUIRED_ACCURACY) {
				AppLogger.debug("Not recording. Bad accuracy.");
				return;
			}
			// HERE i SHOULD TAKE THE PREVIOUS TRACK

			if (isValidLocation(location)) {
				AppLogger.debug("location is valid");
			} else {
				AppLogger.debug("location is not valid");
			}

			// GETTING LAST LOCATION

			if (lastValidLocation == null) {
				lastValidLocation = location;				
			}
			AppLogger.logVisibly("Longitude : " + location.getLongitude() + "Latitude : " + location.getLatitude() + " Distance : "
					+ location.distanceTo(lastValidLocation));

		} catch (Error e) {
			AppLogger.error("Error in onLocationChanged", e);
			throw e;
		} catch (RuntimeException e) {
			AppLogger.error("Trapping exception in onLocationChanged", e);
			throw e;
		}
		lastValidLocation = location;
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
		AppLogger.debug("TrackRecordingService.stopService");
		unregisterLocationListener();
		return super.stopService(name);
	}

	@Override
	public void onStart(Intent intent, int startId) {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public static boolean isValidLocation(Location location) {
		return location != null && Math.abs(location.getLatitude()) <= 90
				&& Math.abs(location.getLongitude()) <= 180;
	}

}

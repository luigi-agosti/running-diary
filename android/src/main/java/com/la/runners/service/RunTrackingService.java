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

public class RunTrackingService extends Service implements LocationListener,
		StoreManager {

	private static final int MIN_REQUIRED_ACCURACY = 200;

	private TrackManager trackManager = new AvarageTrackManager(this);

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

		boolean isGgpsEnabled = false;
		try {
			isGgpsEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		if (!isGgpsEnabled) {
			Notifier.toastMessage(this,
					"Gps is not enabled! you have to enable it");
			return;
		}
		registerLocationListener();
		timer.schedule(checkLocationListener, 1000 * 60 * 5, 1000 * 60);
		trackManager.start();
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
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
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
			changeLocation(location);
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
		AppLogger.debug("TrackRecordingService.stopService");
		trackManager.stop();
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

	private static final boolean isValidLocation(Location location) {
		return location != null && Math.abs(location.getLatitude()) <= 90
				&& Math.abs(location.getLongitude()) <= 180;
	}

	private void changeLocation(Location location) {
		// storeLocation(location, false);
		AppLogger.debug("Location : " + location.toString());
		trackManager.updateLocation(location);
	}

	private int distance = 0;
	private long time = 0l;

	private void storeLocation(Location location, boolean isStart) {
		if (isValidLocation(location)) {
			ContentValues cv = new ContentValues();
			cv.put(Model.Location.LATITUDE, location.getLatitude() * 1E6);
			cv.put(Model.Location.LONGITUDE, location.getLongitude() * 1E6);
			cv.put(Model.Location.ACCURACY, location.getAccuracy());
			cv.put(Model.Location.ALTITUDE, location.getAltitude());
			cv.put(Model.Location.SPEED, location.getSpeed());
			cv.put(Model.Location.TIME, location.getTime());
			float speed = 0;
			if (!isStart) {
				speed = distance * 1000 / (System.currentTimeMillis() - time);
				distance += lastValidLocation.distanceTo(location);
				cv.put(Model.Location.DISTANCE, lastValidLocation
						.distanceTo(location));
			} else {
				time = System.currentTimeMillis();
				distance = 0;
				cv.put(Model.Location.DISTANCE, 0);
			}
			getContentResolver().insert(Model.Location.CONTENT_URI, cv);
			Notifier.toastMessage(this, "New location : " + distance
					+ " avarage speed : " + speed);
			lastValidLocation = location;
		} else {
			AppLogger.debug("Location is not valid");
		}
	}

	@Override
	public void store(double latitude, double longitude, double altitude,
			float accuracy, long time, double speed, double distance) {
		ContentValues cv = new ContentValues();
		cv.put(Model.Location.LATITUDE, latitude * 1E6);
		cv.put(Model.Location.LONGITUDE, longitude * 1E6);
		cv.put(Model.Location.ACCURACY, accuracy);
		cv.put(Model.Location.ALTITUDE, altitude);
		cv.put(Model.Location.SPEED, speed);
		cv.put(Model.Location.TIME, time);
		cv.put(Model.Location.DISTANCE, distance);
		AppLogger.debug("Storing location");
		Notifier.toastMessage(this, "New location : " + distance
				+ " avarage speed : " + speed);
		getContentResolver().insert(Model.Location.CONTENT_URI, cv);
	}

}

package com.la.runners.service.track;

import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;

import android.location.Location;

public class AvarageTrackManager implements TrackManager {

	private static final int DATA_SET_SIZE = 25;
	
	private StoreManager storeManager;
	
	private Location[] locations;
	
	private int index;
	
	private int dataSetSize;
	
	private Run run;
	
	private double distance;
	
	private double lastLatitude = 0D;
	private double lastLongitude = 0D;
	
	private long startTime;
	
	public AvarageTrackManager(StoreManager storeManager) {
		this(storeManager, DATA_SET_SIZE);
	}
	
	public AvarageTrackManager(StoreManager storeManager, int dataSetSize) {
		this.storeManager = storeManager;
		this.dataSetSize = dataSetSize;
	}
	
	@Override
	public void start() {
		reset();
	}

	@Override
	public Run stop() {
		run.time = System.currentTimeMillis() - run.startTime;
		return run;
	}

	@Override
	public void updateLocation(Location location) {
		AppLogger.debug("updating location : " + location);
		locations[index] = location;
		if(index == dataSetSize-1) {
			setPoint(locations);
			locations = new Location[dataSetSize];
			index = 0;
		} else {
			index++;
		}
	}
	
	private void reset() {
		index = 0;
		distance = 0;
		locations = new Location[dataSetSize];
		startTime = System.currentTimeMillis();
	}
	
	private void setPoint(final Location[] locations) {
		double latitude = 0D, longitude = 0D, altitude = 0D; 
		float accuracy = 0F;
		double speed = 0F; 
		long time = 0L;
		
		for(Location l : locations){
			latitude += l.getLatitude();
			longitude += l.getLongitude();
			altitude += l.getAltitude();
			accuracy += l.getAccuracy();
			time += l.getTime();
		}
		
		if(lastLongitude == 0D && lastLatitude == 0D) {
			distance = 0D;
			speed = 0F;
		} else {
			distance = distance(lastLatitude, lastLongitude, latitude/dataSetSize, longitude/dataSetSize);
			speed = distance/(System.currentTimeMillis() - startTime);
		}
		lastLatitude = latitude/dataSetSize;
		lastLongitude = longitude/dataSetSize;
		
		storeManager.store(latitude/dataSetSize, longitude/dataSetSize,
				altitude/dataSetSize, accuracy/dataSetSize, time/dataSetSize, speed/dataSetSize, distance);
	}
	
	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515 * 1.609344;
		return (dist);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}

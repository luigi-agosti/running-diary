
package com.la.runners.service.track;

import android.location.Location;

import com.la.runners.util.AppLogger;

public class AvarageTrackManager implements TrackManager {

    private static final double OEF_CONVERTION_TO_CENTIMETERS = 60 * 1.1515 * 160934.4;
    
    private static final double RAD2DEG_COEF = 180.0 / Math.PI;
    
    private static final double DEG2RAD_COEF = Math.PI / 180.0;
    
    private static final int MAX_LONGITUDE_DEGREE = 180;
    
    private static final int MAX_LATITUDE_DEGREE = 90;
    
    private static final int DATA_SET_SIZE = 25;

    private StoreManager storeManager;

    private Location[] locations;

    private int index;

    private int dataSetSize;

    private Run run;

    private double distance;
    
    private double totalDistance;
    
    private double speed;

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
        if(!isValidLocation(location)) {
            return;
        }
        AppLogger.debug("updating location : " + location);
        locations[index] = location;
        if (index == dataSetSize - 1) {
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

        for (Location l : locations) {
            latitude += l.getLatitude();
            longitude += l.getLongitude();
            altitude += l.getAltitude();
        }
        double newLatitude = latitude / dataSetSize;
        double newLongitude = longitude / dataSetSize;
        long time = System.currentTimeMillis() - startTime;

        if (lastLongitude == 0D && lastLatitude == 0D) {
            distance = 0D;
            totalDistance = 0D;
        } else {
            distance = distance(lastLatitude, lastLongitude, newLatitude, newLongitude);
            totalDistance += distance;
            speed = totalDistance / time; 
        }
        lastLatitude = newLatitude;
        lastLongitude = newLongitude;

        storeManager.store(newLatitude, newLongitude, altitude / dataSetSize,
                time, System.currentTimeMillis(), speed, distance, totalDistance);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * OEF_CONVERTION_TO_CENTIMETERS;
        return dist;
    }
    
    private double deg2rad(double deg) {
        return (deg * DEG2RAD_COEF);
    }

    private double rad2deg(double rad) {
        return (rad * RAD2DEG_COEF);
    }

    private boolean isValidLocation(Location l) {
        return l != null && Math.abs(l.getLatitude()) <= MAX_LATITUDE_DEGREE
                && Math.abs(l.getLongitude()) <= MAX_LONGITUDE_DEGREE;
    }
}

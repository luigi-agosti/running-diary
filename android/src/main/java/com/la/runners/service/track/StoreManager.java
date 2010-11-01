package com.la.runners.service.track;

public interface StoreManager {
    
    void start();

	void trackPoint(double latitude, double longitude, double altitude,
			long time, long timestamp, double speed, double distance, double totalDistance);

	void stop(long duration, double speed, double totalDistance);
	
}

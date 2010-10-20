package com.la.runners.service.track;

public interface StoreManager {

	void store(double latitude, double longitude, double altitude,
			long time, long timestamp, double speed, double distance, double totalDistance);

}

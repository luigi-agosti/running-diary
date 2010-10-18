package com.la.runners.service.track;

public interface StoreManager {

	void store(double latitude, double longitude, double altitude,
			long time, double speed, double distance);

}

package com.la.runners.service.track;

import android.location.Location;


public interface TrackManager {
	
	void start();
	
	void updateLocation(Location location);
	
	void stop();
	
}
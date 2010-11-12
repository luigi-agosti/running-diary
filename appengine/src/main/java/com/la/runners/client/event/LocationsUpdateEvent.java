package com.la.runners.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.la.runners.shared.Location;

public class LocationsUpdateEvent extends GwtEvent<LocationsUpdateHandler> {

    public static final GwtEvent.Type<LocationsUpdateHandler> TYPE = new GwtEvent.Type<LocationsUpdateHandler>();

    private List<Location> locations;
    private double distance;
    
    public LocationsUpdateEvent(List<Location> locations, double distance) {
        this.locations = locations;
        this.setDistance(distance);
    }

    @Override
    protected void dispatch(LocationsUpdateHandler handler) {
        handler.update(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<LocationsUpdateHandler> getAssociatedType() {
        return TYPE;
    }

    public List<Location> getLocations() {
        return locations;
    }

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}
    
}

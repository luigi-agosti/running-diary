package com.la.runners.client.map;

import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.la.runners.client.res.Resources;
import com.la.runners.shared.Location;

public class LocationMarker extends Marker {

    private static final MarkerOptions options = MarkerOptions.newInstance();
    static {
        Icon icon = Icon.newInstance(Resources.INSTANCE.locationIcon().getURL());
        icon.setShadowURL(Resources.INSTANCE.locationIconShadow().getURL());
        icon.setIconSize(Size.newInstance(12, 20));
        icon.setShadowSize(Size.newInstance(22, 20));
        icon.setIconAnchor(Point.newInstance(6, 20));
        icon.setInfoWindowAnchor(Point.newInstance(5, 1));
        options.setIcon(icon);
        options.setDraggable(Boolean.FALSE);
    }
    
    private Location location;
    
    public LocationMarker(LatLng point) {
        this(point, null);
    }
    
    public LocationMarker(Location location) {
        this(location, null);
    }
    
    public LocationMarker(Location l, MarkerDragEndHandler markerDragEndHandler) {
        this(LatLng.newInstance(l.getLatitudeAsDouble(), l.getLongitudeAsDouble()), markerDragEndHandler);
        this.location = l;
    }
    
    public LocationMarker(LatLng point, MarkerDragEndHandler markerDragEndHandler) {
        super(point, options);
        if(markerDragEndHandler != null) {
            addMarkerDragEndHandler(markerDragEndHandler);
            options.setDraggable(Boolean.TRUE);
        }
    }

	public Location getLocation(LocationMarker previousMarker) {
		if(location == null) {
			location = new Location();
		}
		LatLng latlng = getLatLng();
		location.setLatitudeAsDouble(latlng.getLatitude());
		location.setLongitudeAsDouble(latlng.getLongitude());
		location.setDistance(distance);
		location.setAltitude(altitude);
		location.setSpeed(speed);
		location.setTime(time);
		return location;
	}

	private Long altitude;
	private Long distance;
	private Long speed;
	private Long time;
	
	public void setAltitude(Long altitude) {
		this.altitude = altitude;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}

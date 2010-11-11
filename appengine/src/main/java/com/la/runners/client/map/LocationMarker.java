package com.la.runners.client.map;

import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;

public class LocationMarker extends Marker {

    private static final MarkerOptions options = MarkerOptions.newInstance();
    static {
        Icon icon = Icon.newInstance("http://labs.google.com/ridefinder/images/mm_20_red.png");
        icon.setShadowURL("http://labs.google.com/ridefinder/images/mm_20_shadow.png");
        icon.setIconSize(Size.newInstance(12, 20));
        icon.setShadowSize(Size.newInstance(22, 20));
        icon.setIconAnchor(Point.newInstance(6, 20));
        icon.setInfoWindowAnchor(Point.newInstance(5, 1));
        options.setIcon(icon);
        options.setDraggable(Boolean.TRUE);
    }
    
    public LocationMarker(LatLng point) {
        this(point, null);
    }
    
    public LocationMarker(LatLng point, MarkerDragEndHandler markerDragEndHandler) {
        super(point, options);
        if(markerDragEndHandler != null) {
            addMarkerDragEndHandler(markerDragEndHandler);
        }
    }

}

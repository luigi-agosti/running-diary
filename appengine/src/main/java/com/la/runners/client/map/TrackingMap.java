
package com.la.runners.client.map;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.maps.client.MapUIOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.ControlAnchor;
import com.google.gwt.maps.client.control.ControlPosition;
import com.google.gwt.maps.client.control.Control.CustomControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.PolylineLineUpdatedHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.la.runners.client.Context;
import com.la.runners.client.event.LocationsUpdateEvent;
import com.la.runners.client.res.Resources;
import com.la.runners.shared.Location;

public class TrackingMap extends Composite {

    private static final PolyStyleOptions style = PolyStyleOptions.newInstance("#FF0000", 3, 1.0);

    private static final String WIDTH = "640px";

    private static final String HEIGHT = "480px";

    private MapWidget map;

    private FlowPanel panel;

    private boolean editMode;
    
    private Label distance; 
    
    private Context context;
    
    private Polyline poly = new Polyline(new LatLng[0]);

    public TrackingMap(Context context, Long id) {
        this(context, id, Boolean.FALSE);
    }

    public TrackingMap(Context context, Long id, boolean editMode) {
        this.context = context;
        panel = new FlowPanel();
        this.editMode = editMode;
        panel.setStyleName(Resources.INSTANCE.map().container());
        initMap();
        panel.clear();
        panel.add(map);
        initWidget(panel);
        context.getService().getLocations(id, new AsyncCallback<List<Location>>() {
            @Override
            public void onSuccess(List<Location> result) {
                load(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                // TODO some nice way to show message
            }
        });
    }
    
    public boolean isEditMode() {
    	return editMode;
    }

    public List<Location> getLocations() {
    	List<Location> locations = new ArrayList<Location>();
    	LocationMarker previousMarker = null;
    	int index = 0;
    	for(LocationMarker marker : markers) {
    		Location l = marker.getLocation(previousMarker);
    		l.setTimestamp(new Date(index));
    		locations.add(l);
    		index++;
    	}
    	return locations;
    }

    private void initMap() {
        map = new MapWidget();
        map.setSize(WIDTH, HEIGHT);
        map.clearOverlays();
        MapUIOptions opts = map.getDefaultUI();
        if (editMode) {
            // Workaround for bug with click handler & setUItoDefaults() - see
            // issue 260
            opts.setDoubleClick(false);
            map.addMapClickHandler(new MapClickHandler() {
                public void onClick(MapClickEvent e) {
                    MapWidget sender = e.getSender();
                    Overlay overlay = e.getOverlay();
                    LatLng point = e.getLatLng();
                    if (overlay != null && overlay instanceof LocationMarker) {
                        sender.removeOverlay(overlay);
                        markers.remove((LocationMarker)overlay);
                        updatePoliLine();
                    } else {
                    	LocationMarker marker = createMarker(point);
                        sender.addOverlay(marker);
                        updatePoliLine();
                    }
                }
            });
        }
        map.addControl(new CustomControl(new ControlPosition(ControlAnchor.BOTTOM_RIGHT, 15, 15)) {
            @Override
            public boolean isSelectable() {
                return Boolean.FALSE;
            }

            @Override
            protected Widget initialize(final MapWidget map) {
                Panel container = new FlowPanel();
                container.setStyleName(Resources.INSTANCE.map().distanceControllerContainer());
                distance = new Label();
                distance.setStyleName(Resources.INSTANCE.map().distanceController());
                container.add(distance);
                return container;
            }
        });
        
        map.setUI(opts);
    }

    private List<LocationMarker> markers = new ArrayList<LocationMarker>();

    private void load(List<Location> locations) {
        map.clearOverlays();
        LatLng point = null;
        for (Location l : locations) {
            point = LatLng.newInstance(l.getLatitudeAsDouble(), l.getLongitudeAsDouble());
            map.addOverlay(createMarker(point, l));
        }
        if (point != null) {
            map.setCenter(point, 13);
        }
        updatePoliLine();
    }
    
    private LocationMarker createMarker(LatLng point) {
    	return createMarker(point, null);
    }
    
    private LocationMarker createMarker(LatLng point, Location l) {
        LocationMarker lm = null;
        if(editMode) {
            lm = new LocationMarker(point, new MarkerDragEndHandler() {
                public void onDragEnd(MarkerDragEndEvent event) {
                    updatePoliLine();
                }
            });
            if(l != null) {
            	lm.setAltitude(l.getAltitude());
            	lm.setDistance(l.getDistance());
            	lm.setSpeed(l.getSpeed());
            	lm.setTime(l.getTime());
            }
        } else {
            lm = new LocationMarker(point);
        }
        markers.add(lm);
        return lm;
    }

    private void updatePoliLine() {
    	try {
    		if(poly != null) {
    			poly.removePolylineLineUpdatedHandler(polyLineUpdateHandler);
    		}
    		map.removeOverlay(poly);
    	} catch(Exception e) {
    		//TODO
    	}
        poly = new Polyline(new LatLng[markers.size()]);
        map.addOverlay(poly);
        poly.setStrokeStyle(style);

        int index = 0;
        for (Marker marker : markers) {
            poly.insertVertex(index, marker.getLatLng());
            index++;
        }
        poly.addPolylineLineUpdatedHandler(polyLineUpdateHandler);
    }
    
    private PolylineLineUpdatedHandler polyLineUpdateHandler = new PolylineLineUpdatedHandler() {
		@Override
		public void onUpdate(PolylineLineUpdatedEvent event) {				
			String unit = context.getUnitConverter().getDistanceUnit(context.strings);
			distance.setText(poly.getLength() + unit);
		}
    };

	public void save() {
		context.getEventBus().fireEvent(new LocationsUpdateEvent(getLocations(), poly.getLength()));
	}

}

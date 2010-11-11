
package com.la.runners.client.map;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.maps.client.MapUIOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.Control.CustomControl;
import com.google.gwt.maps.client.control.ControlAnchor;
import com.google.gwt.maps.client.control.ControlPosition;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
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
import com.la.runners.client.res.ResourceBundle;
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
        if (id == null) {
            this.editMode = Boolean.TRUE;
        } else {
            this.editMode = editMode;
        }
        panel.setStyleName(ResourceBundle.INSTANCE.map().container());
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

    public List<Location> getLocations() {
        // TODO
        return new ArrayList<Location>();
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
                    if (overlay != null && overlay instanceof Marker) {
                        sender.removeOverlay(overlay);
                        markers.remove((Marker)overlay);
                        updatePoliLine();
                    } else {
                        Marker marker = createMarker(point);
                        sender.addOverlay(marker);
                        markers.add(marker);
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
                container.setStyleName(ResourceBundle.INSTANCE.map().distanceControllerContainer());
                distance = new Label();
                distance.setStyleName(ResourceBundle.INSTANCE.map().distanceController());
                container.add(distance);
                return container;
            }
        });
        
        map.setUI(opts);
    }

    private List<Marker> markers = new ArrayList<Marker>();

    private void load(List<Location> locations) {
        map.clearOverlays();
        LatLng point = null;
        for (Location l : locations) {
            point = LatLng.newInstance(l.getLatitudeAsDouble(), l.getLongitudeAsDouble());
            map.addOverlay(createMarker(point));
        }
        if (point != null) {
            map.setCenter(point, 13);
        }
    }
    
    private Marker createMarker(LatLng point) {
        LocationMarker lm = null;
        if(editMode) {
            lm = new LocationMarker(point, new MarkerDragEndHandler() {
                public void onDragEnd(MarkerDragEndEvent event) {
                    updatePoliLine();
                }
            });
        } else {
            lm = new LocationMarker(point);
        }
        return lm;
    }

    private void updatePoliLine() {
        map.removeOverlay(poly);
        poly = new Polyline(new LatLng[markers.size()]);
        map.addOverlay(poly);
        poly.setStrokeStyle(style);

        int index = 0;
        for (Marker marker : markers) {
            poly.insertVertex(index, marker.getLatLng());
            index++;
        }
        String unit = context.getUnitConverter().getDistanceUnit(context.strings);
        distance.setText(poly.getLength() + unit);
    }

}

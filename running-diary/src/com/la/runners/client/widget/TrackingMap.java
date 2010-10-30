
package com.la.runners.client.widget;

import java.util.List;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.la.runners.client.Context;
import com.la.runners.client.res.Styles;
import com.la.runners.shared.Location;

public class TrackingMap extends Composite {

    private MapWidget map;

    private FlowPanel panel;

    private Icon icon;

    public TrackingMap(Context context) {
        panel = new FlowPanel();
        panel.setStyleName(Styles.Map.mapContainer);
        initMap();
        panel.clear();
        panel.add(map);
        initWidget(panel);
        context.getService().getLocations("", new AsyncCallback<List<Location>>() {
            @Override
            public void onSuccess(List<Location> result) {
                load(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                // TODO show a message
            }
        });
    }

    private void initMap() {
        map = new MapWidget(LatLng.newInstance(51.500152, -0.126236), 13); // from
                                                                           // user
                                                                           // profile?
        map.setSize("500px", "300px");
        map.addControl(new SmallMapControl());
        map.addControl(new MapTypeControl());
        map.clearOverlays();
        // map.addMapDoubleClickHandler(new MapDoubleClickHandler() {
        // @Override
        // public void onDoubleClick(MapDoubleClickEvent event) {
        //
        // }
        // });
    }

    private void load(List<Location> locations) {
        map.clearOverlays();
        MarkerOptions options = MarkerOptions.newInstance();
        options.setIcon(getIcon());
        for (Location l : locations) {
            LatLng point = LatLng.newInstance(l.getLatitudeAsDouble(), l.getLongitudeAsDouble());
            map.addOverlay(new Marker(point, options));
        }
    }

    private Icon getIcon() {
        if (icon == null) {
            icon = Icon.newInstance("http://labs.google.com/ridefinder/images/mm_20_red.png");
            icon.setShadowURL("http://labs.google.com/ridefinder/images/mm_20_shadow.png");
            icon.setIconSize(Size.newInstance(12, 20));
            icon.setShadowSize(Size.newInstance(22, 20));
            icon.setIconAnchor(Point.newInstance(6, 20));
            icon.setInfoWindowAnchor(Point.newInstance(5, 1));
        }
        return icon;
    }

}

package com.la.runners.client.widget.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.la.runners.client.Context;
import com.la.runners.client.event.ShowMapEvent;
import com.la.runners.client.event.ShowMapHandler;
import com.la.runners.client.map.TrackingMap;

public class MapDialog extends CenteredDialog implements ShowMapHandler{

    private static final String DOMAIN = "social-runners";
    private FlowPanel mapContainer;
    private Context context;
    private TrackingMap currentMap;
    
    private static boolean firstLoad = Boolean.TRUE;
    
    public MapDialog(final Context context) {
        this.context = context;
        mapContainer = new FlowPanel();
        add(mapContainer);
        add(new Button(context.strings.dialogCloseButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        }));
        context.getEventBus().addHandler(ShowMapEvent.TYPE, this);
    }
    
    @Override
    public void showMap(final ShowMapEvent event) {
        if (firstLoad) {
            String host = GWT.getHostPageBaseURL();
            String mapKey = context.strings.googleMapsKeyOnAppSpot();
            if(host.contains(DOMAIN)) {
                mapKey = context.strings.googleMapsKeyOnSocialRunnersDomain();
            }
            Maps.loadMapsApi(mapKey, context.strings.googleMapsVersion(),
                    false, new Runnable() {
                        public void run() {
                            load(event);
                        }
                    });
        } else {
            load(event);
        }
    }
    
    private void load(ShowMapEvent event) {
        mapContainer.clear();
        currentMap = new TrackingMap(context, event.getId());
        mapContainer.add(currentMap);
        center();
    }

    
}

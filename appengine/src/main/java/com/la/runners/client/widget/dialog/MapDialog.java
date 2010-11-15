package com.la.runners.client.widget.dialog;

import java.util.List;

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
import com.la.runners.shared.Location;

public class MapDialog extends CenteredDialog implements ShowMapHandler{

    private static final String DOMAIN = "social-runners";
    private FlowPanel mapContainer;
    private Context context;
    private TrackingMap currentMap;
    private Button saveBtn;
    
    private static boolean firstLoad = Boolean.TRUE;
    
    public MapDialog(final Context context) {
        this.context = context;
        mapContainer = new FlowPanel();
        add(mapContainer);
        addToolbarButton(new Button(context.strings.dialogCloseButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        }));
        saveBtn = addToolbarButton(new Button(context.strings.dialogSaveButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                if(currentMap != null) {
                	currentMap.save();
                	hide();
                }
            }
        }));
        saveBtn.setVisible(Boolean.FALSE);
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
        List<Location> locations = event.getLocations();
        if(locations == null) {
            currentMap = new TrackingMap(context, event.getId(), event.getEditMode());
        } else {
            currentMap = new TrackingMap(context, locations, event.getEditMode());            
        }
        if(currentMap.isEditMode()) {
        	saveBtn.setVisible(Boolean.TRUE);
        } else {
        	saveBtn.setVisible(Boolean.FALSE);
        }
        mapContainer.add(currentMap);
        center();
    }

    
}

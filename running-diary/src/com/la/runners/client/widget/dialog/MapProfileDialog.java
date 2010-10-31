package com.la.runners.client.widget.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.la.runners.client.Context;
import com.la.runners.client.event.ShowMapEvent;
import com.la.runners.client.event.ShowMapHandler;
import com.la.runners.client.widget.TrackingMap;

public class MapProfileDialog extends CenteredDialog implements ShowMapHandler{

    private FlowPanel mapContainer;
    private Context context;
    
    private boolean firstLoad = Boolean.TRUE;
    
    public MapProfileDialog(final Context context) {
        this.context = context;
        mapContainer = new FlowPanel();
        add(new Button(context.strings.dialogCloseButton(), new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        }));
        add(mapContainer);
        context.getEventBus().addHandler(ShowMapEvent.TYPE, this);
    }
    
    @Override
    public void showMap(final ShowMapEvent event) {
        if (firstLoad) {
            Maps.loadMapsApi(context.strings.googleMapsKey(), context.strings.googleMapsVersion(),
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
        show();
        mapContainer.clear();
        mapContainer.add(new TrackingMap(context));
    }

    
}

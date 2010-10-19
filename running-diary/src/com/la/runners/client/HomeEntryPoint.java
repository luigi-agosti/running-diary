
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.event.ShowMapEvent;
import com.la.runners.client.event.ShowMapHandler;
import com.la.runners.client.widget.TrackingMap;
import com.la.runners.client.widget.form.RunEditor;
import com.la.runners.client.widget.grid.RunGrid;

public class HomeEntryPoint implements EntryPoint, ShowMapHandler {

    private Context context;

    private boolean firstLoad = true;

    private DialogBox dialogBox;
    
    private FlowPanel mapContainer;

    @Override
    public void onModuleLoad() {
        context = new Context();
        context.getEventBus().addHandler(ShowMapEvent.TYPE, this);
        RunEditor runEditor = new RunEditor(context);
        RunGrid runGrid = new RunGrid(context);
        FlowPanel panel = new FlowPanel();
        panel.add(runGrid);
        panel.add(runEditor);

        dialogBox = new DialogBox();
        
        mapContainer = new FlowPanel();
        mapContainer.add(new Button("Close", new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        }));
        dialogBox.add(mapContainer);

        panel.setStyleName(Styles.Form.entryPoint);
        RootPanel.get(context.strings.gwtHook()).add(panel);
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
        dialogBox.center();
        dialogBox.show();
        mapContainer.add(new TrackingMap(context));
        
    }

}

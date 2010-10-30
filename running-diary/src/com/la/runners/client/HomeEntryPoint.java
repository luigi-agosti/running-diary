
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.event.ShowMapEvent;
import com.la.runners.client.event.ShowMapHandler;
import com.la.runners.client.res.Styles;
import com.la.runners.client.widget.TrackingMap;
import com.la.runners.client.widget.form.RunForm;
import com.la.runners.client.widget.grid.RunGrid;
import com.la.runners.shared.Profile;

public class HomeEntryPoint implements EntryPoint, ShowMapHandler {

    private static final String GWT_HOOK = "gwtHook";
    
    private Context context;

    private boolean firstLoad = Boolean.TRUE;

    private DialogBox dialogBox;
    
    private FlowPanel mapContainer;
    
    private FlowPanel panel;

    @Override
    public void onModuleLoad() {
        panel = new FlowPanel();
        panel.setStyleName(Styles.Form.entryPoint);
        RootPanel.get(GWT_HOOK).add(panel);
        context = new Context();
        context.getService().getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                //TODO Ummm
            }
            @Override
            public void onSuccess(Profile result) {
                context.setProfile(result);
                init();
            }
        });
    }
    
    private void init() {
        context.getEventBus().addHandler(ShowMapEvent.TYPE, this);
        RunForm runEditor = new RunForm(context);
        RunGrid runGrid = new RunGrid(context);
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
        context.getEventBus().fireEvent(new RunListUpdateEvent());
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

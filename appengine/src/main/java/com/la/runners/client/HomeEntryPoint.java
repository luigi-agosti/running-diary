
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.res.Resources;
import com.la.runners.client.widget.dialog.MapDialog;
import com.la.runners.client.widget.dialog.NewProfileDialog;
import com.la.runners.client.widget.form.RunForm;
import com.la.runners.client.widget.grid.RunGrid;
import com.la.runners.shared.Profile;

public class HomeEntryPoint implements EntryPoint {

    private static final String GWT_HOOK = "gwtHook";
    
    private Context context;
    
    private FlowPanel panel;

    @Override
    public void onModuleLoad() {
        Resources.INSTANCE.form().ensureInjected();
        Resources.INSTANCE.map().ensureInjected();
        Resources.INSTANCE.grid().ensureInjected();
        panel = new FlowPanel();
        panel.setStyleName(Resources.INSTANCE.form().entryPoint());
        RootPanel.get(GWT_HOOK).add(panel);
        context = new Context();
        context.getService().getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                //TODO
            }
            @Override
            public void onSuccess(Profile result) {
                if(result == null) {
                    NewProfileDialog dialog = new NewProfileDialog(context) {
                        @Override
                        public void finish(Profile profile) {
                            init(profile);
                        }
                    };
                    dialog.center();
                } else {
                    init(result);
                }
            }
        });
        new MapDialog(context);
    }
    
    private void init(Profile profile) {
        context.setProfile(profile);
        RunForm runEditor = new RunForm(context);
        RunGrid runGrid = new RunGrid(context);
        panel.add(runGrid);
        panel.add(runEditor);
        context.getEventBus().fireEvent(new RunListUpdateEvent());
    }

}

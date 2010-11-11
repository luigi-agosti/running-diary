
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.res.ResourceBundle;

public class StatisticsEntryPoint implements EntryPoint {

    private static final String GWT_HOOK = "gwtHook";
    
    @Override
    public void onModuleLoad() {
        ResourceBundle.INSTANCE.form().ensureInjected();
        ResourceBundle.INSTANCE.map().ensureInjected();
        ResourceBundle.INSTANCE.grid().ensureInjected();
        RootPanel.get(GWT_HOOK).add(new Label("Statistics"));
    }

}

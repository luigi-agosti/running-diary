
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class StatisticsEntryPoint implements EntryPoint {

    private static final String GWT_HOOK = "gwtHook";
    
    @Override
    public void onModuleLoad() {
        RootPanel.get(GWT_HOOK).add(new Label("Statistics"));
    }

}

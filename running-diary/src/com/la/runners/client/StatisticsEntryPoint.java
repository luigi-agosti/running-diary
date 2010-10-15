
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class StatisticsEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        Context context = new Context();
        RootPanel.get(context.strings.gwtHook()).add(new Label("Statistics"));
    }

}

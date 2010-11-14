
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.res.Resources;
import com.la.runners.client.widget.form.SearchForm;
import com.la.runners.client.widget.grid.SearchGrid;

public class SearchEntryPoint implements EntryPoint {

    private static final String GWT_HOOK = "gwtHook";
    
    @Override
    public void onModuleLoad() {
        Resources.INSTANCE.form().ensureInjected();
        Resources.INSTANCE.map().ensureInjected();
        Resources.INSTANCE.grid().ensureInjected();
        FlowPanel panel = new FlowPanel();
        panel.setStyleName(Resources.INSTANCE.form().entryPoint());
        RootPanel.get(GWT_HOOK).add(panel);
        Context context = new Context();
        panel.add(new SearchGrid(context));
        panel.add(new SearchForm(context));
    }

}

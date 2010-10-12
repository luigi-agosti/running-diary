
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.widget.form.SearchForm;
import com.la.runners.client.widget.grid.SearchGrid;

public class SearchEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        ServiceAsync editorService = GWT.create(Service.class);
        FlowPanel panel = new FlowPanel();
        HandlerManager eventBus = new HandlerManager(null);
        panel.add(new SearchGrid(eventBus, editorService));
        panel.add(new SearchForm(eventBus, editorService));
        panel.setStyleName("EditorEntryPoint");
        RootPanel.get("gwtHook").add(panel);
    }

}

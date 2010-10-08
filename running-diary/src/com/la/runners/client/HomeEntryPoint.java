
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.widget.form.RunEditor;
import com.la.runners.client.widget.grid.RunGrid;

public class HomeEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        ServiceAsync editorService = GWT.create(Service.class);
        HandlerManager eventBus = new HandlerManager(null);
        RunEditor runEditor = new RunEditor(eventBus, editorService);
        RunGrid runGrid = new RunGrid(eventBus, editorService);
        FlowPanel panel = new FlowPanel();
        panel.add(runGrid);
        panel.add(runEditor);
        panel.setStyleName("EditorEntryPoint");
        RootPanel.get("gwtHook").add(panel);
    }

}

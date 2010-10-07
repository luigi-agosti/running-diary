
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.widget.form.RunEditor;
import com.la.runners.client.widget.grid.RunGrid;

public class HomeEntryPoint implements EntryPoint {

    private final ServiceAsync editorService = GWT.create(Service.class);

    private static final String GWT_HOOK_ID = "gwtHook";

    private RunEditor runEditor;
    
    private RunGrid runGrid;
    
    private HandlerManager eventBus;

    @Override
    public void onModuleLoad() {
        eventBus = new HandlerManager(null);
        runEditor = new RunEditor(eventBus, editorService);
        runGrid = new RunGrid(eventBus, editorService);
        FlowPanel panel = new FlowPanel();
        panel.add(runGrid);
        panel.add(runEditor);
        RootPanel.get(GWT_HOOK_ID).add(panel);
        panel.setStyleName("EditorEntryPoint");
    }

}

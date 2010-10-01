package com.la.runners.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class RunGrid extends Composite {
    
    private FlowPanel panel;
    
    private HandlerManager eventBus;
    
    private EditorServiceAsync service;
    
    public RunGrid(HandlerManager eventBus, EditorServiceAsync _service) {
        this.eventBus = eventBus;
        this.service = _service;
        panel = new FlowPanel();
        panel.add(new Label("should show the list of runs of the user here"));
        initWidget(panel);
        setStyleName(RunGrid.class.getName());
    }
    
    
   
}

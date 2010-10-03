package com.la.runners.client;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.shared.Run;

public class RunGrid extends Composite {
    
    private FlowPanel grid;
    
    private EditorServiceAsync service;
    
    private Label messageBar;
    
    public RunGrid(HandlerManager eventBus, EditorServiceAsync _service) {
        this.service = _service;
        FlowPanel panel = new FlowPanel();
        messageBar = new Label("Loading runs list of the user here");
        grid = new FlowPanel();
        panel.add(grid);
        panel.add(messageBar);
        initWidget(panel);
        setStyleName(RunGrid.class.getName());
        load();
    }
    
    private void load() {
    	service.search(new AsyncCallback<List<Run>>() {
			@Override
			public void onSuccess(List<Run> result) {
				refreshGrid(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				messageBar.setText("There was an error while requesting data to the server");
			}
		});
    }
    
    private void refreshGrid(List<Run> result) {
    	grid.clear();
    	if(result.isEmpty()) {
    		messageBar.setText("No result found");
    	} else {
	    	for(Run run : result) {
	    		grid.add(new Label(run.getId() + " " + run.getNote()));    		
	    	}
    	}
    }
   
}

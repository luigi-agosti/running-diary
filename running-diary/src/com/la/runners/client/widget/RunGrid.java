package com.la.runners.client.widget;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.EditorServiceAsync;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.event.RunListUpdateHandler;
import com.la.runners.shared.Run;

public class RunGrid extends Composite implements RunListUpdateHandler {
    
    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("dd - EEEE");
    
    private static final String GRID_HEADER_CELL_DATE = "GridHeaderCell-Date";
    private static final String GRID_HEADER_CELL_DISTANCE = "GridHeaderCell-Distance";
    private static final String GRID_HEADER_CELL_NOTE = "GridHeaderCell-Time";
    private static final String GRID_HEADER_CELL_TIME = "GridHeaderCell-Note";

    private static final String GRID_CELL_DATE = "GridCell-Date";
    private static final String GRID_CELL_DISTANCE = "GridCell-Distance";
    private static final String GRID_CELL_TIME = "GridCell-Time";
    private static final String GRID_CELL_NOTE = "GridCell-Note";
    
    private FlexTable grid;
    
    private EditorServiceAsync service;
    
    private GridBar topBar;
    
    private GridBar bottomBar;
    
    public RunGrid(HandlerManager eventBus, EditorServiceAsync _service) {
        this.service = _service;
        eventBus.addHandler(RunListUpdateEvent.TYPE, this);
        
        FlowPanel panel = new FlowPanel();
        topBar = new GridBar(eventBus);
        panel.add(topBar);

        grid = new FlexTable();
        grid.setStyleName("GridTable");
        panel.add(grid);
        
        bottomBar = new GridBar(eventBus, true);
        panel.add(bottomBar);
        
        initWidget(panel);
        setStyleName("RunGrid");
        eventBus.fireEvent(new RunListUpdateEvent());
    }
    
    private void load(Integer year, Integer month) {
        showMessage("Loading...");
    	service.search(year, month, new AsyncCallback<List<Run>>() {
			@Override
			public void onSuccess(List<Run> result) {
				drawGrid(result);
				showMessage(" ");
			}
			@Override
			public void onFailure(Throwable caught) {
			    showMessage("There was an error while requesting data to the server");
			}
		});
    }
    
    private void drawGrid(List<Run> result) {
    	grid.clear();
    	if(result.isEmpty()) {
    	    showMessage("No result found");
    	} else {
    	    grid.setWidget(0,0, createLabel("Date", GRID_HEADER_CELL_DATE));
    	    grid.setWidget(0,1, createLabel("Distance", GRID_HEADER_CELL_DISTANCE));
    	    grid.setWidget(0,2, createLabel("Time", GRID_HEADER_CELL_TIME));
    	    grid.setWidget(0,3, createLabel("Note", GRID_HEADER_CELL_NOTE));
    	    int index = 1;
	    	for(Run run : result) {
	    	    DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy/mm/dd"); 
	    	    grid.setWidget(index,0, createLabel(YEAR_FORMATTER.format(formatter.parse(run.getYear() + "/" + run.getMonth() + "/" + run.getDay())), GRID_CELL_DATE));
	            grid.setWidget(index,1, createLabel("" + run.getDistance(), GRID_CELL_DISTANCE));
	            grid.setWidget(index,2, createLabel("" + run.getTime(), GRID_CELL_TIME));
	            grid.setWidget(index,3, createLabel("" + run.getNote(), GRID_CELL_NOTE));
	            index++;
	    	}
    	}
    }
    
    private Label createLabel(String content, String style) {
        Label l = new Label(content);
        l.setStyleName(style);
        return l;
    }
    
    private void showMessage(String message) {
        topBar.showMessage(message);
        bottomBar.showMessage(message);
    }

    @Override
    public void updateRunList(RunListUpdateEvent event) {
        load(event.getYear(), event.getMonth());
        topBar.setYear(event.getYear());
        topBar.setMonth(event.getMonth());
        bottomBar.setYear(event.getYear());
        bottomBar.setMonth(event.getMonth());
    }
   
}

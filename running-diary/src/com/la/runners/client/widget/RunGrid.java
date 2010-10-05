package com.la.runners.client.widget;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.EditorServiceAsync;
import com.la.runners.client.event.LoadRunEvent;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.event.RunListUpdateHandler;
import com.la.runners.shared.Run;

public class RunGrid extends Composite implements RunListUpdateHandler {
    
    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("dd - EEEE");
    private static final DateTimeFormat FULL_FORMATTER = DateTimeFormat.getFormat("yyyy/mm/dd"); 
    
    private static final String GRID_HEADER_CELL = "GridHeaderCell";

    private static final String GRID_CELL = "GridCell";
    
    private FlexTable grid;
    
    private EditorServiceAsync service;
    
    private HandlerManager eventBus;
    
    private GridBar topBar;
    
    private GridBar bottomBar;
    
    public RunGrid(HandlerManager eventBus, EditorServiceAsync _service) {
        this.service = _service;
        this.eventBus = eventBus;
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
    	    grid.setWidget(0,0, createLabel("Date", GRID_HEADER_CELL));
    	    grid.setWidget(0,1, createLabel("Distance", GRID_HEADER_CELL));
    	    grid.setWidget(0,2, createLabel("Time", GRID_HEADER_CELL));
    	    grid.setWidget(0,3, createLabel("Heart Rate", GRID_HEADER_CELL));
    	    grid.setWidget(0,4, createLabel("Weight", GRID_HEADER_CELL));
    	    grid.setWidget(0,5, createLabel("Shoes", GRID_HEADER_CELL));
    	    grid.setWidget(0,6, createLabel("Note", GRID_HEADER_CELL));
    	    grid.setWidget(0,7, createLabel("Edit", GRID_HEADER_CELL));
    	    int index = 1;
	    	for(Run run : result) {
	    	    grid.setWidget(index,0, createLabel(YEAR_FORMATTER.format(
	    	         FULL_FORMATTER.parse(run.getYear() + "/" + run.getMonth() + "/" + run.getDay())), GRID_CELL));
	            grid.setWidget(index,1, createLabel("" + run.getDistance(), GRID_CELL));
	            grid.setWidget(index,2, createLabel("" + run.getTime(), GRID_CELL));
	            grid.setWidget(index,3, createLabel("" + run.getHeartRate(), GRID_CELL));
	            grid.setWidget(index,4, createLabel("" + run.getWeight(), GRID_CELL));
	            grid.setWidget(index,5, createLabel("" + run.getShoes(), GRID_CELL));
	            grid.setWidget(index,6, createLabel("" + run.getNote(), GRID_CELL));
	            final Long id = run.getId();
	            Button btn = new Button("Edit");
	            btn.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        eventBus.fireEvent(new LoadRunEvent(id));
                    }
	            });
	            grid.setWidget(index,7, btn);
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

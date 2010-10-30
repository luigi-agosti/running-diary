package com.la.runners.client.widget.grid;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.la.runners.client.Context;
import com.la.runners.client.event.LoadRunEvent;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.event.RunListUpdateHandler;
import com.la.runners.client.event.ShowMapEvent;
import com.la.runners.client.res.Styles;
import com.la.runners.client.widget.grid.toolbar.MessageBar;
import com.la.runners.client.widget.grid.toolbar.RunGridBar;
import com.la.runners.shared.Run;

public class RunGrid extends BaseGrid implements RunListUpdateHandler {
    
    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("dd - EEEE");
    private static final DateTimeFormat FULL_FORMATTER = DateTimeFormat.getFormat("yyyy/mm/dd"); 
    
    public RunGrid(Context context) {
        super(context);
        eventBus().addHandler(RunListUpdateEvent.TYPE, this);
    }
    
    private void load() {
        showMessage(strings().runGridLoading());
    	service().search(Integer.valueOf(((RunGridBar)topBar).getYear()), 
    	        Integer.valueOf(((RunGridBar)topBar).getMonth()), new AsyncCallback<List<Run>>() {
			@Override
			public void onSuccess(List<Run> result) {
				drawGrid(result);
				showMessage(strings().runGridDone());
			}
			@Override
			public void onFailure(Throwable caught) {
			    showMessage(strings().runGridProblem());
			}
		});
    }
    
    private void drawGrid(List<Run> result) {
    	grid.clear();
    	if(result.isEmpty()) {
    	    showMessage(strings().runGridNoResult());
    	} else {
    	    newRow();
    	    addHeaderCellToRow(strings().runGridDate());
    	    addHeaderCellToRow(strings().runGridDistance());
    	    addHeaderCellToRow(strings().runGridTime());
    	    if(profile().getHeartRate()) {
    	        addHeaderCellToRow(strings().runGridHeartRate());
    	    }
    	    if(profile().getWeight()) {
    	        addHeaderCellToRow(strings().runGridWeight());
    	    }
    	    if(profile().getShoes()) {
    	        addHeaderCellToRow(strings().runGridShoes());
    	    }
    	    addHeaderCellToRow(strings().runGridNote());
    	    addHeaderCellToRow(strings().runGridEdit());
    	    addHeaderCellToRow(strings().runGridMap());
    	    int index = 1;
	    	for(Run run : result) {
	    	    newRow();
	    	    addLabelCellToRow(index, YEAR_FORMATTER.format(
	                     FULL_FORMATTER.parse(run.getYear() + "/" + run.getMonth() + "/" + run.getDay())));
	    	    addLabelCellToRow(index, run.getDistance());
	    	    addLabelCellToRow(index, run.getTime());
	            if(profile().getHeartRate()) {
	                addLabelCellToRow(index, run.getHeartRate());
	            }
	            if(profile().getWeight()) {
	                addLabelCellToRow(index, run.getWeight());
	            }
	            if(profile().getShoes()) {
	                addLabelCellToRow(index, run.getShoes());
	            }
	            addLabelCellToRow(index, run.getNote());
	            
	            final Long id = run.getId();
	            Button btn = new Button(strings().runGridEdit());
	            btn.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        eventBus().fireEvent(new LoadRunEvent(id));
                    }
	            });
	            addCellToRow(index, btn);
	            
	            Button btnMap = new Button(strings().runGridMap());
	            btnMap.addClickHandler(new ClickHandler() {
	                @Override
	                public void onClick(ClickEvent event) {
	                    eventBus().fireEvent(new ShowMapEvent(id));
	                }
	            });
	            addCellToRow(index, btnMap);
	            index++;
	    	}
    	}
    }
    
    private int cell = 0;
    
    private void addHeaderCellToRow(String label) {
        grid.setWidget(0, cell, createLabel(label, Styles.Grid.gridHeaderCell));
        cell++;
    }
    
    private void addLabelCellToRow(int row, Object obj) {
        addCellToRow(row, createLabel(obj, Styles.Grid.gridCell));
    }
    
    private void addCellToRow(int row, Widget widget) {
        grid.setWidget(row, cell, widget);
        cell++;
    }
    
    private void newRow() {
        cell = 0;
    }
    
    
    @Override
    public void updateRunList(RunListUpdateEvent event) {
        ((RunGridBar)topBar).setYear(event.getYear());
        ((RunGridBar)topBar).setMonth(event.getMonth());
        ((RunGridBar)bottomBar).setYear(event.getYear());
        ((RunGridBar)bottomBar).setMonth(event.getMonth());
        load();
    }
    
    @Override
    protected MessageBar getTopBar() {
        return new RunGridBar(eventBus());
    }
    
    @Override
    protected MessageBar getBottomBar() {
        return new RunGridBar(eventBus(), Boolean.TRUE);
    }
   
}

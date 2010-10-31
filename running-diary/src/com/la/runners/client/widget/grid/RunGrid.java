package com.la.runners.client.widget.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.la.runners.client.Context;
import com.la.runners.client.event.DeleteRunEvent;
import com.la.runners.client.event.DeleteRunHandler;
import com.la.runners.client.event.LoadRunEvent;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.event.RunListUpdateHandler;
import com.la.runners.client.event.ShowMapEvent;
import com.la.runners.client.res.Styles;
import com.la.runners.client.widget.grid.toolbar.MessageBar;
import com.la.runners.client.widget.grid.toolbar.RunGridBar;
import com.la.runners.shared.Run;

public class RunGrid extends BaseGrid implements RunListUpdateHandler, DeleteRunHandler {
    
    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("dd - EEEE");
    private static final DateTimeFormat FULL_FORMATTER = DateTimeFormat.getFormat("yyyy/mm/dd"); 
    private static final DateTimeFormat TIME_FORMATTER = DateTimeFormat.getFormat("HH:mm");
    private static final TimeZone TIME_ZONE = TimeZone.createTimeZone(0);
    
    private List<ExtraCheckBox> selection = new ArrayList<ExtraCheckBox>();
    
    public RunGrid(Context context) {
        super(context);
        eventBus().addHandler(RunListUpdateEvent.TYPE, this);
        eventBus().addHandler(DeleteRunEvent.TYPE, this);
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
    	selection.clear();
    	if(result.isEmpty()) {
    	    showMessage(strings().runGridNoResult());
    	} else {
    	    newRow();
    	    addHeaderCellToRow(strings().runGridSelect());
    	    addHeaderCellToRow(strings().runGridDate());
    	    addHeaderCellToRow(strings().runGridDistance()+ unitConverter().getDistanceUnit(strings()));
    	    addHeaderCellToRow(strings().runGridTime());
    	    addHeaderCellToRow(strings().runGridSpeed() + unitConverter().getSpeedUnit(strings()));
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
	    	    ExtraCheckBox checkBox = new ExtraCheckBox(run.getId());
	    	    selection.add(checkBox);
	    	    addCellToRow(index, checkBox);
	    	    addLabelCellToRow(index, YEAR_FORMATTER.format(
	                     FULL_FORMATTER.parse(run.getYear() + "/" + run.getMonth() + "/" + run.getDay())));
	    	    addLabelCellToRow(index, unitConverter().customUnitDistance(run.getDistance()));
	    	    addLabelCellToRow(index, TIME_FORMATTER.format(new Date(run.getTime()), TIME_ZONE));
	    	    addLabelCellToRow(index, unitConverter().customUnitSpeed(run.getSpeed()).toString());
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

    @Override
    public void deleteRuns(DeleteRunEvent event) {
        List<Long> ids = new ArrayList<Long>();
        for(ExtraCheckBox cb : selection) {
            if(cb.getValue()) {
                ids.add(cb.getId());
            }
        }
        if(selection.isEmpty()) {
            showMessage(strings().runGridSelectionMissing());
            return;
        }
        service().deleteRuns(ids, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                showMessage(strings().runGridProblem());
            }
            @Override
            public void onSuccess(Void result) {
                eventBus().fireEvent(new RunListUpdateEvent());
            }
        });
    }
   
}

package com.la.runners.client.widget.grid;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.la.runners.client.Constants;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.event.LoadRunEvent;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.event.RunListUpdateHandler;
import com.la.runners.client.widget.grid.toolbar.RunGridBar;
import com.la.runners.client.widget.grid.toolbar.MessageBar;
import com.la.runners.shared.Run;

public class RunGrid extends BaseGrid implements RunListUpdateHandler {
    
    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("dd - EEEE");
    private static final DateTimeFormat FULL_FORMATTER = DateTimeFormat.getFormat("yyyy/mm/dd"); 
    
    private HandlerManager eventBus;
    
    public RunGrid(HandlerManager eventBus, ServiceAsync service) {
        super(service);
        this.eventBus = eventBus;
        eventBus.addHandler(RunListUpdateEvent.TYPE, this);
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
    	    grid.setWidget(0,0, createLabel("Date", Constants.Style.gridHeaderCell));
    	    grid.setWidget(0,1, createLabel("Distance", Constants.Style.gridHeaderCell));
    	    grid.setWidget(0,2, createLabel("Time", Constants.Style.gridHeaderCell));
    	    grid.setWidget(0,3, createLabel("Heart Rate", Constants.Style.gridHeaderCell));
    	    grid.setWidget(0,4, createLabel("Weight", Constants.Style.gridHeaderCell));
    	    grid.setWidget(0,5, createLabel("Shoes", Constants.Style.gridHeaderCell));
    	    grid.setWidget(0,6, createLabel("Note", Constants.Style.gridHeaderCell));
    	    grid.setWidget(0,7, createLabel("Edit", Constants.Style.gridHeaderCell));
    	    int index = 1;
	    	for(Run run : result) {
	    	    grid.setWidget(index,0, createLabel(YEAR_FORMATTER.format(
	    	         FULL_FORMATTER.parse(run.getYear() + "/" + run.getMonth() + "/" + run.getDay())), Constants.Style.gridCell));
	            grid.setWidget(index,1, createLabel("" + run.getDistance(), Constants.Style.gridCell));
	            grid.setWidget(index,2, createLabel("" + run.getTime(), Constants.Style.gridCell));
	            grid.setWidget(index,3, createLabel("" + run.getHeartRate(), Constants.Style.gridCell));
	            grid.setWidget(index,4, createLabel("" + run.getWeight(), Constants.Style.gridCell));
	            grid.setWidget(index,5, createLabel("" + run.getShoes(), Constants.Style.gridCell));
	            grid.setWidget(index,6, createLabel("" + run.getNote(), Constants.Style.gridCell));
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
    
    @Override
    public void updateRunList(RunListUpdateEvent event) {
        load(event.getYear(), event.getMonth());
        ((RunGridBar)topBar).setYear(event.getYear());
        ((RunGridBar)topBar).setMonth(event.getMonth());
        ((RunGridBar)bottomBar).setYear(event.getYear());
        ((RunGridBar)bottomBar).setMonth(event.getMonth());
    }
    
    @Override
    protected MessageBar getTopBar() {
        return new RunGridBar(eventBus);
    }
    
    @Override
    protected MessageBar getBottomBar() {
        return new RunGridBar(eventBus, true);
    }
   
}

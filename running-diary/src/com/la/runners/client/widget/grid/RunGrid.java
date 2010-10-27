package com.la.runners.client.widget.grid;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
        showMessage("Loading...");
    	service().search(Integer.valueOf(((RunGridBar)topBar).getYear()), 
    	        Integer.valueOf(((RunGridBar)topBar).getMonth()), new AsyncCallback<List<Run>>() {
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
    	    grid.setWidget(0,0, createLabel("Date", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,1, createLabel("Distance", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,2, createLabel("Time", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,3, createLabel("Heart Rate", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,4, createLabel("Weight", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,5, createLabel("Shoes", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,6, createLabel("Note", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,7, createLabel("Edit", Styles.Grid.gridHeaderCell));
    	    grid.setWidget(0,8, createLabel("Map", Styles.Grid.gridHeaderCell));
    	    int index = 1;
	    	for(Run run : result) {
	    	    grid.setWidget(index,0, createLabel(YEAR_FORMATTER.format(
	    	         FULL_FORMATTER.parse(run.getYear() + "/" + run.getMonth() + "/" + run.getDay())), Styles.Grid.gridCell));
	            grid.setWidget(index,1, createLabel("" + run.getDistance(), Styles.Grid.gridCell));
	            grid.setWidget(index,2, createLabel("" + run.getTime(), Styles.Grid.gridCell));
	            grid.setWidget(index,3, createLabel("" + run.getHeartRate(), Styles.Grid.gridCell));
	            grid.setWidget(index,4, createLabel("" + run.getWeight(), Styles.Grid.gridCell));
	            grid.setWidget(index,5, createLabel("" + run.getShoes(), Styles.Grid.gridCell));
	            grid.setWidget(index,6, createLabel("" + run.getNote(), Styles.Grid.gridCell));
	            final Long id = run.getId();
	            Button btn = new Button("Edit");
	            btn.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        eventBus().fireEvent(new LoadRunEvent(id));
                    }
	            });
	            grid.setWidget(index,7, btn);
	            Button btnMap = new Button("Map");
	            btnMap.addClickHandler(new ClickHandler() {
	                @Override
	                public void onClick(ClickEvent event) {
	                    eventBus().fireEvent(new ShowMapEvent(id));
	                }
	            });
	            grid.setWidget(index,8, btnMap);
	            index++;
	    	}
    	}
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

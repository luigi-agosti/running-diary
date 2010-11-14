package com.la.runners.client.widget.chart;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.OnMouseOutHandler;
import com.google.gwt.visualization.client.events.OnMouseOverHandler;
import com.google.gwt.visualization.client.events.ReadyHandler;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.ColumnChart.Options;
import com.la.runners.client.Context;
import com.la.runners.client.widget.custom.MonthListBox;
import com.la.runners.client.widget.custom.YearListBox;
import com.la.runners.shared.Run;

public class DailyDistanceColumnChart extends ChartContainer {

	private static final Options options = ColumnChart.Options.create(); 
	
	private YearListBox yearListBox;
	
	private MonthListBox monthListBox;
	
	public DailyDistanceColumnChart(Context context) {
		super(context);
		
		options.setHeight(400);
		options.setTitle(getStrings().chartDailyDistanceTitle());
		options.setWidth(800);
		
		yearListBox = new YearListBox();
        monthListBox = new MonthListBox();
        monthListBox.setCurrentMonth();
        addControl(yearListBox);
        monthListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                refreshData();
            }
        });
        addControl(monthListBox);
        yearListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                refreshData();
            }
        });
		refreshData();
	}
	
	@Override
	protected Widget getChart(DataTable data) {
		ColumnChart chart = new ColumnChart(data, options);

		chart.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
			}
	    });
	    		
		chart.addReadyHandler(new ReadyHandler(){
			@Override
			public void onReady(ReadyEvent event) {
				// TODO Auto-generated method stub
			}
	    });
		chart.addOnMouseOverHandler(new OnMouseOverHandler() {
			@Override
			public void onMouseOverEvent(OnMouseOverEvent event) {
				// TODO Auto-generated method stub
			}
	    });
		chart.addOnMouseOutHandler(new OnMouseOutHandler() {
			@Override
			public void onMouseOutEvent(OnMouseOutEvent event) {
				// TODO Auto-generated method stub				
			}
	    });
		return chart;
	}

	@Override
	protected void refreshData() {
		getService().search(yearListBox.getYear(), monthListBox.getMonth(), new AsyncCallback<List<Run>>() {
			@Override
			public void onSuccess(List<Run> result) {
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.NUMBER, getStrings().chartDistance() + getUnitConverter().getDistanceUnit(getStrings()));
				data.addRows(31);
				int index = 0;
				for(int i = 1; i<=31; i++) {
					long value = 0;
					for(Run run : result) {
						if(run.getDay() == i) {
							value += run.getDistance();
						}
					}
					data.setValue(index, 0, getUnitConverter().customUnitDistance(value));
					index++;
				}
				refreshChart(data);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//TODO show the message somewhere
			}
		});		
	}
	
	

}

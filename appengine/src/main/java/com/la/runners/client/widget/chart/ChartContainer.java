package com.la.runners.client.widget.chart;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.la.runners.client.Context;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.res.Resources;
import com.la.runners.client.res.Strings;
import com.la.runners.client.widget.form.field.converter.UnitConverter;

public abstract class ChartContainer extends Composite {
	
	private Context context;
	
	private FlowPanel toolbar;
	
	private FlowPanel content;
	
	public ChartContainer(Context context) {
		this.context = context;
		FlowPanel panel = new FlowPanel();
		panel.setStyleName(Resources.INSTANCE.chart().container());
		toolbar = new FlowPanel();
		toolbar.setStyleName(Resources.INSTANCE.chart().toolbar());
		content = new FlowPanel();
		content.setStyleName(Resources.INSTANCE.chart().content());
		panel.add(toolbar);
		panel.add(content);
		initWidget(panel);
	}
	
	protected void addControl(Widget w){
		toolbar.add(w);
	}
	
	protected void refreshChart(DataTable data) {
	    content.clear();
		content.add(getChart(data));
	}	
	
	protected abstract Widget getChart(DataTable data);
	
	protected void setData(DataTable data) {
		refreshChart(data);
	}
	
	protected abstract void refreshData();
	
	protected ServiceAsync getService() {
		return context.getService();
	}

	protected UnitConverter getUnitConverter() {
		return context.getUnitConverter();
	}
	
	protected Strings getStrings() {
		return context.strings;
	}

}

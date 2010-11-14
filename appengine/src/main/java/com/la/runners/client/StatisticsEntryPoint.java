package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.la.runners.client.res.Resources;
import com.la.runners.client.widget.chart.DailyDistanceColumnChart;
import com.la.runners.client.widget.dialog.MapDialog;
import com.la.runners.client.widget.dialog.NewProfileDialog;
import com.la.runners.shared.Profile;

public class StatisticsEntryPoint implements EntryPoint {

	private static final String GWT_HOOK = "gwtHook";

	private Context context;
	
	@Override
	public void onModuleLoad() {
		Resources.INSTANCE.chart().ensureInjected();
		context = new Context();
		context.getService().getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                //TODO
            }
            @Override
            public void onSuccess(Profile result) {
                if(result == null) {
                    NewProfileDialog dialog = new NewProfileDialog(context) {
                        @Override
                        public void finish(Profile profile) {
                            init();
                        }
                    };
                    dialog.center();
                } else {
                    context.setProfile(result);
                    init();
                }
            }
        });
        new MapDialog(context);
	}
	
	public void init() {
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			public void run() {
				RootPanel.get(GWT_HOOK).add(new DailyDistanceColumnChart(context));
			}
		}, ColumnChart.PACKAGE);
	}

}


package com.la.runners.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.la.runners.R;
import com.la.runners.util.AppLogger;

public class WidgetProvider extends AppWidgetProvider {
    
    private static final String ACTION_STOP = "com.la.runners.ACTION_STOP";
    private static final String ACTION_START = "com.la.runners.ACTION_START";
    private static final String ACTION_MAP = "com.la.runners.ACTION_MAP";
    private static final String ACTION_EDIT = "com.la.runners.ACTION_EDIT";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        AppLogger.debug("onReceive from the widget : " + action);
        if(ACTION_STOP.equals(action)) {
            AppLogger.debug("stop");
            
        } else if(ACTION_START.equals(action)) {
            AppLogger.debug("start");
            
        } else if(ACTION_MAP.equals(action)) {
            AppLogger.debug("map");
            
        } else if(ACTION_EDIT.equals(action)) {
            AppLogger.debug("edit");
            
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int nbWidget = appWidgetIds.length;
        for (int i = 0; i < nbWidget; i++) {
            int appWidgetId = appWidgetIds[i];
            updateView(context, 0, appWidgetId);
        }
    }

    private void updateView(Context context, int index, int widgetId) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.widget_edit, pi(context, ACTION_EDIT));
        views.setOnClickPendingIntent(R.id.widget_start, pi(context, ACTION_START));
        views.setOnClickPendingIntent(R.id.widget_stop, pi(context, ACTION_STOP));
        views.setOnClickPendingIntent(R.id.widget_map, pi(context, ACTION_MAP));
        manager.updateAppWidget(widgetId, views);
    }
    
    private static final PendingIntent pi(Context context, String action) {
        Intent i = new Intent(context, WidgetProvider.class);
        i.setAction(action);
        return PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    
}

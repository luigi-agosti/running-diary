package com.la.runners.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.la.runners.R;
import com.la.runners.activity.map.LocationItemizedOverlay;
import com.la.runners.provider.Model;

public class MapTrackingActivity extends MapActivity {

	private static final String EXTRA_ID = "id";
	
    private static Drawable pathIcon;
    private static Drawable locationIcon;
    
    public static final Intent prepareIntent(Context context) {
        return new Intent(context, MapTrackingActivity.class);
    }
    
    public static final Intent loadMapIntent(Context context, long id) {
        Intent i = prepareIntent(context);
        i.putExtra(EXTRA_ID, id);
        return i;
    }
    
    private MapView mapView;
    private List<Overlay> mapOverlays;
    private LocationItemizedOverlay pathPoints;
    private LocationItemizedOverlay location;
    private MapController mapController;
    
    private Drawable getPathIcon() {
        if(pathIcon == null) {
            pathIcon = getResources().getDrawable(R.drawable.androidmarker_small);
        }
        return pathIcon;
    }

    private Drawable getLocationIcon() {
        if(locationIcon == null) {
            locationIcon = getResources().getDrawable(R.drawable.androidmarker);
        }
        return locationIcon;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_tracking);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapController = mapView.getController();
        mapController.setZoom(17);
        mapOverlays = mapView.getOverlays(); 
        pathPoints = new LocationItemizedOverlay(getPathIcon());
        location = new LocationItemizedOverlay(getLocationIcon());
        String runId = getRunId();
        updateMapWithRun(runId);
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    private String getRunId() {
        Intent intent = getIntent();
        String id = null;
        if(intent != null && intent.hasExtra(EXTRA_ID)) {
        	id = "" + intent.getLongExtra(EXTRA_ID, 0l);
        	//If the run has a remote Id I need to use that for the relation
        	String remoteId = Model.Run.queryForRemoteId(this, id);
        	if(remoteId != null) {
        	    id = remoteId;
        	}
        } else {
            id = Model.Run.queryForCurrentRunId(this);
        }
        return id;
    }
    
    private void updateMapWithRun(String runId) {
        mapOverlays.clear();
        pathPoints.clear();
        location.clear();
    	Cursor c = null;
        try {
            c = Model.Location.get(this, runId);
            if(c.moveToFirst()) {
                mapController.setCenter(location.add(Model.Location.latitude(c), Model.Location.longitude(c)));
            }
            while (c.moveToNext()) {
                pathPoints.add(Model.Location.latitude(c), Model.Location.longitude(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        pathPoints.populateItem();
        location.populateItem();
        mapOverlays.add(location);
        mapOverlays.add(pathPoints);
    }
    
    @Override
    protected void onResume() {
        registerObserver();
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        unregisterObserver();
    }
    
    private ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            getRunId();
        }
    };
    
    private void registerObserver() {
        getContentResolver().registerContentObserver(Model.Location.CONTENT_URI, Boolean.TRUE,
                contentObserver);
    }

    private void unregisterObserver() {
        getContentResolver().unregisterContentObserver(contentObserver);
    }

}

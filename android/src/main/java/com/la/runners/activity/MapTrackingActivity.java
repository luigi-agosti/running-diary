package com.la.runners.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.la.runners.R;
import com.la.runners.activity.map.LocationItemizedOverlay;
import com.la.runners.provider.Model;

public class MapTrackingActivity extends MapActivity {

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, MapTrackingActivity.class);
    }
    
    private MapView mapView;
    private List<Overlay> mapOverlays;
    private Drawable drawable;
    private LocationItemizedOverlay itemizedOverlay;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_tracking);
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedOverlay = new LocationItemizedOverlay(drawable);
        updateRunInformation();
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
    
    private void updateRunInformation() {
        Cursor c = null;
        try {
            c = Model.Location.getLast(this);
            if (c.moveToFirst()) {
                itemizedOverlay.add(Model.Location.latitude(c), Model.Location.longitude(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        itemizedOverlay.populateItem();
        mapOverlays.add(itemizedOverlay);
    }  

}

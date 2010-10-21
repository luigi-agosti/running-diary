package com.la.runners.activity.map;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class LocationItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    
    public LocationItemizedOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
    }

    @Override
    protected OverlayItem createItem(int paramInt) {
        return mOverlays.get(paramInt);
    }

    @Override
    public int size() {
        return mOverlays.size();
    }

    public void add(long latitude, long longitude) {
        GeoPoint point = new GeoPoint((int)latitude,(int)longitude);
        OverlayItem overlayitem = new OverlayItem(point, "", "");
        mOverlays.add(overlayitem);
    }
    
    public void populateItem() {
        populate();
    }
    
}


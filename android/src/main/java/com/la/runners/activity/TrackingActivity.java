
package com.la.runners.activity;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.service.RunTrackingService;

public class TrackingActivity extends BaseActivity {

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, TrackingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking);

        setText(R.id.tracking_distance, R.string.tracking_distance,
                getString(R.string.tracking_computing));
        setText(R.id.tracking_speed, R.string.tracking_speed,
                getString(R.string.tracking_computing));
        setText(R.id.tracking_latitude, R.string.tracking_latitude,
                getString(R.string.tracking_computing));
        setText(R.id.tracking_longitude, R.string.tracking_longitude,
                getString(R.string.tracking_computing));
        setText(R.id.tracking_time, R.string.tracking_time, getString(R.string.tracking_computing));

        ((Button)findViewById(R.id.tracking_stopButton))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View paramView) {
                        stopService(new Intent(getApplicationContext(), RunTrackingService.class));
                        finish();
                    }
                });
        
        ((Button)findViewById(R.id.tracking_mapButton))
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                startActivity(MapTrackingActivity.prepareIntent(getApplicationContext()));
            }
        });

        findViewById(R.id.tracking_distance);
    }

    private void updateRunInformation() {
        Cursor c = null;
        try {
            c = Model.Location.getLast(this);
            if (c.moveToFirst()) {
                setText(R.id.tracking_distance, R.string.tracking_distance,
                        Model.Location.formattedTotalDistance(c));
                setText(R.id.tracking_speed, R.string.tracking_speed,
                        Model.Location.formattedSpeed(c));
                setText(R.id.tracking_latitude, R.string.tracking_latitude,
                        Model.Location.formattedLatitude(c));
                setText(R.id.tracking_longitude, R.string.tracking_longitude,
                        Model.Location.formattedLongitude(c));
                setText(R.id.tracking_time, R.string.tracking_time, Model.Location.fomatterTime(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            updateRunInformation();
        }
    };

    @Override
    protected void onResume() {
        getContentResolver().registerContentObserver(Model.Location.CONTENT_URI, Boolean.TRUE,
                contentObserver);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(contentObserver);
    }
}

package com.la.runners.activity;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.service.RunTrackingService;
import com.la.runners.service.SyncService;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DashboardActivity extends BaseActivity {
    
    @Override
    protected void onResume() {
        if(findViewById(R.id.tracking_trackingPanel).getVisibility() == View.VISIBLE) {
            registerObserver();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterObserver();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        if (Preferences.getFirstRun(getApplicationContext())) {
            new AlertDialog.Builder(this).setTitle(getString(R.string.setupDialog_title))
                    .setMessage(getString(R.string.setupDialog_message))
                    .setPositiveButton(R.string.setupDialog_okButton, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(Preferences.getPreferenceIntent(getApplicationContext()));
                        }
                    }).setNegativeButton(R.string.setupDialog_cancelButton, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            finish();
                        }
                    }).show();
        }
        ((ImageButton)findViewById(R.id.dashboardEditBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                startActivity(RunEditorActivity.prepareIntent(getApplicationContext()));
            }
        });
        ((ImageButton)findViewById(R.id.dashboardStatisticsBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                startActivity(RunStatisticsActivity.prepareIntent(getApplicationContext()));
            }
        });
        ((ImageButton)findViewById(R.id.dashboardRunsBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                startActivity(RunListActivity.prepareIntent(getApplicationContext()));
            }
        });
        ((ImageButton)findViewById(R.id.dashboardGeoStartBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                startTracking();
                startService(new Intent(DashboardActivity.this.getApplicationContext(), RunTrackingService.class));
            }
        });
        ((ImageButton)findViewById(R.id.dashboardDownloadBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                SyncService.startDataSync(getApplicationContext());
            }
        });
    }
    
    private void startTracking() {
        findViewById(R.id.tracking_firstRow).setVisibility(View.GONE);
        findViewById(R.id.tracking_lastRow).setVisibility(View.GONE);
        
        findViewById(R.id.tracking_trackingPanel).setVisibility(View.VISIBLE);
        setText(R.id.tracking_distance, R.string.tracking_distance,
                getString(R.string.tracking_computing));
        setText(R.id.tracking_speed, R.string.tracking_speed,
                getString(R.string.tracking_computing));
        setText(R.id.tracking_time, R.string.tracking_time, getString(R.string.tracking_computing));

        ((Button)findViewById(R.id.tracking_stopButton))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View paramView) {
                        stopService(new Intent(getApplicationContext(), RunTrackingService.class));
                        stopTracking();
                    }
                });
        ((Button)findViewById(R.id.tracking_mapButton))
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                startActivity(MapTrackingActivity.prepareIntent(getApplicationContext()));
            }
        });
        registerObserver();
    }
    
    private void stopTracking() {
        findViewById(R.id.tracking_firstRow).setVisibility(View.VISIBLE);
        findViewById(R.id.tracking_lastRow).setVisibility(View.VISIBLE);
        
        findViewById(R.id.tracking_trackingPanel).setVisibility(View.GONE);
        unregisterObserver();
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
    
    private void registerObserver() {
        getContentResolver().registerContentObserver(Model.Location.CONTENT_URI, Boolean.TRUE,
                contentObserver);
    }

    private void unregisterObserver() {
        getContentResolver().unregisterContentObserver(contentObserver);
    }

}

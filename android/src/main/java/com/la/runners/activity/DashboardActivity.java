package com.la.runners.activity;

import com.la.runners.R;
import com.la.runners.service.RunTrackingService;
import com.la.runners.service.SyncService;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DashboardActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        if (Preferences.getFirstRun(getApplicationContext())) {
            new AlertDialog.Builder(this).setTitle("Setup")
                    .setMessage("Before using this up please select a google account to use")
                    .setPositiveButton("ok", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startPreferences();
                        }
                    }).setNegativeButton("cancel", new OnClickListener() {
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
                findViewById(R.id.dashboardGeoStartContainer).setVisibility(View.GONE);
                findViewById(R.id.dashboardGeoStopContainer).setVisibility(View.VISIBLE);
                startService(new Intent(DashboardActivity.this.getApplicationContext(), RunTrackingService.class));
            }
        });
        ((ImageButton)findViewById(R.id.dashboardGeoStopBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                findViewById(R.id.dashboardGeoStartContainer).setVisibility(View.VISIBLE);
                findViewById(R.id.dashboardGeoStopContainer).setVisibility(View.GONE);
                stopService(new Intent(DashboardActivity.this.getApplicationContext(), RunTrackingService.class));
            }
        });
        ((ImageButton)findViewById(R.id.dashboardDownloadBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                SyncService.startDataSync(getApplicationContext());
            }
        });
    }
    
    private void startPreferences() {
        startActivity(Preferences.getPreferenceIntent(getApplicationContext()));
    }

}

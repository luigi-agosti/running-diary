package com.la.runners.activity;

import com.la.runners.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
                    .setPositiveButton("Confirm", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startPreferences();
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
        ((ImageButton)findViewById(R.id.dashboardGeoRunsBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                startActivity(GeoRunActivity.prepareIntent(getApplicationContext()));
            }
        });
    }
    
    private void startPreferences() {
        startActivity(Preferences.getPreferenceIntent(getApplicationContext()));
    }

}

package com.la.runners.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.la.runners.R;

public class GeoRunActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_run);
    }

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, GeoRunActivity.class);
    }

}

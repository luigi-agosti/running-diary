package com.la.runners.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.la.runners.R;

public class RunStatisticsActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_statistics);
    }

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, RunStatisticsActivity.class);
    }

}

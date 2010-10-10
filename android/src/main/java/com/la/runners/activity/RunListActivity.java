
package com.la.runners.activity;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.provider.Query;
import com.la.runners.util.AppLogger;

/**
 * @author luigi.agosti
 */
public class RunListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_list_activity);
        load();
    }
    
    private void load() {
        Cursor cursor = managedQuery(Query.Run.all(getContentResolver()));
        ListView runsList = (ListView)this.findViewById(R.id.runList);
        runsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AppLogger.logVisibly("click");
            }
        });        
        runsList.setAdapter(new RunListCursorAdapter(this, cursor));
    }

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, RunListActivity.class);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        unregisterObserver();
    };
    
    @Override
    protected void onResume() {
        super.onResume();
        registerObserver();
    }
    
    private ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            load();
        }
    };

    private void registerObserver() {
        getContentResolver().registerContentObserver(Model.Run.CONTENT_URI, true,
                contentObserver);
    }

    private void unregisterObserver() {
        getContentResolver().unregisterContentObserver(contentObserver);
    }
}
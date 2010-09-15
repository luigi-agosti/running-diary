package com.la.runners.activity;

import com.la.runners.service.SyncService;

import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {
	
    private static final int FETCH_ALL_ITEM_ID = 1;
    
    private static final int PREFERENCES_ID = 2;
    
    protected Cursor managedQuery(Cursor cursor) {
        if (cursor != null) {
            startManagingCursor(cursor);
        }
        return cursor;
    }   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, FETCH_ALL_ITEM_ID, Menu.FIRST + 1,
                "Sync").setIcon(
                android.R.drawable.ic_menu_set_as);
        menu.add(Menu.NONE, PREFERENCES_ID, Menu.FIRST + 2,
            "Settings").setIcon(
                android.R.drawable.ic_menu_preferences);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case FETCH_ALL_ITEM_ID: {
                SyncService.startIntent(getApplicationContext());
                break;
            }
            case PREFERENCES_ID: {
                startActivity(Preferences.getPreferenceIntent(this));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    
}

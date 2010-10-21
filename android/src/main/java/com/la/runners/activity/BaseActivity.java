package com.la.runners.activity;

import com.google.android.maps.MapActivity;
import com.la.runners.R;
import com.la.runners.provider.Model;

import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BaseActivity extends Activity {
    
    private static final int PREFERENCES_ID = 1;
    
    protected Cursor managedQuery(Cursor cursor) {
        if (cursor != null) {
            startManagingCursor(cursor);
        }
        return cursor;
    }   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, PREFERENCES_ID, Menu.FIRST + 1, "Settings").setIcon(
                android.R.drawable.ic_menu_preferences);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PREFERENCES_ID: {
                startActivity(Preferences.getPreferenceIntent(this));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    
    protected void setText(int resId, int stringId, Object...objects) {
        ((TextView)findViewById(resId)).setText(String.format(getString(stringId), objects));
    }
    
}

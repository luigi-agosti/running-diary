package com.la.runners.activity;

import android.app.Activity;
import android.database.Cursor;

public class BaseActivity extends Activity {
	
    protected Cursor managedQuery(Cursor cursor) {
        if (cursor != null) {
            startManagingCursor(cursor);
        }
        return cursor;
    }   

}

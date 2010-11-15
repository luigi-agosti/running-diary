package com.la.runners.activity;

import com.la.runners.R;
import com.la.runners.Runners;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends Activity {

	private static final int PREFERENCES_ID = 1;
	private static final int EVENT_ID = 2;

	protected Cursor managedQuery(Cursor cursor) {
		if (cursor != null) {
			startManagingCursor(cursor);
		}
		return cursor;
	}

	@Override
	protected void onResume() {
		Runners runners = (Runners) getApplicationContext();
		Boolean isRunning = runners.isRunning();
		Log.v("is running : ", isRunning.toString());
		if (isRunning) {
			showRunWarning();
		} else {
			hideRunWarning();
		}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, PREFERENCES_ID, Menu.FIRST + 1, "Settings")
				.setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(Menu.NONE, EVENT_ID, Menu.FIRST + 2, "Event")
            .setIcon(android.R.drawable.ic_menu_preferences);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
    		case PREFERENCES_ID: {
    			startActivity(Preferences.getPreferenceIntent(this));
    			break;
    		}
    		case EVENT_ID: {
    		    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=Hemsworth+Pontefract"));
    		    startActivity(i);
    		    break;
    		}
		}
		return super.onOptionsItemSelected(item);
	}

	protected void setText(int resId, int stringId, Object... objects) {
		((TextView) findViewById(resId)).setText(String.format(
				getString(stringId), objects));
	}

	public void showRunWarning() {
		findViewById(R.id.include_running_warning_message).setVisibility(
				View.VISIBLE);
	}

	public void hideRunWarning() {
		findViewById(R.id.include_running_warning_message).setVisibility(
				View.GONE);
	}

}

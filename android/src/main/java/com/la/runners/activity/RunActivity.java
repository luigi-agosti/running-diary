package com.la.runners.activity;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.la.runners.R;
import com.la.runners.Runners;
import com.la.runners.provider.Model;
import com.la.runners.service.RunTrackingService;

public class RunActivity extends BaseActivity {

	@Override
	protected void onResume() {
		registerObserver();
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
		setContentView(R.layout.run);

		setText(R.id.tracking_distance, R.string.tracking_distance,
				getString(R.string.tracking_computing));
		setText(R.id.tracking_speed, R.string.tracking_speed,
				getString(R.string.tracking_computing));
		setText(R.id.tracking_time, R.string.tracking_time,
				getString(R.string.tracking_computing));

		((Button) findViewById(R.id.tracking_stopButton))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View paramView) {
						stopService(new Intent(getApplicationContext(),
								RunTrackingService.class));
						stopTracking();
					}
				});
		((Button) findViewById(R.id.tracking_mapButton))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View paramView) {
						startActivity(MapTrackingActivity
								.prepareIntent(getApplicationContext()));
					}
				});
		startTracking();
	}

	public static final Intent prepareIntent(Context context) {
		return new Intent(context, RunActivity.class);
	}

	void startTracking() {
		registerObserver();
		Runners runners = (Runners) getApplicationContext();
		runners.setRunning(true);
	}

	void stopTracking() {
		unregisterObserver();
		Runners runners = (Runners) getApplicationContext();
		runners.setRunning(false);
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
				setText(R.id.tracking_time, R.string.tracking_time,
						Model.Location.fomatterTime(c));
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
		getContentResolver().registerContentObserver(
				Model.Location.CONTENT_URI, Boolean.TRUE, contentObserver);
	}

	private void unregisterObserver() {
		getContentResolver().unregisterContentObserver(contentObserver);
	}

	@Override
	public void showRunWarning() {
		
	}
	
	@Override
	public void hideRunWarning(){}

}

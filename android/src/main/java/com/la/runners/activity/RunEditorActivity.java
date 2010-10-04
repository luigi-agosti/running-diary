package com.la.runners.activity;

import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.util.AppLogger;

public class RunEditorActivity extends BaseActivity implements
		OnSeekBarChangeListener {

	public static final int MILLISECONDS_IN_A_HOUR = 3600000;
	public static final int MILLISECONDS_IN_A_MINUTE = 60000;
	public static int interval = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.run_editor);

		initDistanceSeekBar();

		initTimePicker();

		if (Preferences.getHeartRate(this)) {
			enablePreferencesFields(R.id.heart_rate, R.id.heart_rate_text);
		}

		if (Preferences.getShoes(this)) {
			enablePreferencesFields(R.id.shoes, R.id.shoes_text);
		}

		if (Preferences.getWeight(this)) {
			enablePreferencesFields(R.id.weight, R.id.weight_text);
		}
	}

	public void submitFormMethod(View submitButton) {
		String errorMessage = null;
		TextView errorMessageTextView = ((TextView)findViewById(R.id.errorMessage));
		
		submitButton.setEnabled(false);
		try {
			ContentValues cv = new ContentValues();

			int year = ((DatePicker) findViewById(R.id.date)).getYear();
			int month = ((DatePicker) findViewById(R.id.date)).getMonth();
			int day = ((DatePicker) findViewById(R.id.date)).getDayOfMonth();
			GregorianCalendar calendar = new GregorianCalendar(year, month, day);
			
			long dateInMillisecons = calendar.getTimeInMillis();

			cv.put(Model.Run.DATE, dateInMillisecons);
			
			Float distance = null;
			if(isFieldNotEmpty(R.id.distance_edit_view)){
				distance = Float.parseFloat(((TextView) findViewById(R.id.distance_edit_view))
							.getText().toString());
			}
			
			if(distance <= 0){
				errorMessage = "Distance has to be greater than 0";
			}
			cv.put(Model.Run.DISTANCE,
					((TextView) findViewById(R.id.distance_edit_view))
							.getText().toString());

			Integer hour = ((TimePicker) findViewById(R.id.time))
					.getCurrentHour();
			Integer minute = ((TimePicker) findViewById(R.id.time))
					.getCurrentMinute();

			// TODO check if the calculation is correct :-)
			long timeInMilliseconds = minute * MILLISECONDS_IN_A_MINUTE + hour
					* MILLISECONDS_IN_A_HOUR;

			if(timeInMilliseconds <= 0 && errorMessage != null){
				errorMessage = "Time has to be greater than 0";
			}
			cv.put(Model.Run.TIME, timeInMilliseconds);

			cv.put(Model.Run.NOTE, ((TextView) findViewById(R.id.note))
					.getText().toString());

			Float heartRate = null;
			if (isFieldNotEmpty(R.id.heart_rate)) {
				heartRate = Float
						.parseFloat(((TextView) findViewById(R.id.heart_rate))
								.getText().toString());
			}
			cv.put(Model.Run.HEART_RATE, heartRate);

			Float weight = null;
			if (isFieldNotEmpty(R.id.weight)) {
				weight = Float
						.parseFloat(((TextView) findViewById(R.id.weight))
								.getText().toString());
			}
			cv.put(Model.Run.WEIGHT, weight);

			cv.put(Model.Run.SHOES, ((TextView) findViewById(R.id.shoes))
					.getText().toString());

			// TODO manage the share checkbox

			if(errorMessage == null){
				errorMessageTextView.setVisibility(View.GONE);	
				getContentResolver().insert(Model.Run.CONTENT_URI, cv);
				new AlertDialog.Builder(this).setTitle("Done").setMessage("New run succesfully saved").setNeutralButton("Close", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//TODO decide what to do after the run is succesfully saved -- redirect to the run list? show an update button?
					}
				}).show();
			}else{
				errorMessageTextView.setText("Error: " + errorMessage);
				errorMessageTextView.setVisibility(View.VISIBLE);		
				errorMessageTextView.setFocusableInTouchMode(true);
				errorMessageTextView.requestFocus();
				
				submitButton.setEnabled(true);
			}
		} catch (Exception e) {
			new AlertDialog.Builder(this).setTitle("Error").setMessage("an unexpected error occurred").setNeutralButton("Close", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// do nothing - it will close on its own
				}
			}).show();
			submitButton.setEnabled(true);
			AppLogger.error(e);
		}
	}

	private boolean isFieldNotEmpty(int fieldId) {
		String fieldlValue = ((TextView) findViewById(fieldId)).getText()
				.toString().trim();
		if (fieldlValue.equals("")) {
			return false;
		}
		return true;
	}

	private void enablePreferencesFields(int editTextId, int textViewId) {
		((TextView) findViewById(textViewId)).setVisibility(View.VISIBLE);
		((EditText) findViewById(editTextId)).setVisibility(View.VISIBLE);
	}

	private void initTimePicker() {
		TimePicker tp = (TimePicker) findViewById(R.id.time);
		tp.setIs24HourView(true);
		tp.setCurrentHour(0);
		tp.setCurrentMinute(0);
	}

	private void initDistanceSeekBar() {
		SeekBar sb = ((SeekBar) findViewById(R.id.distance));
		sb.setOnSeekBarChangeListener(this);

		sb.setMax(42000);
		sb.setProgress(0);
	}

	public static final Intent prepareIntent(Context context) {
		return new Intent(context, RunEditorActivity.class);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		progress = Math.round(((float) progress) / interval) * interval;

		((EditText) findViewById(R.id.distance_edit_view)).setText(Integer
				.toString(progress));

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

}

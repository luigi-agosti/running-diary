
package com.la.runners.activity;

import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.util.AppLogger;

public class RunEditorActivity extends BaseActivity implements OnSeekBarChangeListener {

    public static final int MILLISECONDS_IN_A_HOUR = 3600000;

    public static final int MILLISECONDS_IN_A_MINUTE = 60000;

    public static int INTERVAL = 100;

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
        if(getIntent().hasExtra(Model.Run.ID)) {
            //TODO think a good way to managed fields
//            Cursor c = managedQuery(Model.Run.get(getApplicationContext(), getIntent().getLongExtra(Model.Run.ID, Long.MAX_VALUE)));
//            DatePicker date = (DatePicker)findViewById(R.id.date);
//            TimePicker dayTime = (TimePicker)findViewById(R.id.dayTime);
//            SeekBar distance = (SeekBar)findViewById(R.id.distance);
//            TimePicker time = (TimePicker)findViewById(R.id.time);
            
            //TODO set the all data from the cursor
//            if(c.moveToFirst()) {
//                distance.setProgress((int)Math.abs(Model.Run.distance(c)/100));
//            }
        }
    }

    public void submitFormMethod(View submitButton) {
        String errorMessage = null;
        TextView errorMessageTextView = ((TextView)findViewById(R.id.errorMessage));

        submitButton.setEnabled(false);
        try {
            DatePicker dp = (DatePicker)findViewById(R.id.date);
            int year = dp.getYear();
            int month = dp.getMonth();
            int day = dp.getDayOfMonth();
            GregorianCalendar calendar = new GregorianCalendar(year, month, day);

            long dateInMillisecons = calendar.getTimeInMillis();

            Float distance = null;
            if (isFieldNotEmpty(R.id.distance_edit_view)) {
                distance = Float.parseFloat(((TextView)findViewById(R.id.distance_edit_view))
                        .getText().toString());
            }

            if (distance <= 0) {
                errorMessage = "Distance has to be greater than 0";
            }

            long time = getTimeFromResourceId(R.id.time);
            if (time <= 0 && errorMessage != null) {
                errorMessage = "Time has to be greater than 0";
            }
            long hour = getTimeFromResourceId(R.id.dayTime);
            if (hour <= 0 && errorMessage != null) {
                errorMessage = "Day Time has to be greater than 0";
            }
            Float heartRate = null;
            if (isFieldNotEmpty(R.id.heart_rate)) {
                heartRate = Float.parseFloat(((TextView)findViewById(R.id.heart_rate)).getText()
                        .toString());
            }
            Float weight = null;
            if (isFieldNotEmpty(R.id.weight)) {
                weight = Float.parseFloat(((TextView)findViewById(R.id.weight)).getText()
                        .toString());
            }
            
            ContentValues cv = new ContentValues();
            cv.put(Model.Run.CREATED, System.currentTimeMillis());
            cv.put(Model.Run.START_DATE, dateInMillisecons);
            cv.put(Model.Run.YEAR, year);
            cv.put(Model.Run.MONTH, month);
            cv.put(Model.Run.DAY, day);
            cv.put(Model.Run.HOUR, hour);
            cv.put(Model.Run.NOTE, ((TextView)findViewById(R.id.note)).getText().toString());
            cv.put(Model.Run.TIME, time);
            cv.put(Model.Run.END_DATE, dateInMillisecons + time);
            cv.put(Model.Run.DISTANCE, ((TextView)findViewById(R.id.distance_edit_view)).getText().toString());
            
            cv.put(Model.Run.HEART_RATE, heartRate);
            cv.put(Model.Run.WEIGHT, weight);
            cv.put(Model.Run.SHOES, ((TextView)findViewById(R.id.shoes)).getText().toString());
            cv.put(Model.Run.SHARE, ((CheckBox)findViewById(R.id.share)).isChecked());

            if (errorMessage == null) {
                errorMessageTextView.setVisibility(View.GONE);
                cv.put(Model.Run.MODIFIED, System.currentTimeMillis());
                getContentResolver().insert(Model.Run.CONTENT_URI, cv);
                new AlertDialog.Builder(this).setTitle("Done")
                        .setMessage("New run succesfully saved")
                        .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            } else {
                errorMessageTextView.setText("Error: " + errorMessage);
                errorMessageTextView.setVisibility(View.VISIBLE);
                errorMessageTextView.setFocusableInTouchMode(true);
                errorMessageTextView.requestFocus();

                submitButton.setEnabled(true);
            }
        } catch (Exception e) {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Umm, something wrong!")
                    .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
            submitButton.setEnabled(true);
            AppLogger.error(e);
        }
    }

    private long getTimeFromResourceId(int resourceId) {
        Integer hour = ((TimePicker)findViewById(resourceId)).getCurrentHour();
        Integer minute = ((TimePicker)findViewById(resourceId)).getCurrentMinute();
        // TODO check if the calculation is correct :-)
        return minute * MILLISECONDS_IN_A_MINUTE + hour
                * MILLISECONDS_IN_A_HOUR;
    }

    private boolean isFieldNotEmpty(int fieldId) {
        String fieldlValue = ((TextView)findViewById(fieldId)).getText().toString().trim();
        if (fieldlValue.equals("")) {
            return false;
        }
        return true;
    }

    private void enablePreferencesFields(int editTextId, int textViewId) {
        ((TextView)findViewById(textViewId)).setVisibility(View.VISIBLE);
        ((EditText)findViewById(editTextId)).setVisibility(View.VISIBLE);
    }

    private void initTimePicker() {
        TimePicker tp = (TimePicker)findViewById(R.id.time);
        tp.setIs24HourView(true);
        tp.setCurrentHour(0);
        tp.setCurrentMinute(0);
    }

    private void initDistanceSeekBar() {
        SeekBar sb = ((SeekBar)findViewById(R.id.distance));
        sb.setOnSeekBarChangeListener(this);

        sb.setMax(42000);
        sb.setProgress(0);
    }

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, RunEditorActivity.class);
    }
    
    public static final Intent getLoadRunEditor(Context context, long id) {
        Intent intent = new Intent(context, RunEditorActivity.class);
        intent.putExtra(Model.Run.ID, id);
        return intent;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progress = Math.round(((float)progress) / INTERVAL) * INTERVAL;
        ((EditText)findViewById(R.id.distance_edit_view)).setText(Integer.toString(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}

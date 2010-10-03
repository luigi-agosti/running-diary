
package com.la.runners.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.la.runners.R;
import com.la.runners.provider.Model;

public class RunEditorActivity extends BaseActivity implements OnSeekBarChangeListener {

	public static int interval = 100;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_editor);

        initDistanceSeekBar();
        
        initTimePicker();
        
        if(Preferences.getHeartRate(this)){
        	enablePreferencesFields(R.id.heart_rate, R.id.heart_rate_text);
        }
        
        if(Preferences.getShoes(this)){
        	enablePreferencesFields(R.id.shoes, R.id.shoes_text);
        }
        
        if(Preferences.getWeight(this)){
        	enablePreferencesFields(R.id.weight, R.id.weight_text);
        }
        
        findViewById(R.id.run_editor_submit).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(Model.Run.NOTE, ((TextView)findViewById(R.id.note)).getText().toString());
                //TODO add all the values 
                //andrea wait for me to add the new values because I need to change the database
                
                getContentResolver().insert(Model.Run.CONTENT_URI, cv);
            }
        });
        
    }
    
    private void enablePreferencesFields(int editTextId,int textViewId){
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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		progress = Math.round(((float)progress)/interval)*interval;
		
		((EditText)findViewById(R.id.distance_edit_view)).setText(Integer.toString(progress));
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
		
	}

}

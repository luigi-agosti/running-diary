package com.la.runners.activity.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.util.Utils;

public class RunListCursorAdapter extends CursorAdapter {

	public RunListCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	    Long date = Model.Run.startDate(cursor);
	    TextView noteTextView = (TextView)view.findViewById(R.id.runListActivity_date);
	    noteTextView.setText(Utils.Date.shortDate(date));

	    TextView distanceTextView = (TextView)view.findViewById(R.id.runListActivity_distance);
		distanceTextView.setText("" + Utils.Number.customUnitDistance(Model.Run.distance(cursor)));
		
		TextView timeTextView = (TextView)view.findViewById(R.id.runListActivity_time);
		timeTextView.setText(Utils.Date.time(Model.Run.time(cursor)));
		
		TextView speedTextView = (TextView)view.findViewById(R.id.runListActivity_date);
		speedTextView.setText("" + Utils.Number.customUnitSpeed(Model.Run.speed(cursor)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
	    LinearLayout ll = new LinearLayout(context);
        View.inflate(context, R.layout.run_list_activity_item, ll);
		return ll;
	}

}

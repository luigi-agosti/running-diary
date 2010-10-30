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

public class RunListCursorAdapter extends CursorAdapter {

	public RunListCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Long distance = Model.Run.distance(cursor);
		TextView distanceTextView = (TextView)view.findViewById(R.id.runListActivity_distance);
		distanceTextView.setText("" + distance);
		
		String note = Model.Run.note(cursor);
		TextView noteTextView = (TextView)view.findViewById(R.id.runListActivity_note);
		noteTextView.setText(note);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
	    LinearLayout ll = new LinearLayout(context);
        View.inflate(context, R.layout.run_list_activity_item, ll);
		return ll;
	}

}

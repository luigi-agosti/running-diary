
package com.la.runners.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.la.runners.R;
import com.la.runners.provider.Model;

public class RunEditorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_editor);
        ((Button)findViewById(R.id.run_editor_submit)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View paramView) {
                ContentValues cv = new ContentValues();
                cv.put(Model.Run.NOTE, (String)((TextView)findViewById(R.id.run_editor_submit)).getText());
                getContentResolver().insert(Model.Run.CONTENT_URI, cv);
            }
        });
    }

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, RunEditorActivity.class);
    }

}

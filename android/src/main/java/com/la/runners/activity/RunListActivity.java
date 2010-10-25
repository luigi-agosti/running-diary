
package com.la.runners.activity;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.la.runners.R;
import com.la.runners.activity.adapter.RunListCursorAdapter;
import com.la.runners.provider.Model;
import com.la.runners.util.AppLogger;

/**
 * @author luigi.agosti
 */
public class RunListActivity extends BaseActivity {

    private ListView runsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_list_activity);
        load();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit: {
                AppLogger.debug("edit");
                startActivity(RunEditorActivity.getLoadRunEditor(getApplicationContext(), info.id));
                return true;
            }
            case R.id.delete: {
                AppLogger.debug("delete");
                getApplicationContext().getContentResolver().delete(Model.Run.CONTENT_URI, Model.Run.ID + Model.PARAMETER, new String[]{"" + info.id});
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void load() {
        Cursor cursor = managedQuery(Model.Run.all(getContentResolver()));
        runsList = (ListView)this.findViewById(R.id.runList);
        runsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                startActivity(RunEditorActivity.getLoadRunEditor(getApplicationContext(), id));
            }
        });
        registerForContextMenu(runsList);
        runsList.setAdapter(new RunListCursorAdapter(this, cursor));
    }

    public static final Intent prepareIntent(Context context) {
        return new Intent(context, RunListActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterObserver();
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerObserver();
    }

    private ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            load();
        }
    };

    private void registerObserver() {
        getContentResolver().registerContentObserver(Model.Run.CONTENT_URI, true, contentObserver);
    }

    private void unregisterObserver() {
        getContentResolver().unregisterContentObserver(contentObserver);
    }
}


package com.la.runners.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.la.runners.R;
import com.la.runners.provider.Query;
import com.la.runners.service.SyncService;
import com.la.runners.util.AppLogger;

/**
 * @author luigi.agosti
 */
public class RunListActivity extends BaseActivity {

	private static final int FETCH_ALL_ITEM_ID = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.run_list_activity);

        Cursor channelsCursor = managedQuery(Query.Run.all(getContentResolver()));

        ListView runsList = (ListView)this.findViewById(R.id.runList);

        runsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				AppLogger.logVisibly("click");
			}
        
        });
        
        runsList.setAdapter(new RunListCursorAdapter(this, channelsCursor));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, FETCH_ALL_ITEM_ID, Menu.FIRST + 1,
                "Sync").setIcon(
                android.R.drawable.ic_menu_set_as);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        	case FETCH_ALL_ITEM_ID: {
        		SyncService.startIntent(getApplicationContext());
        		break;
        	}
    	}
    	return super.onOptionsItemSelected(item);
    }
    
}

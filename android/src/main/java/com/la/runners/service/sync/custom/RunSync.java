
package com.la.runners.service.sync.custom;

import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.parser.JsonParserIterator;
import com.la.runners.parser.RunParser;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Constants;

public class RunSync extends BasicSync {

    public RunSync() {
        super(Model.Run.CONTENT_URI, Constants.Server.RUN_CONTENT_URL);
        setSyncUpReourceId(R.string.syncService_run);
    }

    @Override
    protected String convert(Cursor c) {
        return Model.Run.convertAll(c);
    }

    @Override
    protected JsonParserIterator instanziateParser(InputStream urlAsStream) {
        return new RunParser(urlAsStream);
    }

    @Override
    protected void handleRelations(Context context, String id, Long rid) {
        ContentValues cv = new ContentValues();
        cv.put(Model.Location.RUN_ID, rid);
        AppLogger.debug("handleRelations id : " + id + " remote id : " + rid);
        int rows = context.getContentResolver().update(Model.Location.CONTENT_URI, cv, 
                Model.Location.RUN_ID + Model.PARAMETER, new String[]{id});
        AppLogger.debug("Rows changed : " + rows);
    }

}

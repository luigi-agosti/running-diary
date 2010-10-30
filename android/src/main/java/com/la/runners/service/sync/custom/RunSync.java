
package com.la.runners.service.sync.custom;

import java.io.InputStream;

import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.parser.JsonParserIterator;
import com.la.runners.parser.RunParser;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
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

}

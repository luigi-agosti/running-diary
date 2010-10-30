
package com.la.runners.service.sync.custom;

import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.parser.JsonParserIterator;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.util.Constants;

public class LocationSync extends BasicSync {

    public LocationSync() {
        super(Model.Location.CONTENT_URI, Constants.Server.LOCATION_CONTENT_URL);
        setSyncUpReourceId(R.string.syncService_location);
    }

    @Override
    public void syncUpUpdates(Context context, SyncNotifier syncEventListener) {
        // Do not store stuff for now
    }

    @Override
    public void syncUpDeletes(Context context, SyncNotifier syncEventListener) {
        // Do not store stuff for now
    }

    @Override
    public void syncDown(Context context, SyncNotifier syncNotifier) {
        // Do not store stuff for now
    }

    @Override
    protected String convert(Cursor c) {
        return Model.Location.convertAll(c);
    }

    @Override
    protected JsonParserIterator instanziateParser(InputStream urlAsStream) {
        // Do not store stuff for now
        return null;
    }

}

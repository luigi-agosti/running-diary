
package com.la.runners.service.sync.custom;

import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.Runners;
import com.la.runners.activity.Preferences;
import com.la.runners.parser.JsonParserIterator;
import com.la.runners.parser.ProfileParser;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.util.Constants;

public class ProfileSync extends BasicSync {

    public ProfileSync() {
        super(Model.Profile.CONTENT_URI, Constants.Server.PROFILE_CONTENT_URL);
        setSyncUpReourceId(R.string.syncService_profile);
    }

    @Override
    public void syncUp(Context c, SyncNotifier syncNotifier) {
        Runners.getHttpManager(c).post(c, Constants.Server.PROFILE_CONTENT_URL, Model.Profile.convert(c));
    }

    @Override
    protected String convert(Cursor c) {
        // do not need to anything
        return null;
    }

    @Override
    protected JsonParserIterator instanziateParser(InputStream urlAsStream) {
        // do not need to anything
        return null;
    }

    @Override
    public void clean(Context context, SyncNotifier syncNotifier) {
        // do not need to clean
    }

    @Override
    public void syncUpUpdates(Context context, SyncNotifier syncEventListener) {
        // do not need to clean
    }

    @Override
    public void syncUpDeletes(Context context, SyncNotifier syncEventListener) {
        // do not need to clean
    }

    @Override
    public void syncDown(Context c, SyncNotifier syncNotifier) {
        ProfileParser parser = new ProfileParser(Runners.getHttpManager(c).getUrlAsStream(Constants.Server.PROFILE_CONTENT_URL, c));
        while (parser.hasNext()) {
            ContentValues cv = parser.next();
            if (cv.containsKey(Model.Profile.SHOES)) {
                Preferences.setShoes(c, cv.getAsBoolean(Model.Profile.SHOES));
            }
            if (cv.containsKey(Model.Profile.WEATHER)) {
                Preferences.setWeather(c, cv.getAsBoolean(Model.Profile.WEATHER));
            }
            if (cv.containsKey(Model.Profile.WEIGHT)) {
                Preferences.setWeight(c, cv.getAsBoolean(Model.Profile.WEIGHT));
            }
            if (cv.containsKey(Model.Profile.HEART_RATE)) {
                Preferences.setHeartRate(c, cv.getAsBoolean(Model.Profile.HEART_RATE));
            }
            if (cv.containsKey(Model.Profile.NICKNAME)) {
                Preferences.setNickname(c, cv.getAsString(Model.Profile.NICKNAME));
            }
            if (cv.containsKey(Model.Profile.CREATED)) {
                Preferences.setCreated(c, cv.getAsLong(Model.Profile.CREATED));
            }
            if (cv.containsKey(Model.Profile.MODIFIED)) {
                Preferences.setModified(c, cv.getAsLong(Model.Profile.MODIFIED));
            }
        }
    }

    @Override
    protected void handleRelations(Context context, String id, String rid) {
        // Nothing to do
    }

}

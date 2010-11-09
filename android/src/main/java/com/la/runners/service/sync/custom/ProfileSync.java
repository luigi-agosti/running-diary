
package com.la.runners.service.sync.custom;

import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.la.runners.R;
import com.la.runners.activity.Preferences;
import com.la.runners.parser.JsonParserIterator;
import com.la.runners.parser.ProfileParser;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.util.Constants;
import com.la.runners.util.network.NetworkService;

public class ProfileSync extends BasicSync {

    public ProfileSync() {
        super(Model.Profile.CONTENT_URI, Constants.Server.PROFILE_CONTENT_URL);
        setSyncUpReourceId(R.string.syncService_profile);
    }

    @Override
    public void syncUp(Context context, SyncNotifier syncNotifier) {
        NetworkService.postProfile(context, Model.Profile.convert(context));
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
    public void syncDown(Context context, SyncNotifier syncNotifier) {
        ProfileParser parser = NetworkService.getProfileParser(context);
        while (parser.hasNext()) {
            ContentValues cv = parser.next();
            if (cv.containsKey(Model.Profile.SHOES)) {
                Preferences.setShoes(context, cv.getAsBoolean(Model.Profile.SHOES));
            }
            if (cv.containsKey(Model.Profile.WEATHER)) {
                Preferences.setWeather(context, cv.getAsBoolean(Model.Profile.WEATHER));
            }
            if (cv.containsKey(Model.Profile.WEIGHT)) {
                Preferences.setWeight(context, cv.getAsBoolean(Model.Profile.WEIGHT));
            }
            if (cv.containsKey(Model.Profile.HEART_RATE)) {
                Preferences.setHeartRate(context, cv.getAsBoolean(Model.Profile.HEART_RATE));
            }
            if (cv.containsKey(Model.Profile.NICKNAME)) {
                Preferences.setNickname(context, cv.getAsString(Model.Profile.NICKNAME));
            }
            if (cv.containsKey(Model.Profile.CREATED)) {
                Preferences.setCreated(context, cv.getAsLong(Model.Profile.CREATED));
            }
            if (cv.containsKey(Model.Profile.MODIFIED)) {
                Preferences.setModified(context, cv.getAsLong(Model.Profile.MODIFIED));
            }
        }
    }

    @Override
    protected void handleRelations(Context context, String id, String rid) {
        // Nothing to do
    }

}

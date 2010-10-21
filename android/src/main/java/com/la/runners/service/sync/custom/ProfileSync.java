package com.la.runners.service.sync.custom;

import android.content.ContentValues;
import android.content.Context;

import com.la.runners.activity.Preferences;
import com.la.runners.parser.ProfileParser;
import com.la.runners.provider.Model;
import com.la.runners.service.sync.BasicSync;
import com.la.runners.service.sync.SyncNotifier;
import com.la.runners.util.AppLogger;
import com.la.runners.util.network.NetworkService;

public class ProfileSync extends BasicSync {

    @Override
    public void syncUp(Context context, SyncNotifier syncNotifier) {
        NetworkService.postProfile(context, Model.Profile.convert(context));
    }

    @Override
    public void clean(Context context, SyncNotifier syncNotifier) {
        //do not need to clean
    }

    @Override
    public void syncDown(Context context, SyncNotifier syncNotifier) {
        ProfileParser parser = NetworkService.getProfileParser(context);
        while (parser.hasNext()) {
            ContentValues cv = parser.next();
            AppLogger.debug("value of the parser are : " + cv);
            if(cv.containsKey(Model.Profile.SHOES)) {
                Preferences.setShoes(context, cv.getAsBoolean(Model.Profile.SHOES));
            }
            if(cv.containsKey(Model.Profile.WEATHER)) {
                Preferences.setWeather(context, cv.getAsBoolean(Model.Profile.WEATHER));
            }
            if(cv.containsKey(Model.Profile.WEIGHT)) {
                Preferences.setWeight(context, cv.getAsBoolean(Model.Profile.WEIGHT));
            }
            if(cv.containsKey(Model.Profile.HEART_RATE)) {
                Preferences.setHeartRate(context, cv.getAsBoolean(Model.Profile.HEART_RATE));
            }
            if(cv.containsKey(Model.Profile.NICKNAME)) {
                Preferences.setNickname(context, cv.getAsString(Model.Profile.NICKNAME));
            }
            if(cv.containsKey(Model.Profile.CREATED)) {
                Preferences.setCreated(context, cv.getAsLong(Model.Profile.CREATED));
            }
            if(cv.containsKey(Model.Profile.MODIFIED)) {
                Preferences.setModified(context, cv.getAsLong(Model.Profile.MODIFIED));
            }
        }
    }

}

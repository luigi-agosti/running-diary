
package com.la.runners.activity;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.la.runners.R;
import com.la.runners.provider.Model;
import com.la.runners.service.SyncService;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Notifier;
import com.la.runners.util.network.GoogleAuth;

/**
 * @author luigi.agosti
 */
public class Preferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    private static final String VERSION_KEY = "version";

    private static final String ACCOUNT_KEY = "accountPreference";
    
    private static final String FIRST_RUN = "firstRunPreference";
    
    private static final String GOOGLE_AUTH_TOKEN = "googleAuthToken";
    
    private static final String GOOGLE_ACSID_COOKIE = "googleAcidCookie";
    
    private static final String SYNC_PROFILE = "syncProfile";
    
    private static final String SAVE_PROFILE = "saveProfile";
    
    private static final String TRACKING_FREQUENCY = "avarageSize";

    private static final String DEFAULT_TRACKING_FREQUENCY = "10";

    public static final Intent getPreferenceIntent(Context context) {
        return new Intent(context, Preferences.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        setAccounts();
        setApplicationInfoPreferences();
        findPreference(SYNC_PROFILE).setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference paramPreference) {
                SyncService.startSyncProfile(getApplicationContext());
                return false;
            }
        });
        findPreference(SAVE_PROFILE).setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference paramPreference) {
                SyncService.startSaveProfile(getApplicationContext());
                return false;
            }
        });
    }
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String key) {
        if (ACCOUNT_KEY.equals(key)) {
            AppLogger.logVisibly("onSharedPreferenceChanged : " + key);
            SyncService.startDataSync(getApplicationContext());
            SyncService.startSyncProfile(getApplicationContext());
            setFirstRun(getApplicationContext(), false);
            finish();
        }
    }
    
    private void setAccounts() {
        Account[] accounts = GoogleAuth.getInstance().getAccounts(getApplicationContext());
        if(accounts == null || accounts.length <= 0) {
            Notifier.toastMessage(this, R.string.error_10);
        }
        if(accounts != null && accounts.length > 0) {
            ListPreference lp = (ListPreference)findPreference(ACCOUNT_KEY);
            String[] entries = new String[accounts.length];
            int index = 0;
            for(Account account : accounts) {
                entries[index] = account.name;
                index++;
            }
            lp.setEntryValues(entries);
            lp.setEntries(entries);
            lp.setKey(ACCOUNT_KEY);
        }
    }

    private void setApplicationInfoPreferences() {
        Preference version = (Preference)findPreference(VERSION_KEY);
        String packageName = getApplication().getApplicationInfo().packageName;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_META_DATA);
            version.setSummary("" + pInfo.versionName);
        } catch (Exception e) {
            if (AppLogger.isErrorEnabled()) {
                AppLogger.error("Problem trying to get version information : " + packageName, e);
            }
        }
    }
    
    public static final String getAccount(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(
                ACCOUNT_KEY, null);
    }
    
    public static final Boolean getHeartRate(Context c) {
    	return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(Model.Profile.HEART_RATE, false);
    }
    
    public static final Boolean getWeight(Context c) {
    	return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(Model.Profile.WEIGHT, false);
    }
    
    public static final Boolean getWeather(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(Model.Profile.WEATHER, false);
    }
    
    public static final Boolean getShoes(Context c) {
    	return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(Model.Profile.SHOES, false);
    }
    
    public static final Boolean getFirstRun(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(FIRST_RUN, true);
    }

    public static final int getAvarageSize(Context c) {
        //List preference doesn't support integer-array
        //http://code.google.com/p/android/issues/detail?id=2096
        return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(c).getString(TRACKING_FREQUENCY, DEFAULT_TRACKING_FREQUENCY));
    }
    
    public static final String getNickname(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(Model.Profile.NICKNAME, null);
    }

    public static final void setFirstRun(Context c, boolean value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putBoolean(FIRST_RUN, value);
        editor.commit();
    }

    public static final String getGoogleAuthToken(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(GOOGLE_AUTH_TOKEN, null);
    }
    
    public static final void setGoogleAuthToken(Context c, String value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putString(GOOGLE_AUTH_TOKEN, value);
        editor.commit();
    }

    public static final String getGoogleAcsidCookie(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(
                GOOGLE_ACSID_COOKIE, null);
    }
    
    public static final void setGoogleAcsidCookie(Context c, String value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putString(GOOGLE_ACSID_COOKIE, value);
        editor.commit();
    }

    public static void setNickname(Context c, String value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putString(Model.Profile.NICKNAME, value);
        editor.commit();
    }

    public static void setHeartRate(Context c, Boolean value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putBoolean(Model.Profile.HEART_RATE, value);
        editor.commit();
    }

    public static void setWeather(Context c, Boolean value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putBoolean(Model.Profile.WEATHER, value);
        editor.commit();
    }

    public static void setWeight(Context c, Boolean value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putBoolean(Model.Profile.WEIGHT, value);
        editor.commit();
    }

    public static void setShoes(Context c, Boolean value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putBoolean(Model.Profile.SHOES, value);
        editor.commit();
    }

    public static Long getModified(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getLong(Model.Profile.MODIFIED, System.currentTimeMillis());
    }

    public static Long getCreated(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getLong(Model.Profile.CREATED, System.currentTimeMillis());
    }

    public static void setModified(Context c, Long value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putLong(Model.Profile.MODIFIED, value);
        editor.commit();
    }
    
    public static void setCreated(Context c, Long value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(c).edit();
        editor.putLong(Model.Profile.CREATED, value);
        editor.commit();
    }

}

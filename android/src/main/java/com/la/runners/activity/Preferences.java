
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
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.la.runners.R;
import com.la.runners.util.AppLogger;
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

	private static final String HEART_RATE = "heartRate";

	private static final String SHOES = "shoes";

	private static final String WEIGHT = "weight";

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
    }
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String key) {
        if (ACCOUNT_KEY.equals(key)) {
            AppLogger.logVisibly("onSharedPreferenceChanged : " + key);
            setFirstRun(getApplicationContext(), false);
        } 
    }
    
    private void setAccounts() {
        Account[] accounts = GoogleAuth.getInstance().getAccounts(getApplicationContext());
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
    
    public static final Boolean getHeartRate(Context context) {
    	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(HEART_RATE, false);
    }
    
    public static final Boolean getWeight(Context context) {
    	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(WEIGHT, false);
    }
    
    public static final Boolean getShoes(Context context) {
    	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SHOES, false);
    }
    
    public static final Boolean getFirstRun(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
                FIRST_RUN, true);
    }

    public static final void setFirstRun(Context context, boolean value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(FIRST_RUN, value);
        editor.commit();
    }

    public static final String getGoogleAuthToken(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(
                GOOGLE_AUTH_TOKEN, null);
    }
    
    public static final void setGoogleAuthToken(Context context, String value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(GOOGLE_AUTH_TOKEN, value);
        editor.commit();
    }

    public static final String getGoogleAcsidCookie(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(
                GOOGLE_ACSID_COOKIE, null);
    }
    
    public static final void setGoogleAcsidCookie(Context context, String value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(GOOGLE_ACSID_COOKIE, value);
        editor.commit();
    }

}

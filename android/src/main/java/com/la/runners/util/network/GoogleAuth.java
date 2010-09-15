
package com.la.runners.util.network;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.la.runners.Runners;
import com.la.runners.activity.Preferences;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Constants;

public class GoogleAuth {

    private static final String STATUS_VERIFICATION_URL = Constants.Server.APPENGINE_URL + "/auth/check";

    private static final String LOGIN_URL = Constants.Server.APPENGINE_URL + "/_ah/login?continue=" +
        Constants.Server.APPENGINE_URL + "&auth=";

    private static final String AUTH_TOKEN_TYPE = "ah";
    
    private static final String GOOGLE_ACCOUNT_TYPES = "com.google";
    
    private static final String ACSID_COOKIE = "ACSID=";
    
    private static final String SEPARATOR = ":";
    
    private static final String SET_COOKIES = "Set-Cookie";
    
    private static final String LOGGED_IN = "TRUE";
    
    private static HttpManager httpManager = null;

    private static GoogleAuth instance;

    private GoogleAuth() { 
        httpManager = Runners.getInstance().getHttpManager();
    }

    public Cookie getAuthCookie() {
        return authCookie;
    }

    public void setAuthCookie(Cookie authCookie) {
        this.authCookie = authCookie;
    }

    private Cookie authCookie = null;

    public static GoogleAuth getInstance() {
        if (instance == null) {
            instance = new GoogleAuth();
        }
        return instance;
    }

    /**
     * Call the server and verify that the user is logged in properly
     * @param context
     * @return
     */
    public Boolean isLoggedIn(Context context) {
        try {
            String result = httpManager.getUrlAsString(STATUS_VERIFICATION_URL, context, false);
            if(LOGGED_IN.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            AppLogger.error("Problem when trying to check the logged status", e);
        }
        return false;
    }
    
    /**
     * Login, in this case invalidate the token by default
     * @param context
     * @param selectedAccount
     * @param intent
     */
    public void login(Context context, String selectedAccount, Intent intent) {
        login(context, selectedAccount, intent, true);
    }
    
    /**
     * Login get a token from the _ah/login and set the token in the preferences
     * @param context
     * @param selectedAccount
     * @param intent
     * @param invalidate
     */
    public void login(Context context, String selectedAccount, Intent intent, boolean invalidate) {
        Account[] accounts = getAccounts(context);
        if (accounts != null && accounts.length > 0) {
            for (Account account : accounts) {
                if (selectedAccount.equals(account.name)) {
                    AccountManager accountManager = AccountManager.get(context);
                    accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, false, new GetAuthTokenCallback(
                            context, intent, account, invalidate), null);
                }
            }
        }
    }

    /**
     * Get the list of google accounts
     * @param context
     * @return
     */
    public Account[] getAccounts(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        return accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPES);
    }

    private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {

        private Intent intent;

        private Context context;
        
        private Account account;
        
        private boolean invalidate;

        public GetAuthTokenCallback(Context context, Intent intent, Account account, boolean invalidate) {
            this.intent = intent;
            this.context = context;
            this.account = account;
            this.invalidate = invalidate;
        }
        
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            Bundle bundle;
            try {
                bundle = result.getResult();
                Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
                if (intent != null) {
                    context.startActivity(intent);
                } else {
                    String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                    if(invalidate) {
                        AccountManager accountManager = AccountManager.get(context);
                        accountManager.invalidateAuthToken(account.type, token);
                        login(context, account.name, intent, false);
                    } else {
                        onGetAuthToken(token);
                    }
                }
            } catch (Exception e) {
                if(AppLogger.isErrorEnabled()) {
                    AppLogger.error(e);
                }
            }
        }

        protected void onGetAuthToken(String token) {
            DefaultHttpClient defaultHttpClient = httpManager.getDefaultHttpClient();
            try {
                defaultHttpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS,
                        false);
                HttpGet httpGet = new HttpGet(LOGIN_URL + token);
                HttpResponse response;
                response = defaultHttpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() != 302) {
                    if(AppLogger.isErrorEnabled()) {
                        AppLogger.error("302 ? there was a problem");
                    }
                    return;
                }
                Header[] headers = response.getHeaders(SET_COOKIES);
                String acsidCookie = null;
                for (Header header: headers) {
                    if (header.getValue().indexOf(ACSID_COOKIE) >=0) {
                        String value = header.getValue();
                        String[] pairs = value.split(SEPARATOR);
                        acsidCookie = pairs[0];
                    }
                }
                if(acsidCookie != null) {
                    Preferences.setGoogleAuthToken(context, token);
                    Preferences.setGoogleAcsidCookie(context, acsidCookie);
                }
            } catch (Exception e) {
                if(AppLogger.isErrorEnabled()) {
                    AppLogger.error(e);
                }
            } finally {
                defaultHttpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
            }
            context.startService(intent);
        }
    }

}

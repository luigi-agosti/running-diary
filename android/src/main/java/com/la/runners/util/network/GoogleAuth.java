
package com.la.runners.util.network;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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

import com.la.runners.R;
import com.la.runners.Runners;
import com.la.runners.activity.Preferences;
import com.la.runners.exception.ConnectionException;
import com.la.runners.parser.AuthCheckParser;
import com.la.runners.service.SyncService;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Constants;

public class GoogleAuth {

    private static final String LOGIN_URL = Constants.Server.APPENGINE_URL + "/_ah/login?continue=" +
        Constants.Server.APPENGINE_URL + "&auth=";

    private static final String AUTH_TOKEN_TYPE = "ah";
    
    private static final String GOOGLE_ACCOUNT_TYPES = "com.google";
    
    private static final String ACSID_COOKIE = "ACSID=";
    
    private static final String SEPARATOR = ":";
    
    private static final String SET_COOKIES = "Set-Cookie";
    
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
    public Boolean isLoggedIn(final Context context) {
        return isLoggedIn(context, true);
    }
    
    private Boolean isLoggedIn(final Context context, boolean withRetry) {
        try {
            AuthCheckParser result = NetworkService.getAuthCheckParser(context);
            if(result != null && result.isLoggerIn()) {
                AppLogger.debug("Logged in");
                return true;
            }
        } catch (Throwable e) {
            AppLogger.error("Problem when trying to check the logged status", e);
            if(withRetry) {
                return isLoggedIn(context, false);
            }
        }
        return false;
    }
    
    /**
     * Login, in this case invalidate the token by default
     * @param context
     * @param selectedAccount
     * @param intent
     * @throws RuntimeException
     */
    public void login(Context context, String selectedAccount, final Intent intent) {
        login(context, selectedAccount, intent, true);
    }

    private void login(final Context context, String selectedAccount, final Intent intent, boolean invalidate) {
        Account[] accounts = getAccounts(context);
        if (accounts != null && accounts.length > 0) {
            for (Account account : accounts) {
                if (selectedAccount.equals(account.name)) {
                    AccountManager accountManager = AccountManager.get(context);
                    accountManager.getAuthToken(account, AUTH_TOKEN_TYPE, false, 
                    	new GetAuthTokenCallback(context, account, invalidate), null);
                }
            }
        }
    }

    /**
     * Get the list of google accounts
     * @param context
     * @return
     */
    public Account[] getAccounts(final Context context) {
        AccountManager accountManager = AccountManager.get(context);
        return accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPES);
    }

    private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {

        private Context context;
        
        private Account account;
        
        private boolean invalidate;

        public GetAuthTokenCallback(final Context context, Account account, boolean invalidate) {
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
                        onGetAuthToken(context, token);
                    }
                }
            } catch (Throwable e) {
                if(AppLogger.isErrorEnabled()) {
                    AppLogger.error(e);
                }
            }
        }

        protected void onGetAuthToken(Context context, String token) throws ClientProtocolException, IOException {
            DefaultHttpClient defaultHttpClient = httpManager.getDefaultHttpClient();
            try {
                defaultHttpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);
                HttpGet httpGet = new HttpGet(LOGIN_URL + token);
                HttpResponse response = defaultHttpClient.execute(httpGet);
                //if (response.getStatusLine().getStatusCode() != 302) {
                //    throw new RuntimeException("302 ? there was a problem");
                //}
                Header[] headers = response.getHeaders(SET_COOKIES);
                String acsidCookie = null;
                for (Header header: headers) {
                    if (header.getValue().indexOf(ACSID_COOKIE) >= 0) {
                        String value = header.getValue();
                        String[] pairs = value.split(SEPARATOR);
                        acsidCookie = pairs[0];
                    }
                }
                if(acsidCookie != null) {
                    Preferences.setGoogleAuthToken(context, token);
                    Preferences.setGoogleAcsidCookie(context, acsidCookie);
                    SyncService.startSyncProfile(context);
                    SyncService.startDataSync(context);
                } else {
                    throw new ConnectionException(R.string.error_13);
                }
            } catch (Throwable t) {
            	AppLogger.error(t);
            } finally {
                defaultHttpClient.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
            }
        }
    }

}

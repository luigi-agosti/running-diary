
package com.la.runners.util.network;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.content.ContentValues;
import android.content.Context;
import android.provider.Settings.Secure;

import com.la.runners.activity.Preferences;
import com.la.runners.util.AppLogger;

/**
 * @author luigi.agosti
 */
public class HttpManager {

    private static final String HEADER_DEVICE_UID = "device_uid";

    private static final String HTTP = "http";

    private static final String HTTPS = "https";
    
    private static final String COOKIES = "Cookie";

    private static final int HTTP_PORT = 80;

    private static final int HTTPS_PORT = 443;

    private AbstractHttpClient client;

    public HttpManager(Context context) {
        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HTTP, PlainSocketFactory.getSocketFactory(), HTTP_PORT));
        schemeRegistry.register(new Scheme(HTTPS, SSLSocketFactory.getSocketFactory(), HTTPS_PORT));
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        client = new DefaultHttpClient(cm, params);
    }

    public DefaultHttpClient getDefaultHttpClient() {
        return (DefaultHttpClient)client;
    }

    public InputStream getUrlAsStream(String url, final Context context, boolean withDeviceUid) {
        if (!Network.isNetworkAvailable(context)) {
            AppLogger.warn("Network is down can't procede with loading resource at url : " + url);
            return null;
        }
        HttpUriRequest get = new HttpGet(url);
        HttpResponse response;
        InputStream is = null;
        try {
            String acsidCookie = Preferences.getGoogleAcsidCookie(context);
            if (acsidCookie != null) {
                get.addHeader(COOKIES, acsidCookie);
            }
            if (withDeviceUid) {
                String deviceUid = Secure
                        .getString(context.getContentResolver(), Secure.ANDROID_ID);
                AppLogger.logVisibly("adding header : " + deviceUid);
                get.addHeader(new BasicHeader(HEADER_DEVICE_UID, deviceUid));
            }
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            return is;
        } catch (Exception e) {
            if (AppLogger.isErrorEnabled()) {
                AppLogger.error("Problem loading resource at url " + url, e);
            }
            closeSilently(is);
            return null;
        }
    }

    public InputStream getUrlAsStream(String url, Context context) {
        return getUrlAsStream(url, context, false);
    }

    public static final ByteArrayOutputStream download(HttpManager httpService, String url,
            Context context) {
        if (!Network.isNetworkAvailable(context)) {
            AppLogger.warn("Network is down can't procede with loading resource at url : " + url);
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = httpService.getUrlAsStream(url, context);
        try {
            if (is == null) {
                return null;
            }
            int c;
            while ((c = is.read()) != -1) {
                out.write(c);
            }
            return out;
        } catch (Exception e) {
            if (AppLogger.isErrorEnabled()) {
                AppLogger.error("Problem during the download for : " + url, e);
            }
            closeSilently(out);
            return null;
        } finally {
            closeSilently(is);
        }
    }

    private static final void closeSilently(Closeable c) {
        if (c == null)
            return;
        try {
            c.close();
        } catch (Throwable t) {
            if (AppLogger.isInfoEnabled()) {
                AppLogger.info("Problem trying to close some object.", t);
            }
        }
    }

    public boolean post(final Context context, String url, String data) {
        if (!Network.isNetworkAvailable(context)) {
            AppLogger.warn("Network is down can't procede with loading resource at url : " + url);
            return false;
        }
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response;
        InputStream is = null;
        try {
            String acsidCookie = Preferences.getGoogleAcsidCookie(context);
            if (acsidCookie != null) {
                httpPost.addHeader(COOKIES, acsidCookie);
            }
            StringEntity tmp = null;
            try {
                tmp = new StringEntity(data,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                AppLogger.error("HTTPHelp : UnsupportedEncodingException : ", e);
            }
            httpPost.setEntity(tmp);
            response = client.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                return true;
            }
            return false;
        } catch (Exception e) {
            if (AppLogger.isErrorEnabled()) {
                AppLogger.error("Problem loading resource at url " + url, e);
            }
            closeSilently(is);
            return false;
        }
    }

}

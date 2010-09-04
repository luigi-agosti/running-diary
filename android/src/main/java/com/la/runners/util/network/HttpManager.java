
package com.la.runners.util.network;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.provider.Settings.Secure;

import com.la.runners.util.AppLogger;

/**
 * @author luigi.agosti
 */
public class HttpManager {

    private static final String HEADER_DEVICE_UID = "device_uid";

    private static final String HTTP = "http";

    private static final String HTTPS = "https";

    private static final int HTTP_PORT = 80;
    
    private static final int HTTPS_PORT = 443;

    private boolean threadSafe;

    private AbstractHttpClient client;

    private HttpUriRequest get;

    public HttpManager(Context context) {
        this(true, context);
    }

    public HttpManager(boolean threadSafe, final Context c) {
        this.threadSafe = threadSafe;
        if (threadSafe == true) {
            HttpParams params = new BasicHttpParams();
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme(HTTP, PlainSocketFactory.getSocketFactory(),
                    HTTP_PORT));
            schemeRegistry.register(new Scheme(HTTPS, SSLSocketFactory.getSocketFactory(), HTTPS_PORT));
            ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
            client = new DefaultHttpClient(cm, params);
        } else {
            client = new DefaultHttpClient();
        }
        
    }

    public InputStream getUrlAsStream(String url, final Context context, boolean withDeviceUid) {
        if (!Network.isNetworkAvailable(context)) {
            AppLogger.warn("Network is down can't procede with loading resource at url : " + url);
            return null;
        }
        if (get != null && !this.threadSafe) {
            get.abort();
        }
        get = new HttpGet(url);
        HttpResponse response;
        InputStream is = null;
        try {
            response = client.execute(get);
            if(withDeviceUid) {
                client.addRequestInterceptor(new HttpRequestInterceptor() {
                    @Override
                    public void process(HttpRequest request, HttpContext httpContext) throws HttpException,
                            IOException {
                        request.addHeader(new BasicHeader(HEADER_DEVICE_UID, 
                                Secure.getString(context.getContentResolver(), Secure.ANDROID_ID)));
                    }
                });
            }
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
}

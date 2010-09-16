package com.la.runners.util.network;

import java.io.InputStream;

import android.content.Context;
import android.content.Intent;

import com.la.runners.Runners;
import com.la.runners.activity.Preferences;
import com.la.runners.parser.AuthCheckParser;
import com.la.runners.parser.RunParser;
import com.la.runners.parser.SchemaParser;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Constants;

public class NetworkService {
    
    public static final SchemaParser getSchema(Context context) {
        InputStream is = getHttpManager(context).getUrlAsStream(Constants.Server.SCHEMA_URL, context);
        SchemaParser parser = null;
        try {
            parser = new SchemaParser(is);
        } catch(Exception e) {
            AppLogger.error(e);
        }
        return parser;
    }
    
    public static final RunParser getRunParser(Context context) {
        InputStream is = getHttpManager(context).getUrlAsStream(Constants.Server.RUN_CONTENT_URL, context);
        RunParser parser = null; 
        try {
            parser = new RunParser(is);
        } catch(Exception e) {
            AppLogger.error(e);
        }
        return parser;
	}

    public static final AuthCheckParser getAuthCheckParser(Context context) {
        InputStream is = getHttpManager(context).getUrlAsStream(Constants.Server.AUTH_CHECK, context);
        AuthCheckParser parser = null; 
        try {
            parser = new AuthCheckParser(is);
        } catch(Exception e) {
            AppLogger.error(e);
        }
        return parser;
    }
	
	public static final HttpManager getHttpManager(Context context) {
		Runners runners = Runners.getInstance();
		HttpManager httpManager = null;
        if(runners == null) {
            httpManager = new HttpManager(context);
        } else {
            httpManager = runners.getHttpManager();            
        }
        return httpManager;
	}

}

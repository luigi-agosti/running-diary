package com.la.runners.util.network;

import java.io.InputStream;

import android.content.Context;

import com.la.runners.Runners;
import com.la.runners.parser.RunParser;
import com.la.runners.parser.SchemaParser;
import com.la.runners.util.AppLogger;

public class NetworkService {
    
    private static final String SCHEMA_URL = "http://running-diary.appspot.com/data";
    
    public static final String RUN_CONTENT_URL = "http://running-diary.appspot.com/data/Run";
    
    public static final SchemaParser getSchema(Context context) {
        InputStream is = getHttpManager(context).getUrlAsStream(SCHEMA_URL, context);
        SchemaParser parser = null; 
        try {
            parser = new SchemaParser(is);
        } catch(Exception e) {
            AppLogger.error(e);
        }
        return parser;
    }

	public static final RunParser getRunParser(Context context) {
		InputStream is = getHttpManager(context).getUrlAsStream(RUN_CONTENT_URL, context);
        RunParser parser = null; 
        try {
            parser = new RunParser(is);
        } catch(Exception e) {
            AppLogger.error(e);
        }
        return parser;
	}
	
	private static final HttpManager getHttpManager(Context context) {
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

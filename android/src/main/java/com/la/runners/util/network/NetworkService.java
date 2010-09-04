package com.la.runners.util.network;

import java.io.InputStream;

import android.content.Context;

import com.la.runners.Runners;
import com.la.runners.parser.SchemaParser;
import com.la.runners.util.AppLogger;

public class NetworkService {
    
    private static final String SCHEMA_URL = "http://running-diary.appspot.com/data";
    
    public static final SchemaParser getSchema(Context context) {
        Runners runners = Runners.getInstance();
        HttpManager httpManager = null;
        if(runners == null) {
            httpManager = new HttpManager(context);
        } else {
            httpManager = runners.getHttpManager();            
        }
        InputStream is = httpManager.getUrlAsStream(SCHEMA_URL, context);
        
        SchemaParser parser = null; 
        try {
            parser = new SchemaParser(is);
        } catch(Exception e) {
            AppLogger.error(e);
        }
        return parser;
    }

}

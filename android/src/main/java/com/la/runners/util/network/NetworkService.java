package com.la.runners.util.network;

import android.content.Context;

import com.la.runners.Runners;
import com.la.runners.parser.AuthCheckParser;
import com.la.runners.parser.ProfileParser;
import com.la.runners.parser.RunParser;
import com.la.runners.parser.SchemaParser;
import com.la.runners.util.Constants;

public class NetworkService {
    
    public static final SchemaParser getSchema(Context context) {
        return new SchemaParser(getHttpManager(context).getUrlAsStream(Constants.Server.SCHEMA_URL, context));
    }
    
    public static final RunParser getRunParser(Context context) {
        return new RunParser(getHttpManager(context).getUrlAsStream(Constants.Server.RUN_CONTENT_URL, context));
	}
    
    public static final ProfileParser getProfileParser(Context context) {
        return new ProfileParser(getHttpManager(context).getUrlAsStream(Constants.Server.PROFILE_CONTENT_URL, context));
    }
    
    public static final void postRun(Context context, String runs) {
        getHttpManager(context).post(context, Constants.Server.RUN_CONTENT_URL, runs);
    }
    
    public static final void postLocation(Context context, String locations) {
        getHttpManager(context).post(context, Constants.Server.LOCATION_CONTENT_URL, locations);
    }

    public static final void postProfile(Context context, String profile) {
        getHttpManager(context).post(context, Constants.Server.PROFILE_CONTENT_URL, profile);
    }
    
    public static final AuthCheckParser getAuthCheckParser(Context context) {
        return new AuthCheckParser(getHttpManager(context).getUrlAsStream(Constants.Server.AUTH_CHECK, context));
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

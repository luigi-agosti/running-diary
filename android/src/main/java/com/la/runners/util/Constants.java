package com.la.runners.util;

public interface Constants {
	
    public interface Server {
        
        String APPENGINE_URL = "http://running-diary.appspot.com";
    
        String SCHEMA_URL = APPENGINE_URL + "/data";
    
        String RUN_CONTENT_URL = APPENGINE_URL + "/data/Run";
        
        String LOCATION_CONTENT_URL = APPENGINE_URL + "/data/Location";
        
        String PROFILE_CONTENT_URL = APPENGINE_URL + "/data/Profile";
        
        String AUTH_CHECK = APPENGINE_URL + "/auth/check";
    }

}

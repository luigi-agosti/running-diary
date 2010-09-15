
package com.la.runners.provider;

import android.content.ContentResolver;
import android.database.Cursor;


/**
 * @author luigi.agosti
 */
public class Query {

    public static final String PARAMETER = "= ?";

//    private static final String ASCENDANT = " asc";
//
//    private static final String DESCENDANT = " desc";
//
//    private static final String IS_NULL = " is null";
//
//    private static final String AND = " and ";

    public static class Run {
    	
    	public static final Cursor all(ContentResolver resolver) {
    		return resolver.query(Model.Run.CONTENT_URI, null, null, null, null);
    	}
    	
    }

}

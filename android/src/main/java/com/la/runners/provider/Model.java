package com.la.runners.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;
import android.net.Uri;

public class Model {
    
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("hh:mm a dd MMM yyyy");
    
    public static final String SEPARATOR = "/";
    
    public static final String ID_INDICATOR = "#";

    public static final String AUTHORITY = "com.la.runners";

    private static final String CONTENT_PREFIX = "content://";

    public static class Run {
    	
    	public static final String NAME = Run.class.getSimpleName(); 
    	

    	
    	public static final Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);
    	
    	public static final String ID = "id";
    	
    	public static final String TIME = "time";
    	
    	public static final String DISTANCE = "distance";
    	
    	public static final String DATE = "date";
    	
    	public static final String NOTE = "note";

		public static final int INCOMING_COLLECTION = 10;

		public static final int INCOMING_ITEM = 20;

		public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.runners.run";

		public static final String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.runners.run";
    	
        public static final String id(Cursor c) {
            return c.getString(c.getColumnIndex(ID));
        }

        public static Long time(Cursor c) {
            return c.getLong(c.getColumnIndex(TIME));
        }

        public static String distance(Cursor c) {
            return c.getString(c.getColumnIndex(DISTANCE));
        }

        public static Long date(Cursor c) {
            return c.getLong(c.getColumnIndex(DATE));
        }

        public static String formattedModifiedDate(Cursor c) {
        	Date date = new Date(c.getLong(c.getColumnIndex(DATE)));
        	return DATE_FORMATTER.format(date);
        }

        public static String note(Cursor c) {
            return c.getString(c.getColumnIndex(NOTE));
        }
        
    }

}

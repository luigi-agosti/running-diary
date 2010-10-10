package com.la.runners.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONStringer;

import com.la.runners.util.AppLogger;

import android.database.Cursor;
import android.net.Uri;

public class Model {
    
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("hh:mm a dd MMM yyyy");
    
    public static final String SEPARATOR = "/";
    
    public static final String ID_INDICATOR = "#";

    public static final String AUTHORITY = "com.la.runners";

    private static final String CONTENT_PREFIX = "content://";
    
    public interface Database {
    	
    	String NAME = "com.la.runners.RunnersDiary.db";
    	
    	int VERSION = 5;
    	
    }

    public static class Run {
    	
    	public static final String NAME = Run.class.getSimpleName(); 
    	
    	public static final Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);
    	
    	public static final String ID = "_id";
    	
    	public static final String REMOTE_ID = "id";
    	
    	public static final String TIME = "time";
    	
    	public static final String DISTANCE = "distance";
    	
    	public static final String DATE = "date";
    	
    	public static final String NOTE = "note";
    	
    	public static final String HEART_RATE ="heartRate";
    	
    	public static final String WEIGHT = "weight";
    	
    	public static final String SHOES = "shoes";
    	
    	public static final String SHARE = "share";

		public static final int INCOMING_COLLECTION = 10;

		public static final int INCOMING_ITEM = 20;

		public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.runners.run";

		public static final String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.runners.run";

		public static final String YEAR = "year";
		
		public static final String MONTH = "month";
		
		public static final String DAY = "day";

        public static final String MODIFIED = "modified";

        public static final String DAY_TIME = "dayTime";
    	
        public static final String id(Cursor c) {
            return c.getString(c.getColumnIndex(ID));
        }

        public static final Long time(Cursor c) {
            return c.getLong(c.getColumnIndex(TIME));
        }

        public static final Float distance(Cursor c) {
            return c.getFloat(c.getColumnIndex(DISTANCE));
        }

        public static final Long date(Cursor c) {
            return c.getLong(c.getColumnIndex(DATE));
        }

        public static final String formattedModifiedDate(Cursor c) {
        	Date date = new Date(c.getLong(c.getColumnIndex(DATE)));
        	return DATE_FORMATTER.format(date);
        }

        public static final String note(Cursor c) {
            return c.getString(c.getColumnIndex(NOTE));
        }
        
        public static final String convertAll(Cursor c) {
            JSONStringer stringer = new JSONStringer();
            convertRun(stringer, c);
            String result = stringer.toString();
            if(AppLogger.isDebugEnabled()) {
                AppLogger.debug(result);
            }
            return result;
        }
        
        private static final void convertRun(JSONStringer js, Cursor c) {
            try {
                js.array();
                while(c.moveToNext()) {
                    js.object();
                    Long date = Model.Run.date(c);
                    if(date != null) {
                    	js.key(Model.Run.DATE).value(date);
                    }
                    Integer year = Model.Run.year(c);
                    if(year != null) {
                    	js.key(Model.Run.YEAR).value(year);
                    }
                    Integer month = Model.Run.month(c);
                    if(month != null) {
                    	js.key(Model.Run.MONTH).value(month);
                    }
                    Integer day = Model.Run.day(c);
                    if(day != null) {
                    	js.key(Model.Run.DAY).value(day);
                    }
                    Float distance = Model.Run.distance(c);
                    if(distance != null) {
                    	js.key(Model.Run.DISTANCE).value(distance);
                    }
                    String shoes = Model.Run.shoes(c);
                    if(shoes != null) {
                    	js.key(Model.Run.SHOES).value(shoes);
                    }
                    Long time = Model.Run.time(c);
                    if(time != null) {
                    	js.key(Model.Run.TIME).value(time);
                    }
                    Long dayTime = Model.Run.dayTime(c);
                    if(dayTime != null) {
                        js.key(Model.Run.DAY_TIME).value(dayTime);
                    }
                    Integer heartRate = Model.Run.heartRate(c);
                    if(heartRate != null) {
                    	js.key(Model.Run.HEART_RATE).value(heartRate);
                    }
                    Integer weight = Model.Run.weight(c);
                    if(weight != null) {
                    	js.key(Model.Run.WEIGHT).value(weight);
                    }
                    String note = Model.Run.note(c);
                    if(note != null) {
                    	js.key(Model.Run.NOTE).value(note);
                    }
                    Long modified = Model.Run.modified(c);
                    if(modified != null) {
                    	js.key(Model.Run.MODIFIED).value(modified);
                    }
                    Boolean shared = Model.Run.share(c);
                    if(shared != null) {
                    	js.key(Model.Run.SHARE).value(shared);
                    }
                    js.endObject();
                }
                js.endArray();
            } catch(JSONException e) {
                if(AppLogger.isErrorEnabled()) {
                    AppLogger.error(e);
                }
            }
        }

		private static Boolean share(Cursor c) {
			int share = c.getInt(c.getColumnIndex(SHARE));
			if(share == 1) {
				return Boolean.TRUE;
			} 
			return Boolean.FALSE;
		}

		private static Long modified(Cursor c) {
			return c.getLong(c.getColumnIndex(MODIFIED));
		}

		private static Long dayTime(Cursor c) {
            return c.getLong(c.getColumnIndex(DAY_TIME));
        }

        private static Integer weight(Cursor c) {
			return c.getInt(c.getColumnIndex(WEIGHT));
		}

		private static Integer heartRate(Cursor c) {
			return c.getInt(c.getColumnIndex(HEART_RATE));
		}

		private static String shoes(Cursor c) {
			return c.getString(c.getColumnIndex(SHOES));
		}

		private static Integer day(Cursor c) {
			return c.getInt(c.getColumnIndex(DAY));
		}

		private static Integer month(Cursor c) {
			return c.getInt(c.getColumnIndex(MONTH));
		}

		private static Integer year(Cursor c) {
			return c.getInt(c.getColumnIndex(YEAR));
		}
        
    }

}
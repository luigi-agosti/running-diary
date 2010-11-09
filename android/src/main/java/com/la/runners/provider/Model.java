package com.la.runners.provider;

import org.json.JSONException;
import org.json.JSONStringer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.la.runners.activity.Preferences;
import com.la.runners.provider.SyncProvider.Syncable;
import com.la.runners.util.AppLogger;
import com.la.runners.util.Utils;

public class Model {
    
    public static final String SEPARATOR = "/";
    
    public static final String ID_INDICATOR = "#";

    public static final String AUTHORITY = "com.la.runners";

    public static final String CONTENT_PREFIX = "content://";
    
    public static final String PARAMETER = "= ?";
    
    private static final String DESCENDANT = " desc";

    private static final String IS_NULL = " is null";

    
    public interface Database {
    	
    	String NAME = "com.la.runners.RunnersDiary.db";
    	
    	int VERSION = 5;
    	
    }

    public static class Run extends Syncable {
    	
    	public static final String NAME = Run.class.getSimpleName(); 
    	
    	public static final Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);
    	
    	public static final String DISTANCE = "distance";
    	public static final String SPEED = "speed";
    	
    	public static final String TIME = "time";
    	public static final String CREATED = "created";
    	public static final String YEAR = "year";    	
    	public static final String MONTH = "month";
    	public static final String DAY = "day";
    	public static final String HOUR = "hour";
    	public static final String START_DATE = "startDate";
    	public static final String END_DATE = "endDate";
    	public static final String MODIFIED = "modified";
    	
    	
    	public static final String NOTE = "note";
    	public static final String HEART_RATE ="heartRate";
    	public static final String WEIGHT = "weight";
    	public static final String SHOES = "shoes";
    	public static final String SHARE = "share";

		public static final int INCOMING_COLLECTION = 10;

		public static final int INCOMING_ITEM = 20;

		public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.runners.run";

		public static final String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.runners.run";

        public static final Long time(Cursor c) {
            return c.getLong(c.getColumnIndex(TIME));
        }

        public static final Long distance(Cursor c) {
            return c.getLong(c.getColumnIndex(DISTANCE));
        }

        public static final Long created(Cursor c) {
            return c.getLong(c.getColumnIndex(CREATED));
        }

        public static final Long speed(Cursor c) {
            return c.getLong(c.getColumnIndex(SPEED));
        }

        public static final String note(Cursor c) {
            return c.getString(c.getColumnIndex(NOTE));
        }
        
        public static final String convertAll(Cursor c) {
            JSONStringer stringer = new JSONStringer();
            convert(stringer, c);
            String result = stringer.toString();
            if(AppLogger.isDebugEnabled()) {
                AppLogger.debug(result);
            }
            return result;
        }
        
        private static final void convert(JSONStringer js, Cursor c) {
            try {
                js.array();
                while(c.moveToNext()) {
                    js.object();
                    Long created = created(c);
                    if(created != null) {
                    	js.key(CREATED).value(created);
                    }
                    Integer year = year(c);
                    if(year != null) {
                    	js.key(YEAR).value(year);
                    }
                    Integer month = month(c);
                    if(month != null) {
                    	js.key(MONTH).value(month);
                    }
                    Integer day = day(c);
                    if(day != null) {
                    	js.key(DAY).value(day);
                    }
                    Integer hour = hour(c);
                    if(hour != null) {
                        js.key(HOUR).value(hour);
                    }
                    Long distance = distance(c);
                    if(distance != null) {
                    	js.key(DISTANCE).value(distance);
                    }
                    Long speed = speed(c);
                    if(speed != null) {
                        js.key(SPEED).value(speed);
                    }
                    String shoes = shoes(c);
                    if(shoes != null) {
                    	js.key(SHOES).value(shoes);
                    }
                    Long time = time(c);
                    if(time != null) {
                    	js.key(TIME).value(time);
                    }
                    Integer heartRate = heartRate(c);
                    if(heartRate != null) {
                    	js.key(HEART_RATE).value(heartRate);
                    }
                    Integer weight = weight(c);
                    if(weight != null) {
                    	js.key(WEIGHT).value(weight);
                    }
                    String note = note(c);
                    if(note != null) {
                    	js.key(NOTE).value(note);
                    }
                    Long modified = modified(c);
                    if(modified != null) {
                    	js.key(MODIFIED).value(modified);
                    }
                    Long startDate = startDate(c);
                    if(startDate != null) {
                        js.key(START_DATE).value(startDate);
                    }
                    Long endDate = endDate(c);
                    if(endDate != null) {
                        js.key(END_DATE).value(endDate);
                    }
                    Boolean shared = share(c);
                    if(shared != null) {
                    	js.key(SHARE).value(shared);
                    }
                    Long id = Long.valueOf(id(c));
                    if(id != null) {
                        js.key(ID).value(id);
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
		
		public static Long startDate(Cursor c) {
		    return c.getLong(c.getColumnIndex(START_DATE));
		}
		
		private static Long endDate(Cursor c) {
		    return c.getLong(c.getColumnIndex(END_DATE));
		}

		private static Integer hour(Cursor c) {
            return c.getInt(c.getColumnIndex(HOUR));
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
		
		public static final Cursor all(ContentResolver cr) {
            return cr.query(CONTENT_URI, null, null, null, null);
        }

        public static final Cursor notSync(Context c) {
            return c.getContentResolver().query(CONTENT_URI, null, REMOTE_ID + IS_NULL, null, null);
        }
        
        public static final Cursor get(Context c, long id) {
            return c.getContentResolver().query(CONTENT_URI, null, ID + PARAMETER, new String [] {""+id} , null);
        }

        private static final String[] OP1 = new String[] {ID};
        
        private static final String[] OP2 = new String[]{REMOTE_ID};
        
        public static final String queryForCurrentRunId(Context c) {
            Cursor cursor = null;
            try {
                cursor = c.getContentResolver().query(CONTENT_URI, OP1, null, null , MODIFIED + DESCENDANT + " LIMIT 1");
                if (cursor.moveToFirst()) {
                    return Model.Run.id(cursor);
                } else {
                    return null;
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        public static final String queryForRemoteId(Context c, String runId) {
            Cursor cursor = null;
            try {
                cursor = c.getContentResolver().query(CONTENT_URI, OP2, ID + PARAMETER, new String[]{runId}, null);
                if(cursor.moveToFirst()) {
                    return remoteId(cursor);
                } else {
                    return null;
                }
            } finally {
                if(cursor != null) {
                    cursor.close();
                }
            }
        }
        
    }
    
    public static class Location extends Syncable {
        
        public static final String NAME = Location.class.getSimpleName(); 
        
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);
        
		public static final int INCOMING_COLLECTION = 30;

		public static final int INCOMING_ITEM = 40;

		public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.runners.location";

		public static final String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.runners.location";
        
        public static final String RUN_ID = "runId";
        
        public static final String DISTANCE = "distance";
        
        public static final String TIME = "time";
        
        public static final String TIMESTAMP = "timestamp";

        public static final String SPEED = "speed";
        
        public static final String ALTITUDE = "altitude";
        
        public static final String LONGITUDE = "longitude";
        
        public static final String LATITUDE = "latitude";
        
        public static final String ACCURACY = "accuracy";

        public static final String TOTAL_DISTANCE = "totalDistance";

        public static final String convertAll(Cursor c) {
            JSONStringer stringer = new JSONStringer();
            convert(stringer, c);
            String result = stringer.toString();
            if(AppLogger.isDebugEnabled()) {
                AppLogger.debug(result);
            }
            return result;
        }
        
        private static final void convert(JSONStringer js, Cursor c) {
            try {
                js.array();
                while(c.moveToNext()) {
                    js.object();
                    Long runId = runId(c);
                    if(runId != null) {
                        js.key(RUN_ID).value(runId);
                    }
                    Long speed = speed(c);
                    if(speed != null) {
                        js.key(SPEED).value(speed);
                    }
                    Long time = time(c);
                    if(time != null) {
                        js.key(TIME).value(time);
                    }
                    Long timestamp = timestamp(c);
                    if(timestamp != null) {
                        js.key(TIMESTAMP).value(timestamp);
                    }
                    Long distance = distance(c);
                    if(distance != null) {
                        js.key(DISTANCE).value(distance);
                    }
                    Long totalDistance = totalDistance(c);
                    if(totalDistance != null) {
                        js.key(TOTAL_DISTANCE).value(totalDistance);
                    }
                    Long altitude = altitude(c);
                    if(altitude != null) {
                        js.key(ALTITUDE).value(altitude);
                    }
                    Long longitude = longitude(c);
                    if(longitude != null) {
                        js.key(LONGITUDE).value(longitude);
                    }
                    Long latitude = latitude(c);
                    if(latitude != null) {
                        js.key(LATITUDE).value(latitude);
                    }
                    Long id = Long.valueOf(id(c));
                    if(id != null) {
                        js.key(ID).value(id);
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
        
        public static final Long runId(Cursor c) {
            return c.getLong(c.getColumnIndex(RUN_ID));
        }
        
        public static final Long distance(Cursor c) {
            return c.getLong(c.getColumnIndex(DISTANCE));
        }

        public static final String formattedDistance(Cursor c) {
            return Utils.Number.shorDecimal(c.getLong(c.getColumnIndex(DISTANCE))/100D);
        }

        public static final Long totalDistance(Cursor c) {
            return c.getLong(c.getColumnIndex(TOTAL_DISTANCE));
        }

        public static final String formattedTotalDistance(Cursor c) {
            return Utils.Number.shorDecimal(c.getLong(c.getColumnIndex(TOTAL_DISTANCE))/100D);
        }
        
        public static final Long altitude(Cursor c) {
            return c.getLong(c.getColumnIndex(ALTITUDE));
        }
        
        public static final Long accuracy(Cursor c) {
            return c.getLong(c.getColumnIndex(ACCURACY));
        }
        
        public static final Long speed(Cursor c) {
            return c.getLong(c.getColumnIndex(SPEED));
        }
        
        public static final String formattedSpeed(Cursor c) {
            return Utils.Number.shorDecimal(c.getLong(c.getColumnIndex(SPEED))/27777.778D);
        }
        
        public static final Long longitude(Cursor c) {
            return c.getLong(c.getColumnIndex(LONGITUDE));
        }
        
        public static final String formattedLongitude(Cursor c) {
            return Utils.Number.longDecimal(c.getLong(c.getColumnIndex(LONGITUDE))/1000000D);
        }

        public static final Long latitude(Cursor c) {
            return c.getLong(c.getColumnIndex(LATITUDE));
        }

        public static final String formattedLatitude(Cursor c) {
            return Utils.Number.longDecimal(c.getLong(c.getColumnIndex(LATITUDE))/1000000D);
        }
        
        public static final Long timestamp(Cursor c) {
            return c.getLong(c.getColumnIndex(TIMESTAMP));
        }   

        public static final Long time(Cursor c) {
            return c.getLong(c.getColumnIndex(TIME));
        }   
        
        public static final String fomatterTime(Cursor c) {
            return Utils.Date.time(c.getLong(c.getColumnIndex(TIME)));
        }   
        
        public static final Cursor all(ContentResolver cr) {
            return cr.query(CONTENT_URI, null, null, null, null);
        }

        private static final String[] OP1 = new String[]{LATITUDE, LONGITUDE};
        
        public static final Cursor get(Context c, String runId) {
            //Limit is a trick I should have it in the content provider 
            return c.getContentResolver().query(CONTENT_URI, OP1, RUN_ID + PARAMETER, new String[]{runId}, TIMESTAMP + DESCENDANT);
        }
        
        public static final Cursor getLast(Context c) {
            //Limit is a trick I should have it in the content provider 
            return c.getContentResolver().query(CONTENT_URI, null, null, null, TIMESTAMP + DESCENDANT + " LIMIT 1");
        }
        
    }
    
    public static class Profile extends Syncable {
        
        public static final String NAME = Profile.class.getSimpleName(); 
        
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);
        
        public static final int INCOMING_COLLECTION = 50;

        public static final int INCOMING_ITEM = 60;

        public static final String ITEM_TYPE = "vnd.android.cursor.item/vnd.runners.profile";

        public static final String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.runners.profile";
        
        public static final String SHOES = "shoes";
        
        public static final String WEIGHT = "weight";
        
        public static final String CREATED = "created";
        
        public static final String NICKNAME = "nickname";
        
        public static final String HEART_RATE = "heartRate";
        
        public static final String WEATHER = "weather";

        public static final String MODIFIED = "modified";
        
        
        public static final String convert(Context c) {
            try {
                JSONStringer js = new JSONStringer();
                js.array();
                js.object();
                String nickname = Preferences.getNickname(c);
                if(nickname != null) {
                    js.key(NICKNAME).value(nickname);
                }
                Boolean shoes = Preferences.getShoes(c);
                if(shoes != null) {
                    js.key(SHOES).value(shoes);
                }
                Boolean weight = Preferences.getWeight(c);
                if(weight != null) {
                    js.key(WEIGHT).value(weight);
                }
                Boolean heartRate = Preferences.getHeartRate(c);
                if(heartRate != null) {
                    js.key(HEART_RATE).value(heartRate);
                }
                Boolean weather = Preferences.getWeather(c);
                if(weather != null) {
                    js.key(WEATHER).value(weather);
                }
                Long modified = Preferences.getModified(c);
                if(modified != null) {
                    js.key(MODIFIED).value(modified);
                }
                Long created = Preferences.getCreated(c);
                if(created != null) {
                    js.key(CREATED).value(created);
                }
                js.endObject();
                js.endArray();
                return js.toString();
            } catch(JSONException e) {
                return "[]";
            }
        }
        
        public static Boolean shoes(Cursor c) {
            int shoes = c.getInt(c.getColumnIndex(SHOES));
            if(shoes == 1) {
                return Boolean.TRUE;
            } 
            return Boolean.FALSE;
        }
        
        public static Boolean weather(Cursor c) {
            int weather = c.getInt(c.getColumnIndex(WEATHER));
            if(weather == 1) {
                return Boolean.TRUE;
            } 
            return Boolean.FALSE;
        }
        
        public static Boolean heartRate(Cursor c) {
            int heartRate = c.getInt(c.getColumnIndex(HEART_RATE));
            if(heartRate == 1) {
                return Boolean.TRUE;
            } 
            return Boolean.FALSE;
        }
        
        public static Boolean weight(Cursor c) {
            int weight = c.getInt(c.getColumnIndex(WEIGHT));
            if(weight == 1) {
                return Boolean.TRUE;
            } 
            return Boolean.FALSE;
        }
        
        public static final String nickname(Cursor c) {
            return c.getString(c.getColumnIndex(NICKNAME));
        }
        
        public static final Cursor all(ContentResolver cr) {
            return cr.query(CONTENT_URI, null, null, null, null);
        }

        public static final Cursor notSync(Context c) {
            return c.getContentResolver().query(CONTENT_URI, null, REMOTE_ID + IS_NULL, null, null);
        }
    }

}

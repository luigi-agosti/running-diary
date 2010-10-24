package com.la.runners.util;

import android.location.Location;

public class Utils {

    public static class Geo {
        
        private static final double OEF_CONVERTION_TO_CENTIMETERS = 60 * 1.1515 * 160934.4;
        
        private static final double RAD2DEG_COEF = 180.0 / Math.PI;
        
        private static final double DEG2RAD_COEF = Math.PI / 180.0;
        
        private static final int MAX_LONGITUDE_DEGREE = 180;
        
        private static final int MAX_LATITUDE_DEGREE = 90;
        
        public static final double distance(double lat1, double lon1, double lat2, double lon2) {
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                    * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * OEF_CONVERTION_TO_CENTIMETERS;
            return dist;
        }
        
        private static final double deg2rad(double deg) {
            return (deg * DEG2RAD_COEF);
        }
    
        private static final double rad2deg(double rad) {
            return (rad * RAD2DEG_COEF);
        }
    
        public static final boolean isValidLocation(Location l) {
            return l != null && Math.abs(l.getLatitude()) <= MAX_LATITUDE_DEGREE
                    && Math.abs(l.getLongitude()) <= MAX_LONGITUDE_DEGREE;
        }
    
    }
    
    public static class Date {
        
        public static final int year(long millisec) {
            
            return 0;
        }
        
        public static final int month(long millisec) {
            
            return 0;
        }
        
        public static final int day(long millisec) {
            
            return 0;
        }

        public static String dayTime(long time) {
            // TODO Auto-generated method stub
            return null;
        }

    }
    
    public static class Number {
        
        private static final double MULTIPLIER = 1000000D;
        
        public static final long e6(double number) {
            return (long)(number * MULTIPLIER);
        }
    }
    
}

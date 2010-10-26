package com.la.runners.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

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
        
        private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("hh:mm a dd MMM yyyy");
        private static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");
        static {
            TIME_FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT+0"));  
        }
        
        private static final SimpleDateFormat HOUR_FORMATTER = new SimpleDateFormat("HH");
        private static final SimpleDateFormat DAY_FORMATTER = new SimpleDateFormat("dd");
        private static final SimpleDateFormat MONTH_FORMATTER = new SimpleDateFormat("MM");
        private static final SimpleDateFormat YEAR_FORMATTER = new SimpleDateFormat("yyyy");
        private static final SimpleDateFormat MINUTES_FORMATTER = new SimpleDateFormat("mm");
        
        public static final int year(long millisec) {
            return Integer.valueOf(YEAR_FORMATTER.format(new java.util.Date(millisec)));
        }
        
        public static final int month(long millisec) {
            return Integer.valueOf(MONTH_FORMATTER.format(new java.util.Date(millisec)));
        }
        
        public static final int day(long millisec) {
            return Integer.valueOf(DAY_FORMATTER.format(new java.util.Date(millisec)));
        }

        public static final int hour(long millisec) {
            return Integer.valueOf(HOUR_FORMATTER.format(new java.util.Date(millisec)));
        }
        
        public static final int minutes(long millisec) {
            return Integer.valueOf(MINUTES_FORMATTER.format(new java.util.Date(millisec)));
        }
        
        public static final String time(long millisec) {
            return TIME_FORMATTER.format(new java.util.Date(millisec));
        }

        public static String dayTime(long time) {
            
            return null;
        }

    }
    
    public static class Number {
        
        private static final DecimalFormat SHORT_DECIMAL_FORMATTER = new DecimalFormat("#.##");
        private static final DecimalFormat LONG_DECIMAL_FORMATTER = new DecimalFormat("##.######");
        
        private static final double MULTIPLIER = 1000000D;
        
        public static final long e6(double number) {
            return (long)(number * MULTIPLIER);
        }
        
        public static final String shorDecimal(double number) {
            return SHORT_DECIMAL_FORMATTER.format(number);
        }
        
        public static final String longDecimal(double number) {
            return LONG_DECIMAL_FORMATTER.format(number); 
        }
    }
    
}

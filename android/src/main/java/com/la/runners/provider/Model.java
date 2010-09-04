package com.la.runners.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.text.TextUtils;

public class Model {
    
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("hh:mm a dd MMM yyyy");
    
    public static class Article {
        
        public static final String title(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.TITLE));
        }
        
        public static String id(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.ID));
        }

        public static String articleId(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.ARTICLE_ID));
        }

        public static String authors(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.AUTHORS));
        }

        public static String text(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.TEXT));
        }

        public static String modifiedDate(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.MODIFIED_DATE));
        }

        public static String caption(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.DESCRIPTION));
        }

        public static String formattedModifiedDate(Cursor c) {
            Date date = new Date(c.getLong(c.getColumnIndex(MetaData.Article.Columns.MODIFIED_DATE)));
            return DATE_FORMATTER.format(date);
        }

        public static String shortUrl(Cursor c) {
            return c.getString(c.getColumnIndex(MetaData.Article.Columns.SHORT_URL));
        }

        public static List<String> relatedArticles(Cursor c) {
            String related = c.getString(c.getColumnIndex(MetaData.Article.Columns.RELATED_ARTICLES));
            if(TextUtils.isEmpty(related)) {
                return new ArrayList<String>();                
            }
            return Arrays.asList(related.split(MetaData.Article.RELATED_ARTICLE_SEPARATOR));
        }
        
    }

}

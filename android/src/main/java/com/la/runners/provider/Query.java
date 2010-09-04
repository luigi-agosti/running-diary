
package com.la.runners.provider;

import android.content.ContentResolver;
import android.database.Cursor;

/**
 * @author luigi.agosti
 */
public class Query {

    public static final String PARAMETER = "= ?";

    private static final String ASCENDANT = " asc";

    private static final String DESCENDANT = " desc";

    private static final String IS_NULL = " is null";

    private static final String AND = " and ";

    /**
     * All the query for Articles.
     * 
     * @author luigi.agosti
     */
    public static class Article {

        // TODO add optimized version for articleList

        /**
         * Select * from articles where channel_short_name = ? order by _id asc;
         * 
         * @param contentResolver
         * @param channelShortName
         * @return Cursor with the list of articles
         */
        public static final Cursor whereChannelShortNameIs(ContentResolver contentResolver,
                String channelShortName) {
            return whereChannelShortNameIs(contentResolver, channelShortName, null,
                    MetaData.Article.Columns.ID + ASCENDANT);
        }

        private static final Cursor whereChannelShortNameIs(ContentResolver contentResolver,
                String channelShortName, String[] projection, String order) {
            return contentResolver.query(MetaData.Article.CONTENT_URI, projection,
                    MetaData.Article.Columns.CHANNEL_SHORT_NAME + PARAMETER, new String[] {
                        channelShortName
                    }, order);
        }

        /**
         * Select * from articles where channel_short_name = ? order by mDate;
         * 
         * @param contentResolver
         * @param channelShortName
         * @return Cursor with the list of articles
         */
        public final static Cursor whereChannelShortNameIsOrderByModifiedDate(
                ContentResolver contentResolver, String channelShortName) {
            return whereChannelShortNameIs(contentResolver, channelShortName, null,
                    MetaData.Article.Columns.MODIFIED_DATE + DESCENDANT);
        }
    }

}

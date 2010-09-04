
package com.la.runners.provider;

import android.net.Uri;

/**
 * Just a container to every constant describing the model.
 * 
 * @author luigi.agosti
 */
public interface MetaData {

    interface Intent {

        String GET_ARTICLES = "uk.co.and.dailymail.intent.GetArticles";

    }

    String SEPARATOR = "/";

    String CONTENT_PREFIX = "content://";

    String AUTHORITY = "uk.co.and.dailymail";

    String ID_INDICATOR = "#";

    String DATABASE_NAME = "uk.co.and.dailymail.db";

    int DATABASE_VERSION = 25;

    interface BaseColumns {
        /**
         * I'm using the column defined in the android core so if they changed
         * it we are fine.
         */
        String ID = android.provider.BaseColumns._ID;
    }

    interface FileColumns extends BaseColumns {
        /**
         * Unfortunately there isn't one define ... I think!
         */
        String DATA = "_data";

        String URL = "url";
    }

    /**
     * Channel
     */
    interface Channel {

        String NAME = "channels";

        Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);

        String ITEM_TYPE = "vnd.android.cursor.item/vnd.dailymail.channel";

        String DIR_TYPE = "vnd.android.cursor.dir/vnd.dailymail.channel";

        int INCOMING_COLLECTION = 10;

        int INCOMING_ITEM = 20;

        interface Columns extends BaseColumns {

            String TITLE = "title";

            String SHORT_NAME = "col";

            String IMAGE_TYPES = "image_types";

            String SYNC_DATE = "sync_last";

            String DEFAULT_SYNC_ENABLED = "default_sync_enabled";

            String USER_SYNC_ENABLED = "user_sync_enabled";

            String SYNC_STATUS = "sync_status";

            String ORDERING = "ordering";

        }

        interface Status {

            String STARTED = "started";

            String FINISHED = "done";

            String FAILD = "faild";

            String DISABLED = "disabled";

        }

        interface Statements {
            String CREATE_STATEMENT = "create table " + NAME + " (" + Columns.ID
                    + " integer primary key autoincrement, " + Columns.ORDERING
                    + " integer not null," + Columns.TITLE + " text not null, "
                    + Columns.DEFAULT_SYNC_ENABLED + " integer not null,"
                    + Columns.USER_SYNC_ENABLED + " integer, " + Columns.SYNC_DATE + " timestamp,"
                    + Columns.SYNC_STATUS + " text not null," + Columns.SHORT_NAME
                    + " text not null, " + Columns.IMAGE_TYPES + " text);";

            String DROP_STATEMENT = "drop table if exists " + NAME;
        }
    }

    /**
     * Article
     */
    interface Article {

        String RELATED_ARTICLE_CHANNEL_NAME = "related_articles";
        
        String RELATED_ARTICLE_SEPARATOR = "@D@";
        
        String NAME = "articles";

        Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);

        String ITEM_TYPE = "vnd.android.cursor.item/vnd.dailymail.article";

        String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.dailymail.article";

        int INCOMING_COLLECTION = 30;

        int INCOMING_ITEM = 40;

        interface Columns extends BaseColumns {

            String ARTICLE_ID = "article_id";

            String CHANNEL_SHORT_NAME = "channel_short_name";

            String TITLE = "title";

            String HEADLINE = "h1";

            String DESCRIPTION = "desc";

            String URL = "aUrl";

            String TEXT = "aTxt";

            String SHORT_URL = "sUrl";

            String AUTHORS = "authors"; // comma separated list

            String MODIFIED_DATE = "mDate";
            
            String RELATED_ARTICLES = "related_articles";

        }

        interface Statements {
            String CREATE_STATEMENT = "create table " + NAME + " (" + Columns.ID
                    + " integer primary key autoincrement, " + Columns.ARTICLE_ID
                    + " integer not null, " + Columns.CHANNEL_SHORT_NAME + " text not null, "
                    + Columns.TITLE + " text not null, " + Columns.HEADLINE + " text,"
                    + Columns.DESCRIPTION + " text not null," + Columns.URL + " text not null,"
                    + Columns.TEXT + " text not null," + Columns.SHORT_URL + " text,"
                    + Columns.RELATED_ARTICLES + " text," + Columns.AUTHORS + " text," 
                    + Columns.MODIFIED_DATE + " timestamp not null"
                    + ");";

            String DROP_STATEMENT = "drop table if exists " + NAME;

            String CREATE_CLEAN_TRIGGER = "create trigger clean_" + NAME + " after delete on "
                    + Channel.NAME + " begin delete from " + NAME + " where "
                    + Columns.CHANNEL_SHORT_NAME + " = old." + Channel.Columns.SHORT_NAME
                    + "; end;";

            String DROP_CLEAN_TRIGGER = "drop trigger if exists clean_" + NAME + ";";
        }
    }

    /**
     * Thumbnails are the images related to a specific article.
     */
    interface ArticleThumbnail {

        interface Types {
            String TN = "tn";

            String PF = "pf";

            String LG = "lg";

            String BY = "by";
        }

        String NAME = "articlethumbnails";

        Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);

        String ITEM_TYPE = "vnd.android.cursor.item/vnd.dailymail.articlethumbnail";

        String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.dailymail.articlethumbnail";

        int INCOMING_COLLECTION = 50;

        int INCOMING_ITEM = 60;

        int INCOMING_ITEM_TYPE = 10;

        interface Columns extends FileColumns {

            String ARTICLE_ID = "article_id";

            String TYPE = "type";

            String CHANNEL_SHORT_NAME = "channel_short_name";

        }

        interface Projections {
            String[] ID_DATA = new String[] {
                    MetaData.ArticleThumbnail.Columns.ID, MetaData.ArticleThumbnail.Columns.DATA
            };
        }

        interface Statements {
            String CREATE_STATEMENT = "create table " + NAME + " (" + Columns.ID
                    + " integer primary key autoincrement, " + Columns.ARTICLE_ID
                    + " integer not null, " + Columns.CHANNEL_SHORT_NAME + " text not null, "
                    + Columns.URL + " text not null, " + Columns.TYPE + " text not null, "
                    + Columns.DATA + " text);";

            String DROP_STATEMENT = "drop table if exists " + NAME + ";";

            String CREATE_CLEAN_TRIGGER = "create trigger clean_" + NAME + " after delete on "
                    + Article.NAME + " begin delete from " + NAME + " where " + Columns.ARTICLE_ID
                    + " = old." + Columns.ID + "; end;";

            String DROP_CLEAN_TRIGGER = "drop trigger if exists clean_" + NAME + ";";

            String CREATE_INDEX_1 = "create unique index if not exists " + NAME + "_index on "
                    + NAME + " ( " + Columns.ARTICLE_ID + "," + Columns.TYPE + " );";

            String DROP_INDEX_1 = "drop index if exists " + NAME + "_index;";
        }

    }

    /**
     * Image represent one of the item of the gallery associated with the
     * article
     */
    interface ArticleImage {

        String NAME = "articleimages";

        Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);

        String ITEM_TYPE = "vnd.android.cursor.item/vnd.dailymail.articleimage";

        String COLLECTION_TYPE = "vnd.android.cursor.dir/vnd.dailymail.articleimage";

        int INCOMING_COLLECTION = 70;

        int INCOMING_ITEM = 80;

        interface Columns extends FileColumns {

            String ARTICLE_ID = "article_id";

            String CAPTION = "cp";

            String SIZE = "sz";

            String CHANNEL_SHORT_NAME = "channel_short_name";

        }

        interface Projections {
            String[] ID_DATA = new String[] {
                    MetaData.ArticleImage.Columns.ID, MetaData.ArticleImage.Columns.DATA
            };
        }

        interface Statements {

            String CREATE_STATEMENT = "create table " + NAME + " (" + Columns.ID
                    + " integer primary key autoincrement, " + Columns.CHANNEL_SHORT_NAME
                    + " text not null, " + Columns.ARTICLE_ID + " integer not null, " + Columns.URL
                    + " text not null, " + Columns.CAPTION + " text, " + Columns.SIZE
                    + " text not null, " + Columns.DATA + " text );";

            String DROP_STATEMENT = "drop table if exists " + NAME;

            String CREATE_CLEAN_TRIGGER = "create trigger clean_" + NAME + " after delete on "
                    + Article.NAME + " begin delete from " + NAME + " where " + Columns.ARTICLE_ID
                    + " = old." + Columns.ID + "; end;";

            String DROP_CLEAN_TRIGGER = "drop trigger if exists clean_" + NAME + ";";

            /**
             * Change this is case the name of the trigger or the table has
             * changed so in this way updates can clean old stuff
             */
            String DROP_OLD_STUFF = "drop table if exists galleryimages; drop trigger if exists clean_gallery_images;";
        }

    }

    interface SearchableArticle {
        String NAME = "articleSearch";

        Uri CONTENT_URI = Uri.parse(CONTENT_PREFIX + AUTHORITY + "/" + NAME);

        int INCOMING_ITEM = 90;

        interface Columns {
            String ARTICLE_ID = "_id";

            String DESCRIPTION = "desc";

            String TITLE = "title";

            String CHANNEL = "channel";
        }

        interface Statements {
            String CREATE_STATEMENT = "create virtual table " + NAME + " USING fts3("
                    + Columns.ARTICLE_ID + " INTEGER NOT NULL," + Columns.TITLE + " TEXT,"
                    + Columns.CHANNEL + " TEXT," + Columns.DESCRIPTION + " TEXT);";

            String DROP_STATEMENT = "drop table if exists " + NAME + ";";

            // TODO not working because of
            // http://www.sqlite.org/c3ref/last_insert_rowid.html
            String CREATE_INSERT_TRIGGER = "create trigger insert_" + NAME + " after insert on "
                    + Article.NAME + " begin insert into " + NAME + "(" + Columns.ARTICLE_ID + ","
                    + Columns.DESCRIPTION + ") values(new." + Columns.ARTICLE_ID + "," + "new."
                    + Columns.DESCRIPTION + ")" + "; end;";

            String CREATE_CLEAN_TRIGGER = "create trigger delete_" + NAME + " after delete on "
                    + Article.NAME + " begin delete from " + NAME + " where " + Columns.ARTICLE_ID
                    + " = old." + Columns.ARTICLE_ID + "; end;";

            String DROP_INSERT_TRIGGER = "drop trigger if exists insert_" + NAME + ";";

            String DROP_CLEAN_TRIGGER = "drop trigger if exists delete_" + NAME + ";";
        }
    }

}

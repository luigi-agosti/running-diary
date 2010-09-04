
package com.la.runners.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.la.runners.util.AppLogger;
import com.la.runners.util.network.NetworkService;

/**
 * @author luigi.agosti
 */
public class Provider extends ContentProvider {

    private static final UriManager urlMatcher = new UriManager();

    private DatabaseManager databaseManager;

    private SQLiteDatabase database;

    /**
     * Can delete only channel the consistency of the data is held by triggers.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowAffected = 0;
        switch (urlMatcher.match(uri)) {
            case MetaData.Channel.INCOMING_COLLECTION: {
                rowAffected = database.delete(MetaData.Channel.NAME, selection, selectionArgs);
                break;
            }
            case MetaData.Channel.INCOMING_ITEM: {
                rowAffected = database.delete(MetaData.Channel.NAME, MetaData.BaseColumns.ID + "="
                        + uri.getPathSegments().get(1), null);
                break;
            }
            case MetaData.Article.INCOMING_COLLECTION: {
                rowAffected = database.delete(MetaData.Article.NAME, selection, selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return rowAffected;
    }

    @Override
    public String getType(Uri uri) {
        String type = null;
        switch (urlMatcher.match(uri)) {
            case MetaData.Article.INCOMING_COLLECTION: {
                type = MetaData.Article.COLLECTION_TYPE;
                break;
            }
            case MetaData.Article.INCOMING_ITEM: {
                type = MetaData.Article.ITEM_TYPE;
                break;
            }
            case MetaData.Channel.INCOMING_COLLECTION: {
                type = MetaData.Channel.DIR_TYPE;
                break;
            }
            case MetaData.Channel.INCOMING_ITEM: {
                type = MetaData.Channel.ITEM_TYPE;
                break;
            }
            case MetaData.ArticleThumbnail.INCOMING_COLLECTION: {
                type = MetaData.ArticleThumbnail.COLLECTION_TYPE;
                break;
            }
            case MetaData.ArticleThumbnail.INCOMING_ITEM: {
                type = MetaData.ArticleThumbnail.ITEM_TYPE;
                break;
            }
            case MetaData.ArticleImage.INCOMING_COLLECTION: {
                type = MetaData.ArticleImage.COLLECTION_TYPE;
                break;
            }
            case MetaData.ArticleImage.INCOMING_ITEM:
                type = MetaData.ArticleImage.ITEM_TYPE;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;
        switch (urlMatcher.match(uri)) {
            case MetaData.Channel.INCOMING_COLLECTION: {
                try {
                    long channelId = database.insertOrThrow(MetaData.Channel.NAME, null, values);
                    result = ContentUris.withAppendedId(MetaData.Channel.CONTENT_URI, channelId);
                    getContext().getContentResolver().notifyChange(result, null);
                } catch (SQLException e) {
                    if (AppLogger.isErrorEnabled()) {
                        AppLogger.error("===========================================");
                        AppLogger.error(e);
                        AppLogger.error("Problem inserting channel with values : ");
                        AppLogger.error(MetaData.Channel.Columns.TITLE + " : "
                                + values.get(MetaData.Channel.Columns.TITLE));
                        AppLogger.error(MetaData.Channel.Columns.SHORT_NAME + " : "
                                + values.get(MetaData.Channel.Columns.SHORT_NAME));
                        AppLogger.error(MetaData.Channel.Columns.DEFAULT_SYNC_ENABLED + " : "
                                + values.get(MetaData.Channel.Columns.DEFAULT_SYNC_ENABLED));
                        AppLogger.error(MetaData.Channel.Columns.SYNC_STATUS + " : "
                                + values.get(MetaData.Channel.Columns.SYNC_STATUS));
                        AppLogger.error(MetaData.Channel.Columns.SYNC_DATE + " : "
                                + values.get(MetaData.Channel.Columns.SYNC_DATE));
                        AppLogger.error("===========================================");
                    }
                }
                break;
            }
            case MetaData.Article.INCOMING_COLLECTION: {
                try {
                    long articleId = database.insertOrThrow(MetaData.Article.NAME, null, values);
                    result = ContentUris.withAppendedId(MetaData.Article.CONTENT_URI, articleId);

                    ContentValues v = new ContentValues(3);
                    v.put(MetaData.SearchableArticle.Columns.ARTICLE_ID, articleId);
                    v.put(MetaData.SearchableArticle.Columns.TITLE, values
                            .getAsString((MetaData.Article.Columns.TITLE)));
                    v.put(MetaData.SearchableArticle.Columns.DESCRIPTION, values
                            .getAsString((MetaData.Article.Columns.DESCRIPTION)));
                    v.put(MetaData.SearchableArticle.Columns.CHANNEL, values
                            .getAsString((MetaData.Article.Columns.CHANNEL_SHORT_NAME)));
                    database.insertOrThrow(MetaData.SearchableArticle.NAME, "", v);
                    getContext().getContentResolver().notifyChange(result, null);
                } catch (SQLException e) {
                    if (AppLogger.isErrorEnabled()) {
                        AppLogger.error("===========================================");
                        AppLogger.error(e);
                        AppLogger.error("Problem inserting article with values : ");
                        AppLogger.error(MetaData.Article.Columns.AUTHORS + " : "
                                + values.get(MetaData.Article.Columns.AUTHORS));
                        AppLogger.error(MetaData.Article.Columns.CHANNEL_SHORT_NAME + " : "
                                + values.get(MetaData.Article.Columns.CHANNEL_SHORT_NAME));
                        AppLogger.error(MetaData.Article.Columns.DESCRIPTION + " : "
                                + values.get(MetaData.Article.Columns.DESCRIPTION));
                        AppLogger.error(MetaData.Article.Columns.HEADLINE + " : "
                                + values.get(MetaData.Article.Columns.HEADLINE));
                        AppLogger.error(MetaData.Article.Columns.MODIFIED_DATE + " : "
                                + values.get(MetaData.Article.Columns.MODIFIED_DATE));
                        AppLogger.error(MetaData.Article.Columns.SHORT_URL + " : "
                                + values.get(MetaData.Article.Columns.SHORT_URL));
                        AppLogger.error(MetaData.Article.Columns.TEXT + " : "
                                + values.get(MetaData.Article.Columns.TEXT));
                        AppLogger.error(MetaData.Article.Columns.TITLE + " : "
                                + values.get(MetaData.Article.Columns.TITLE));
                        AppLogger.error(MetaData.Article.Columns.URL + " : "
                                + values.get(MetaData.Article.Columns.URL));
                        AppLogger.error(MetaData.Article.Columns.ARTICLE_ID + " : "
                                + values.get(MetaData.Article.Columns.ARTICLE_ID));
                        AppLogger.error("===========================================");
                    }
                }
                break;
            }
            case MetaData.ArticleThumbnail.INCOMING_COLLECTION: {
                try {
                    long imageId = database.insertOrThrow(MetaData.ArticleThumbnail.NAME, null,
                            values);
                    result = ContentUris.withAppendedId(uri, imageId);
                    getContext().getContentResolver().notifyChange(result, null);
                } catch (SQLException e) {
                    if (AppLogger.isErrorEnabled()) {
                        AppLogger.error("===========================================");
                        AppLogger.error(e);
                        AppLogger.error("Problem inserting thumbnail image with values : ");
                        AppLogger.error(MetaData.ArticleThumbnail.Columns.ARTICLE_ID + " : "
                                + values.get(MetaData.ArticleThumbnail.Columns.ARTICLE_ID));
                        AppLogger.error(MetaData.ArticleThumbnail.Columns.TYPE + " : "
                                + values.get(MetaData.ArticleThumbnail.Columns.TYPE));
                        AppLogger.error(MetaData.ArticleThumbnail.Columns.CHANNEL_SHORT_NAME
                                + " : "
                                + values.get(MetaData.ArticleThumbnail.Columns.CHANNEL_SHORT_NAME));
                        AppLogger.error(MetaData.ArticleThumbnail.Columns.URL + " : "
                                + values.get(MetaData.ArticleThumbnail.Columns.URL));
                        AppLogger.error("===========================================");
                    }
                }
                break;
            }
            case MetaData.ArticleImage.INCOMING_COLLECTION: {
                try {
                    long galeryImageId = database.insertOrThrow(MetaData.ArticleImage.NAME, null,
                            values);
                    result = ContentUris.withAppendedId(uri, galeryImageId);
                    getContext().getContentResolver().notifyChange(result, null);
                } catch (SQLException e) {
                    if (AppLogger.isErrorEnabled()) {
                        AppLogger.error("===========================================");
                        AppLogger.error(e);
                        AppLogger.error("Problem inserting article image image with values : ");
                        AppLogger.error(MetaData.ArticleImage.Columns.ARTICLE_ID + " : "
                                + values.get(MetaData.ArticleImage.Columns.ARTICLE_ID));
                        AppLogger.error(MetaData.ArticleImage.Columns.CAPTION + " : "
                                + values.get(MetaData.ArticleImage.Columns.CAPTION));
                        AppLogger.error(MetaData.ArticleImage.Columns.URL + " : "
                                + values.get(MetaData.ArticleImage.Columns.URL));
                        AppLogger.error(MetaData.ArticleImage.Columns.SIZE + " : "
                                + values.get(MetaData.ArticleImage.Columns.SIZE));
                        AppLogger.error("===========================================");
                    }
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return result;
    }

    @Override
    public boolean onCreate() {
        if (AppLogger.isDebugEnabled()) {
            AppLogger.debug("creating DMA Provider");
        }
        databaseManager = new DatabaseManager(getContext(), NetworkService.getSchema(getContext()));
        database = databaseManager.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Cursor cursor;
        switch (urlMatcher.match(uri)) {
            case MetaData.Article.INCOMING_COLLECTION: {
                cursor = database.query(MetaData.Article.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case MetaData.Article.INCOMING_ITEM: {
                cursor = database.query(MetaData.Article.NAME, projection,
                        MetaData.Article.Columns.ID + Query.PARAMETER, UriManager
                                .getIdSelectionArgumentsFromUri(uri), null, null, sortOrder);
                break;
            }
            case MetaData.Channel.INCOMING_COLLECTION: {
                cursor = database.query(MetaData.Channel.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case MetaData.Channel.INCOMING_ITEM: {
                cursor = database.query(MetaData.Channel.NAME, projection,
                        MetaData.Channel.Columns.ID + Query.PARAMETER, UriManager
                                .getIdSelectionArgumentsFromUri(uri), null, null, sortOrder);
                break;
            }
            case MetaData.ArticleThumbnail.INCOMING_COLLECTION: {
                cursor = database.query(MetaData.ArticleThumbnail.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case MetaData.ArticleThumbnail.INCOMING_ITEM: {
                cursor = database.query(MetaData.ArticleThumbnail.NAME, projection,
                        MetaData.ArticleThumbnail.Columns.ID + Query.PARAMETER, UriManager
                                .getIdSelectionArgumentsFromUri(uri), null, null, sortOrder);
                break;
            }
            case MetaData.ArticleImage.INCOMING_COLLECTION: {
                cursor = database.query(MetaData.ArticleImage.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            }
            case MetaData.ArticleImage.INCOMING_ITEM: {
                cursor = database.query(MetaData.ArticleImage.NAME, projection,
                        MetaData.ArticleImage.Columns.ID + Query.PARAMETER, UriManager
                                .getIdSelectionArgumentsFromUri(uri), null, null, sortOrder);
                break;
            }
            case MetaData.SearchableArticle.INCOMING_ITEM: {
                // The search will always come as a where clause.
                // TODO could possible have content://<>/<>/<searchterm>
                cursor = database.query(true, MetaData.SearchableArticle.NAME, projection,
                        selection, selectionArgs, null, null, sortOrder, null);
                break;
            }
            default: {
                throw new IllegalArgumentException("The URI '" + uri + "' is not implemented.");
            }
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowUpdated = 0;
        switch (urlMatcher.match(uri)) {
            case MetaData.Channel.INCOMING_ITEM: {
                rowUpdated = database.update(MetaData.Channel.NAME, values,
                        MetaData.Channel.Columns.ID + Query.PARAMETER, UriManager
                                .getIdSelectionArgumentsFromUri(uri));
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            case MetaData.ArticleThumbnail.INCOMING_ITEM: {
                rowUpdated = database.update(MetaData.ArticleThumbnail.NAME, values,
                        MetaData.ArticleThumbnail.Columns.ID + Query.PARAMETER, UriManager
                                .getIdSelectionArgumentsFromUri(uri));
                Cursor c = null;
                try {
                    c = database.query(MetaData.ArticleThumbnail.NAME, null,
                            MetaData.ArticleThumbnail.Columns.ID + Query.PARAMETER, UriManager
                                    .getIdSelectionArgumentsFromUri(uri), null, null, null);
                    getContext().getContentResolver().notifyChange(uri, null);
                } catch (Exception e) {
                    AppLogger.error(e);
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
                break;
            }
            case MetaData.ArticleImage.INCOMING_ITEM: {
                rowUpdated = database.update(MetaData.ArticleImage.NAME, values,
                        MetaData.ArticleImage.Columns.ID + Query.PARAMETER, UriManager
                                .getIdSelectionArgumentsFromUri(uri));
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        return rowUpdated;
    }

}


package com.la.runners.provider;

import android.content.UriMatcher;
import android.net.Uri;

public class UriManager extends UriMatcher {

    public UriManager(int code) {
        super(code);
        setUp();
    }

    public UriManager() {
        super(UriMatcher.NO_MATCH);
        setUp();
    }

    public void setUp() {
        
        //TODO

        add(MetaData.Article.NAME, MetaData.Article.INCOMING_COLLECTION);
        add(MetaData.Article.NAME + MetaData.SEPARATOR + MetaData.ID_INDICATOR,
                MetaData.Article.INCOMING_ITEM);

    }

    public void add(String path, int code) {
        super.addURI(MetaData.AUTHORITY, path, code);
    }

    public static final String[] getIdSelectionArgumentsFromUri(Uri uri) {
        return new String[] {
            uri.getPathSegments().get(1)
        };
    }



}

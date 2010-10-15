
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
        add(Model.Run.NAME, Model.Run.INCOMING_COLLECTION);
        add(Model.Run.NAME + Model.SEPARATOR + Model.ID_INDICATOR,
                Model.Run.INCOMING_ITEM);
        add(Model.Location.NAME, Model.Location.INCOMING_COLLECTION);
        add(Model.Location.NAME + Model.SEPARATOR + Model.ID_INDICATOR,
                Model.Location.INCOMING_ITEM);
    }

    public void add(String path, int code) {
        super.addURI(Model.AUTHORITY, path, code);
    }

    public static final String[] getIdSelectionArgumentsFromUri(Uri uri) {
        return new String[] {
            uri.getPathSegments().get(1)
        };
    }
    
}

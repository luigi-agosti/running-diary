
package com.la.runners.provider;

import com.la.runners.provider.SyncProvider.Sync;

import android.content.UriMatcher;
import android.net.Uri;

public class SyncUriManager extends UriMatcher {

    public SyncUriManager(int code) {
        super(code);
        setUp();
    }

    public SyncUriManager() {
        super(UriMatcher.NO_MATCH);
        setUp();
    }

    public void setUp() {
        add(Sync.NAME, Sync.INCOMING_COLLECTION);
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

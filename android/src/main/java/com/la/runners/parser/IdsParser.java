package com.la.runners.parser;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

import com.la.runners.provider.SyncProvider.Sync;

public class IdsParser extends AbstractJsonParserIterator {

    public IdsParser(InputStream stream) {
        super(stream, null);
    }

    @Override
    public ContentValues next() {
        ContentValues cv = new ContentValues();
        JsonNode node = nextNode();
        if(node!=null) {
            cv.put(Sync.REMOTE_ID, node.get(Sync.REMOTE_ID).getLongValue());
            cv.put(Sync.ID, node.get(Sync.ID).getLongValue());
        }
        return cv;
    }

}

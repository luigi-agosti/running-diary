package com.la.runners.parser;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

import com.la.runners.provider.Model;

public class ProfileParser extends AbstractJsonParserIterator {

    public ProfileParser(InputStream stream) {
        super(stream, null);
    }

    @Override
    public ContentValues next() {
        ContentValues cv = new ContentValues();
        JsonNode node = nextNode();
        if(node!=null) {
            cv.put(Model.Profile.NICKNAME, node.get(Model.Profile.NICKNAME).getValueAsText());
            cv.put(Model.Profile.CREATED, node.get(Model.Profile.CREATED).getLongValue());
            cv.put(Model.Profile.MODIFIED, node.get(Model.Profile.MODIFIED).getLongValue());
            cv.put(Model.Profile.WEATHER, node.get(Model.Profile.WEATHER).getBooleanValue());
            cv.put(Model.Profile.WEIGHT, node.get(Model.Profile.WEIGHT).getBooleanValue());
            cv.put(Model.Profile.HEART_RATE, node.get(Model.Profile.HEART_RATE).getBooleanValue());
            cv.put(Model.Profile.SHOES, node.get(Model.Profile.SHOES).getValueAsText());
        }
        return cv;
    }

}

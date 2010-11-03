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
        if(node != null) {
            addValueAsText(Model.Profile.NICKNAME, cv, node);
            addLongValue(Model.Profile.CREATED, cv, node);
            addLongValue(Model.Profile.MODIFIED, cv, node);
            addBooleanValue(Model.Profile.WEATHER, cv, node);
            addBooleanValue(Model.Profile.WEIGHT, cv, node);
            addBooleanValue(Model.Profile.HEART_RATE, cv, node);
            addBooleanValue(Model.Profile.SHOES, cv, node);
        }
        return cv;
    }

}

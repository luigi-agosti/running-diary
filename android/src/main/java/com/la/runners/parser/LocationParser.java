package com.la.runners.parser;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

import com.la.runners.provider.Model;

public class LocationParser extends AbstractJsonParserIterator {

	public LocationParser(InputStream stream) throws ParserException {
		super(stream, null);
	}

	@Override
	public ContentValues next() {
		ContentValues cv = new ContentValues();
		JsonNode node = nextNode();
		if(node!=null) {
			cv.put(Model.Location.REMOTE_ID, node.get(Model.Location.REMOTE_ID).getValueAsText());
			cv.put(Model.Location.ACCURACY, node.get(Model.Location.ACCURACY).getLongValue());
			cv.put(Model.Location.ALTITUDE, node.get(Model.Location.ALTITUDE).getLongValue());
			cv.put(Model.Location.DISTANCE, node.get(Model.Location.DISTANCE).getLongValue());
			cv.put(Model.Location.LATITUDE, node.get(Model.Location.LATITUDE).getLongValue());
			cv.put(Model.Location.LONGITUDE, node.get(Model.Location.LONGITUDE).getLongValue());
			cv.put(Model.Location.SPEED, node.get(Model.Location.SPEED).getLongValue());
			cv.put(Model.Location.START, node.get(Model.Location.START).getLongValue());
			cv.put(Model.Location.TIME, node.get(Model.Location.TIME).getLongValue());
		}
		return cv;
	}
}

package com.la.runners.parser;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;

import com.la.runners.provider.Model;

import android.content.ContentValues;

public class RunParser extends AbstractJsonParserIterator {

	public RunParser(InputStream stream) {
		super(stream, null);
	}

	@Override
	public ContentValues next() {
		ContentValues cv = new ContentValues();
		JsonNode node = nextNode();
		if(node!=null) {
			cv.put(Model.Run.REMOTE_ID, node.get(Model.Run.REMOTE_ID).getValueAsText());
			cv.put(Model.Run.TIME, node.get(Model.Run.TIME).getLongValue());
			cv.put(Model.Run.DAY_TIME, node.get(Model.Run.DAY_TIME).getLongValue());
			cv.put(Model.Run.DATE, node.get(Model.Run.DATE).getLongValue());
			cv.put(Model.Run.YEAR, node.get(Model.Run.YEAR).getIntValue());
			cv.put(Model.Run.MONTH, node.get(Model.Run.MONTH).getIntValue());
			cv.put(Model.Run.DAY, node.get(Model.Run.DAY).getLongValue());
			cv.put(Model.Run.DISTANCE, node.get(Model.Run.DISTANCE).getValueAsText());
			String note = node.get(Model.Run.NOTE).getTextValue();
			if(note != null) {
			    cv.put(Model.Run.NOTE, note);
			}
			Boolean share = node.get(Model.Run.SHARE).getBooleanValue();
			if(share != null && share) { 
			    cv.put(Model.Run.SHARE, 1);
			} else {
			    cv.put(Model.Run.SHARE, 0);
			}
			cv.put(Model.Run.SHOES, node.get(Model.Run.SHOES).getValueAsText());
			cv.put(Model.Run.HEART_RATE, node.get(Model.Run.HEART_RATE).getLongValue());
			cv.put(Model.Run.WEIGHT, node.get(Model.Run.WEIGHT).getLongValue());
		}
		return cv;
	}

}

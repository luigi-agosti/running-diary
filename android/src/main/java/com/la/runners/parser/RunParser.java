package com.la.runners.parser;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;

import com.la.runners.provider.Model;

import android.content.ContentValues;

public class RunParser extends AbstractJsonParserIterator {

	public RunParser(InputStream stream) throws ParserException {
		super(stream, null);
	}

	@Override
	public ContentValues next() {
		ContentValues cv = new ContentValues();
		JsonNode node = nextNode();
		if(node!=null) {
			cv.put(Model.Run.ID, node.get(Model.Run.ID).getValueAsText());
			cv.put(Model.Run.TIME, node.get(Model.Run.TIME).getLongValue());
			cv.put(Model.Run.DATE, node.get(Model.Run.DATE).getLongValue());
			cv.put(Model.Run.DISTANCE, node.get(Model.Run.DISTANCE).getValueAsText());
			cv.put(Model.Run.NOTE, node.get(Model.Run.NOTE).getValueAsText());
		}
		return cv;
	}

}

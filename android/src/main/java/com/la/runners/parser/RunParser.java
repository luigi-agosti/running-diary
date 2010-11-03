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
		    addValueAsText(Model.Run.REMOTE_ID, cv, node);
		    addLongValue(Model.Run.TIME, cv, node);
		    addLongValue(Model.Run.CREATED, cv, node);
		    addLongValue(Model.Run.MODIFIED, cv, node);
		    addLongValue(Model.Run.START_DATE, cv, node);
		    addLongValue(Model.Run.END_DATE, cv, node);
		    
		    addLongValue(Model.Run.TIME, cv, node);
		    addIntValue(Model.Run.YEAR, cv, node);
		    addIntValue(Model.Run.MONTH, cv, node);
		    addLongValue(Model.Run.DAY, cv, node);
		    addLongValue(Model.Run.HOUR, cv, node);
		    addValueAsText(Model.Run.DISTANCE, cv, node);
		    addValueAsText(Model.Run.NOTE, cv, node);
		    addBooleanValueAsInt(Model.Run.SHARE, cv, node);
			
			addValueAsText(Model.Run.SHOES, cv, node);
			addLongValue(Model.Run.HEART_RATE, cv, node);
			addLongValue(Model.Run.WEIGHT, cv, node);
		}
		return cv;
	}
	
}

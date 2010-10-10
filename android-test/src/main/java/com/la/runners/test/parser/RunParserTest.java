package com.la.runners.test.parser;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import android.content.ContentValues;

import com.la.runners.parser.ParserException;
import com.la.runners.parser.RunParser;
import com.la.runners.provider.Model;

public class RunParserTest extends BaseParserTestCase {

    private RunParser parser;

    public void testParseRunsJson() throws ParserException, UnsupportedEncodingException {
        String json = "[{\"id\":1,\"time\":2,\"distance\":10,\"date\":1283633901348,\"note\":\"test\"}]";
        parser = new RunParser(new ByteArrayInputStream(json.getBytes()));
        assertNotNull(parser);
        if(parser.hasNext()) {
	        ContentValues cv = parser.next();
	        assertEquals("1", cv.get(Model.Run.ID));
	        assertEquals(Long.valueOf(2), cv.get(Model.Run.TIME));
	        assertEquals("10", cv.get(Model.Run.DISTANCE));
	        assertEquals(Long.valueOf("1283633901348"), cv.get(Model.Run.DATE));
	        assertEquals("test", cv.get(Model.Run.NOTE));
        } else {
        	fail();
        }
    }

}
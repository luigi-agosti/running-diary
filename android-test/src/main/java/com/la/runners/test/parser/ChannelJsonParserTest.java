
package com.la.runners.test.parser;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.la.runners.exception.ParserException;
import com.la.runners.parser.SchemaParser;

/**
 * @author luigi.agosti
 */
public class ChannelJsonParserTest extends BaseParserTestCase {

    private SchemaParser parser;

    public void testParseChannelsJson() throws ParserException, UnsupportedEncodingException {
        String json = "{\"name\":\"running-diary\",\"version\":\"1.344566489793798460\"," +
        	"\"dropStatements\":[\"drop table Run;\"]," +
        	"\"createStatements\":[\"create table Run(_id integer primary key autoincrement," +
        		"id integer,time integer,distance text,date integer,note text);\"]}";
        parser = new SchemaParser(new ByteArrayInputStream(json.getBytes()));
        assertEquals("running-diary", parser.getName());
        assertEquals(846, parser.getVersion());
        assertEquals(Arrays.asList("drop table Run;"), parser.getDropStms());
        assertEquals(Arrays.asList("create table Run(_id integer primary key autoincrement," +
        		"id integer,time integer,distance text,date integer,note text);"), parser.getCreateStms());
    }

}


package com.la.runners.test.parser;

import com.la.runners.parser.JsonParserIterator;

import android.content.ContentValues;
import android.test.AndroidTestCase;

/**
 * Just a base class to make the tests easier to implements.
 * 
 * TODO use Instrumentation Test case to access files ( getInstrumentation getContext ) 
 * 
 * @author luigi.agosti
 *
 */
public class BaseParserTestCase extends AndroidTestCase {

    public BaseParserTestCase() {
        super();
    }

    protected ContentValues assertNextRecordValue(String column, String expected, JsonParserIterator parser) {
        assertNotNull("The parser is null, can't procede!", parser);
        assertTrue("Expecting another element but the list is finished", parser.hasNext());
        ContentValues cv = parser.next();
        assertNotNull(cv);
        assertEquals(expected, cv.getAsString(column));
        return cv;
    }

    protected static final void assertCurrentRecordValue(String column, String expected, ContentValues cv) {
        assertNotNull(cv);
        assertEquals(expected, cv.getAsString(column));
    }

    protected static final void assertCurrentRecordValue(String column, Long expected, ContentValues cv) {
        assertNotNull(cv);
        assertEquals(expected, cv.getAsLong(column));
    }

    protected static final void assertCurrentRecordValue(String column, Integer expected, ContentValues cv) {
        assertNotNull(cv);
        assertEquals(expected, cv.getAsInteger(column));
    }

    protected static final void assertCurrentRecordValue(String column, int expected, ContentValues cv) {
        assertNotNull(cv);
        assertEquals(expected, cv.getAsInteger(column).intValue());
    }

    protected static final void assertCurrentRecordValueNull(String column, ContentValues cv) {
        assertNotNull(cv);
        assertNull(cv.getAsString(column));
    }
    
}

package com.la.runners.parser;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

/**
 * Jeneric parser to navigate through the nodes
 * 
 * @author luigi.agosti
 *
 */
public interface JsonParserIterator {

    boolean hasNext();

    ContentValues next();
    
    JsonNode getCurrentNode();
    
    int getPosition();

}

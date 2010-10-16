package com.la.runners.parser;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.la.runners.R;
import com.la.runners.exception.ParserException;

public class AuthCheckParser {

    private static final String STATUS = "status";
    
    private static final String YES = "yes";
    
    private boolean isLoggedIn = Boolean.FALSE;
    
    public AuthCheckParser(InputStream inputStream) {
        try {
            JsonNode rootNode = new ObjectMapper().readValue(inputStream, JsonNode.class);
            String status = rootNode.get(STATUS).getTextValue();
            if(YES.equals(status)) {
                isLoggedIn = Boolean.TRUE;
            } else {
                isLoggedIn = Boolean.FALSE;
            }
        } catch (Throwable e) {
            throw new ParserException(R.string.error_2, e.getMessage());
        }
    }
    
    public boolean isLoggerIn() {
        return isLoggedIn;
    }

}

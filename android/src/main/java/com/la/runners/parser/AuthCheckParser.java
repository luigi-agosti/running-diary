package com.la.runners.parser;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.la.runners.util.AppLogger;

public class AuthCheckParser {

    private static final String STATUS = "status";
    
    private static final String YES = "yes";
    
    private boolean isLoggedIn = false;
    
    public AuthCheckParser(InputStream inputStream) throws ParserException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readValue(inputStream, JsonNode.class);
            String status = rootNode.get(STATUS).getTextValue();
            if(YES.equals(status)) {
                isLoggedIn = true;
            } else {
                isLoggedIn = false;
            }
        } catch (Throwable e) {
            if (AppLogger.isErrorEnabled()) {
                AppLogger.error("Problem during parsing, probably network is donw!", e);
            }
            throw new ParserException("A problem occured during the parsing.");
        }
    }
    
    public boolean isLoggerIn() {
        return isLoggedIn;
    }

}

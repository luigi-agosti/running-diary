package com.la.runners.exception;

public class ParserException extends ResourceException {

    private static final long serialVersionUID = 1L;
    
    public ParserException() {
    }

    public ParserException(int resourceId, Object...objects) {
        super(resourceId, objects);
    }

}

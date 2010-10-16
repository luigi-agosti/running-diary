package com.la.runners.exception;

public class ParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private int resourceId;
    
    private Object[] objects;
    
    public ParserException() {
    }

    public ParserException(int resourceId, Object...objects) {
        this.resourceId = resourceId;
        this.objects = objects;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Object[] getObjects() {
        return objects;
    }

}

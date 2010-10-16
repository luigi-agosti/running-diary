package com.la.runners.exception;

public class ConnectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private int resourceId;
    
    private Object[] objects;
    
    public ConnectionException() {
    }

    public ConnectionException(int resourceId, Object...objects) {
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

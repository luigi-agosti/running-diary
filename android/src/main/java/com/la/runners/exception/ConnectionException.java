package com.la.runners.exception;

public class ConnectionException extends ResourceException {

    private static final long serialVersionUID = 1L;
    
    public ConnectionException() {
    }

    public ConnectionException(int resourceId, Object...objects) {
        super(resourceId, objects);
    }

}

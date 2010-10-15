package com.la.runners.client;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.la.runners.client.res.Strings;

public class Context implements Serializable {

    private static final long serialVersionUID = 1L;

    public Strings strings;
    
    private ServiceAsync service;
    
    private HandlerManager eventBus;
    
    public Context() {
        strings = GWT.create(Strings.class);
        service = GWT.create(Service.class);
        eventBus = new HandlerManager(null);
    }

    public ServiceAsync getService() {
        return service;
    }

    public HandlerManager getEventBus() {
        return eventBus;
    }

}

package com.la.runners.client;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.la.runners.client.res.Strings;
import com.la.runners.client.widget.form.field.converter.ConverterFactory;
import com.la.runners.client.widget.form.field.converter.UnitConverter;
import com.la.runners.shared.Profile;

public class Context implements Serializable {

    private static final long serialVersionUID = 1L;

    public Strings strings;
    
    private ServiceAsync service;
    
    private HandlerManager eventBus;
    
    private Profile profile;
    
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
    
    public Profile getProfile() {
        return profile;
    }
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public UnitConverter getUnitConverter() {
        return ConverterFactory.getUnitConvert(getProfile());
    }

}

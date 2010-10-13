package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ProfileUpdateEvent extends GwtEvent<ProfileUpdateHandler> {

    public static final GwtEvent.Type<ProfileUpdateHandler> TYPE = new GwtEvent.Type<ProfileUpdateHandler>();

    public ProfileUpdateEvent() {
    }
    
    @Override
    protected void dispatch(ProfileUpdateHandler handler) {
        handler.updateProfile(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ProfileUpdateHandler> getAssociatedType() {
        return TYPE;
    }

}

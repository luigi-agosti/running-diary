package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMapEvent extends GwtEvent<ShowMapHandler> {

    public static final GwtEvent.Type<ShowMapHandler> TYPE = new GwtEvent.Type<ShowMapHandler>();

    private Long id;
    
    public ShowMapEvent(Long id) {
        this.id = id;
    }

    @Override
    protected void dispatch(ShowMapHandler handler) {
        handler.showMap(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ShowMapHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getId() {
        return id;
    }
    
}

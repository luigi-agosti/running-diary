
package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoadRunEvent extends GwtEvent<LoadRunHandler> {

    public static final GwtEvent.Type<LoadRunHandler> TYPE = new GwtEvent.Type<LoadRunHandler>();

    private Long id;
    
    public LoadRunEvent(Long id) {
        this.id = id;
    }

    @Override
    protected void dispatch(LoadRunHandler handler) {
        handler.loadRun(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<LoadRunHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getId() {
        return id;
    }

}

package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RunCloneEvent extends GwtEvent<RunCloneHandler> {

    public static final GwtEvent.Type<RunCloneHandler> TYPE = new GwtEvent.Type<RunCloneHandler>();

    private Long id;
    
    public RunCloneEvent(Long id) {
        this.id = id;
    }

    @Override
    protected void dispatch(RunCloneHandler handler) {
        handler.clone(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RunCloneHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getId() {
        return id;
    }
    
}

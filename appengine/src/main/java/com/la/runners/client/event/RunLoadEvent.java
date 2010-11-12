
package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RunLoadEvent extends GwtEvent<RunLoadHandler> {

    public static final GwtEvent.Type<RunLoadHandler> TYPE = new GwtEvent.Type<RunLoadHandler>();

    private Long id;
    
    public RunLoadEvent(Long id) {
        this.id = id;
    }

    @Override
    protected void dispatch(RunLoadHandler handler) {
        handler.loadRun(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RunLoadHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getId() {
        return id;
    }

}

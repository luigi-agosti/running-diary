package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteRunEvent extends GwtEvent<DeleteRunHandler> {

    public static final GwtEvent.Type<DeleteRunHandler> TYPE = new GwtEvent.Type<DeleteRunHandler>();
    
    public DeleteRunEvent() { }

    @Override
    protected void dispatch(DeleteRunHandler handler) {
        handler.deleteRuns(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<DeleteRunHandler> getAssociatedType() {
        return TYPE;
    }

}


package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RunListUpdateEvent extends GwtEvent<RunListUpdateHandler> {

    public static final GwtEvent.Type<RunListUpdateHandler> TYPE = new GwtEvent.Type<RunListUpdateHandler>();

    private Integer month;

    private Integer year;

    public RunListUpdateEvent() {
    }

    public RunListUpdateEvent(Integer month, Integer year) {
        this.year = year;
        this.month = month;
    }

    @Override
    protected void dispatch(RunListUpdateHandler handler) {
        handler.updateRunList(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RunListUpdateHandler> getAssociatedType() {
        return TYPE;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }
}

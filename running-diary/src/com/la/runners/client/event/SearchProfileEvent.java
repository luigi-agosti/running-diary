package com.la.runners.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SearchProfileEvent extends GwtEvent<SearchProfileHandler> {

    public static final GwtEvent.Type<SearchProfileHandler> TYPE = new GwtEvent.Type<SearchProfileHandler>();

    private String filter;

    public SearchProfileEvent() {
    }

    public SearchProfileEvent(String filter) {
        this.filter = filter;
    }

    @Override
    protected void dispatch(SearchProfileHandler handler) {
        handler.search(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SearchProfileHandler> getAssociatedType() {
        return TYPE;
    }

    public String getFilter() {
        return filter;
    }
    
}

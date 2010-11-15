package com.la.runners.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.la.runners.shared.Location;

public class ShowMapEvent extends GwtEvent<ShowMapHandler> {

    public static final GwtEvent.Type<ShowMapHandler> TYPE = new GwtEvent.Type<ShowMapHandler>();

    private Long id;
    
    private Boolean editMode;
    
    private List<Location> locations;
    
    public ShowMapEvent(Long id) {
        this(id, Boolean.FALSE);
    }
    
    public ShowMapEvent(Long id, boolean editMode) {
        this.id = id;
        this.setEditMode(editMode);
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

	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	public Boolean getEditMode() {
		return editMode;
	}

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }
    
}

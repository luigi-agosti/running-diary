package com.la.runners.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LocationsUpdateHandler extends EventHandler {
    
    void update(LocationsUpdateEvent event);
    
}

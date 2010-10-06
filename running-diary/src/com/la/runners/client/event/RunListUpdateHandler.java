package com.la.runners.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RunListUpdateHandler extends EventHandler {
	
	void updateRunList(RunListUpdateEvent event);

	
}

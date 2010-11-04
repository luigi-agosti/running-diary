package com.la.runners.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DeleteRunHandler extends EventHandler {
	
	void deleteRuns(DeleteRunEvent event);

}

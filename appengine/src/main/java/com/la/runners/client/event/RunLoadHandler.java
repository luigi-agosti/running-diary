package com.la.runners.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RunLoadHandler extends EventHandler {
	
	void loadRun(RunLoadEvent event);

}

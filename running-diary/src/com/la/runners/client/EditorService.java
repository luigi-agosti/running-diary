package com.la.runners.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.la.runners.shared.Run;

@RemoteServiceRelativePath("editorService")
public interface EditorService extends RemoteService {

	void save(Run page);

	Run get(Long id);
	
}

package com.la.runners.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.la.runners.shared.Run;

public interface EditorServiceAsync {

	void save(Run page, AsyncCallback<Void> callback);

	void get(Long id, AsyncCallback<Run> callback);

    void search(AsyncCallback<List<Run>> callback);

}

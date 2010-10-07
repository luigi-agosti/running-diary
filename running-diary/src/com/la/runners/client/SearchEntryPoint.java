
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SearchEntryPoint implements EntryPoint {

    private final ServiceAsync editorService = GWT.create(Service.class);

    private static final String GWT_HOOK_ID = "gwtHook";

    @Override
    public void onModuleLoad() {
        RootPanel.get(GWT_HOOK_ID).add(new Label("Search"));
    }

}

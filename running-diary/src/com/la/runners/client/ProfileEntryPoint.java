
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.widget.form.ProfileEditor;
import com.la.runners.client.widget.grid.FollowersGrid;

public class ProfileEntryPoint implements EntryPoint {

    private final ServiceAsync editorService = GWT.create(Service.class);

    private static final String GWT_HOOK_ID = "gwtHook";

    @Override
    public void onModuleLoad() {
        FlowPanel panel = new FlowPanel();
        panel.add(new FollowersGrid(editorService));
        panel.add(new ProfileEditor(editorService));
        RootPanel.get(GWT_HOOK_ID).add(panel);
        panel.setStyleName("EditorEntryPoint");
        RootPanel.get(GWT_HOOK_ID).add(panel);
    }

}

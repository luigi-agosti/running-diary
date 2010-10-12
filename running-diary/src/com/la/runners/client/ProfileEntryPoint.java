
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.widget.form.ProfileEditor;
import com.la.runners.client.widget.grid.FollowersGrid;
import com.la.runners.client.widget.grid.InviteGrid;

public class ProfileEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        ServiceAsync editorService = GWT.create(Service.class);
        FlowPanel panel = new FlowPanel();
        panel.add(new InviteGrid(editorService));
        panel.add(new FollowersGrid(editorService));
        panel.add(new ProfileEditor(editorService));
        panel.setStyleName("EditorEntryPoint");
        RootPanel.get("gwtHook").add(panel);
    }

}

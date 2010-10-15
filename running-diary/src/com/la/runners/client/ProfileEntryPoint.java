
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.widget.form.ProfileForm;
import com.la.runners.client.widget.grid.FollowersGrid;
import com.la.runners.client.widget.grid.InviteGrid;

public class ProfileEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        Context context = new Context();
        FlowPanel panel = new FlowPanel();
        panel.add(new InviteGrid(context));
        panel.add(new FollowersGrid(context));
        panel.add(new ProfileForm(context));
        panel.setStyleName(Styles.Form.entryPoint);
        RootPanel.get(context.strings.gwtHook()).add(panel);
    }

}

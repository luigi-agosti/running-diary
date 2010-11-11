
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.res.ResourceBundle;
import com.la.runners.client.widget.form.ProfileForm;
import com.la.runners.client.widget.grid.FollowersGrid;
import com.la.runners.client.widget.grid.InviteGrid;
import com.la.runners.shared.Profile;

public class ProfileEntryPoint implements EntryPoint {

    private static final String GWT_HOOK = "gwtHook";
    
    private Context context;
    
    @Override
    public void onModuleLoad() {
        ResourceBundle.INSTANCE.form().ensureInjected();
        ResourceBundle.INSTANCE.map().ensureInjected();
        ResourceBundle.INSTANCE.grid().ensureInjected();
        context = new Context();
        context.getService().getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                //TODO Ummm
            }
            @Override
            public void onSuccess(Profile result) {
                context.setProfile(result);
                init();
            }
        });
    }
    
    private void init(){
        FlowPanel panel = new FlowPanel();
        panel.add(new InviteGrid(context));
        panel.add(new FollowersGrid(context));
        panel.add(new ProfileForm(context));
        panel.setStyleName(ResourceBundle.INSTANCE.form().entryPoint());
        RootPanel.get(GWT_HOOK).add(panel);
    }

}

package com.la.runners.client.widget.grid;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.la.runners.client.Context;
import com.la.runners.client.event.ProfileUpdateEvent;
import com.la.runners.client.event.ProfileUpdateHandler;
import com.la.runners.client.res.Resources;
import com.la.runners.shared.Invite;

public class InviteGrid extends BaseGrid implements ProfileUpdateHandler {
    
    public InviteGrid(Context context) {
        super(context);
        eventBus().addHandler(ProfileUpdateEvent.TYPE, this);
        setMainColorStyle(Resources.INSTANCE.grid().red());
        load();
    }
    
    @Override
    public void updateProfile(ProfileUpdateEvent event) {
        load();
    }
    
    private void load() {
        setVisible(false);
        service().getInvites(new AsyncCallback<List<Invite>>() {
            @Override
            public void onSuccess(List<Invite> result) {
                drawGrid(result);
            }
            @Override
            public void onFailure(Throwable caught) {

            }
        });
    }

    private void drawGrid(List<Invite> result) {
        grid.clear();
        if(result == null || result.isEmpty()) {
            setVisible(false);
        } else {
            setVisible(true);
            grid.setWidget(0,0, createLabel("Invite from nickname", Resources.INSTANCE.grid().headerCell()));
            grid.setWidget(0,1, createLabel("Accept", Resources.INSTANCE.grid().headerCell()));
            grid.setWidget(0,2, createLabel("Reject", Resources.INSTANCE.grid().headerCell()));
            int index = 1;
            for(Invite invite : result) {
                grid.setWidget(index,0, createLabel(invite.getSenderNickname(), Resources.INSTANCE.grid().grid()));
                final String token = invite.getToken();
                final int rowIndex = index;
                Button btnAccept = new Button("Accept");
                btnAccept.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        service().acceptInvite(token, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                showMessage("Umm, there was some problem");
                            }

                            @Override
                            public void onSuccess(Void result) {
                                showMessage("Success!");
                                grid.removeRow(rowIndex);
                                eventBus().fireEvent(new ProfileUpdateEvent());
                            }
                        });
                    }
                });
                grid.setWidget(index,1, btnAccept);
                Button btnReject = new Button("Reject");
                btnReject.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        service().rejectInvite(token, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                showMessage("Umm, there was some problem, try to refresh the browser");
                            }
                            @Override
                            public void onSuccess(Void result) {
                                showMessage("Success!");
                                grid.removeRow(rowIndex);
                                eventBus().fireEvent(new ProfileUpdateEvent());
                            }
                        });
                    }
                });
                grid.setWidget(index,2, btnReject);
                index++;
                showMessage("");
            }
        }
    }

}

package com.la.runners.client.widget.grid;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.la.runners.client.Context;
import com.la.runners.client.event.SearchProfileEvent;
import com.la.runners.client.event.SearchProfileHandler;
import com.la.runners.client.res.Resources;
import com.la.runners.shared.Profile;
import com.la.runners.shared.ServerException;

public class SearchGrid extends BaseGrid implements SearchProfileHandler {

    public SearchGrid(Context context) {
        super(context);
        context.getEventBus().addHandler(SearchProfileEvent.TYPE, this);
    }

    private void drawGrid(List<Profile> result) {
        grid.clear();
        if(result == null || result.isEmpty()) {
            showMessage("No data found, try to search for your friends");
        } else {
            grid.setWidget(0,0, createLabel("Nickname", Resources.INSTANCE.grid().headerCell()));
            grid.setWidget(0,1, createLabel("Add to your list", Resources.INSTANCE.grid().headerCell()));
            grid.setWidget(0,2, createLabel("Profile Page", Resources.INSTANCE.grid().headerCell()));
            int index = 1;
            for(Profile profile : result) {
                grid.setWidget(index,0, createLabel(profile.getNickname(), Resources.INSTANCE.grid().cell()));
                final String userId = profile.getUserId();
                Button btnProfile = new Button("See");
                btnProfile.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        showMessage("function not implemented yet");
                    }
                });
                grid.setWidget(index,1, btnProfile);
                
                Button btnInvite = new Button("Invite");
                btnInvite.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        try {
                            service().sendInvite(userId, new AsyncCallback<Void>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    showMessage("Umm, there was some problem");
                                }
                                @Override
                                public void onSuccess(Void result) {
                                    showMessage("Success!");
                                }
                            });
                        } catch (ServerException e) {
                            showMessage(e.getMessage());
                        }
                    }
                });
                grid.setWidget(index,2, btnInvite);
                index++;
            }
            showMessage("Results : " + --index);
        }
    }

    @Override
    public void search(SearchProfileEvent event) {
        showMessage("Loading...");
        service().searchProfile(event.getFilter(), new AsyncCallback<List<Profile>>() {
            @Override
            public void onSuccess(List<Profile> result) {
                drawGrid(result);
            }
            
            @Override
            public void onFailure(Throwable caught) {
                showMessage("There was an error while requesting data to the server");
            }
        });
    }
}

package com.la.runners.client.widget.grid;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.la.runners.client.Constants;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.event.SearchProfileEvent;
import com.la.runners.client.event.SearchProfileHandler;
import com.la.runners.shared.Profile;

public class SearchGrid extends BaseGrid implements SearchProfileHandler {

    public SearchGrid(HandlerManager eventBus, ServiceAsync service) {
        super(service);
        eventBus.addHandler(SearchProfileEvent.TYPE, this);
    }

    private void drawGrid(List<Profile> result) {
        grid.clear();
        if(result == null || result.isEmpty()) {
            showMessage("No result found");
        } else {
            grid.setWidget(0,0, createLabel("Nickname", Constants.Style.gridHeaderCell));
            grid.setWidget(0,1, createLabel("Add to your list", Constants.Style.gridHeaderCell));
            grid.setWidget(0,2, createLabel("Profile Page", Constants.Style.gridHeaderCell));
            int index = 1;
            for(Profile profile : result) {
                grid.setWidget(index,0, createLabel(profile.getNickname(), Constants.Style.gridCell));
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
                        service.sendInvite(userId, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                showMessage("Umm, there was some problem");
                            }
                            @Override
                            public void onSuccess(Void result) {
                                showMessage("Success!");
                            }
                        });
                    }
                });
                grid.setWidget(index,2, btnInvite);
                index++;
            }
            showMessage("");
        }
    }

    @Override
    public void search(SearchProfileEvent event) {
        showMessage("Loading...");
        service.searchProfile(event.getFilter(), new AsyncCallback<List<Profile>>() {
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

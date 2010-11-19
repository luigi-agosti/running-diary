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
import com.la.runners.shared.Profile;

public class FollowersGrid extends BaseGrid implements ProfileUpdateHandler {
    
    public static final String ID = "followersGrid";
    
    public FollowersGrid(Context context) {
        super(context, ID);
        eventBus().addHandler(ProfileUpdateEvent.TYPE, this);
        load();
    }
    
    @Override
    public void updateProfile(ProfileUpdateEvent event) {
        load();
    }
    
    private void load() {
        showMessage("Loading...");
        service().getFollowers(new AsyncCallback<List<Profile>>() {
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

    private void drawGrid(List<Profile> result) {
        grid.clear();
        if(result == null || result.isEmpty()) {
            showMessage("No friends linked to you yet");
        } else {
            grid.setWidget(0,0, createLabel("Nickname", Resources.INSTANCE.grid().headerCell()));
            grid.setWidget(0,1, createLabel("Remove", Resources.INSTANCE.grid().headerCell()));
            grid.setWidget(0,2, createLabel("Profile Page", Resources.INSTANCE.grid().headerCell()));
            int index = 1;
            for(Profile profile : result) {
                grid.setWidget(index,0, createLabel(profile.getNickname(), Resources.INSTANCE.grid().cell()));
                final String followerUserId = profile.getUserId();
                Button btnProfile = new Button("Profile Page");
                btnProfile.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        showMessage("function not implemented yet");
                    }
                });
                grid.setWidget(index,1, btnProfile);
                final int rowIndex = index;
                Button btnRemove = new Button("Remove");
                btnRemove.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        service().removeFollower(followerUserId, new AsyncCallback<Void>() {
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
                grid.setWidget(index,2, btnRemove);
                index++;
            }
            showMessage("You have " + --index + " friends");
        }
    }

}

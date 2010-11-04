package com.la.runners.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ProfileUpdateHandler extends EventHandler {
    
    void updateProfile(ProfileUpdateEvent event);

}

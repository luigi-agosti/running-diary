package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.event.ProfileUpdateEvent;
import com.la.runners.client.event.ProfileUpdateHandler;
import com.la.runners.shared.Profile;

public class ProfileEditor extends BaseForm implements ProfileUpdateHandler {

    private CheckBox heartRateInput;
    private CheckBox weightInput;
    private CheckBox shoesInput;
    private CheckBox weatherInput;
    private TextBox nicknameInput;
    
    private Profile profile;
    
    public ProfileEditor(final ServiceAsync service) {
        super("Profile Editor");
        nicknameInput = addTextBoxWithLabel("Nickname");
        addButton("Delete account", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.deleteProfile(new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage("Successful remove, please logout!");
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage("Umm, something is wrong!");
                    }
                });
            }
        });
        addSubtitle("Run settings");
        heartRateInput = addCheckBoxWithLabel("Heart Rate");
        weightInput = addCheckBoxWithLabel("Weight");
        weatherInput  = addCheckBoxWithLabel("Wheather");
        shoesInput = addCheckBoxWithLabel("Shoes");
        addButton("Save", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage("Successful saved!");
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage("Umm, something is wrong!");
                    }
                });
            }
        });
        
        service.getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                showMessage("Umm, something is wrong!");
            }
            @Override
            public void onSuccess(Profile result) {
                load(result);
            }
        });
        showMessage("");
    }
    
    public void load(Profile profile) {
        if (profile == null) {
            profile = new Profile();
        }
        this.profile = profile;
        setValue(nicknameInput, profile.getNickname());
        setValue(heartRateInput, profile.getHeartRate());
        setValue(weightInput, profile.getWeight());
        setValue(shoesInput, profile.getShoes());
        setValue(weatherInput, profile.getWeather());
    }
    
    public Profile get() {
        String nickname = nicknameInput.getText();
        if(nickname != null) {
            profile.setNickname(nickname);            
        }
        profile.setHeartRate(heartRateInput.getValue());
        profile.setWeight(weightInput.getValue());
        profile.setShoes(shoesInput.getValue());
        profile.setWeather(weatherInput.getValue());
        return profile;
    }

    @Override
    public void updateProfile(ProfileUpdateEvent event) {
        //TODO
    }
    
}

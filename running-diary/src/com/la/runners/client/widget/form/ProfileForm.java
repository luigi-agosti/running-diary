package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.Context;
import com.la.runners.client.event.ProfileUpdateEvent;
import com.la.runners.client.event.ProfileUpdateHandler;
import com.la.runners.shared.Profile;

public class ProfileForm extends BaseForm implements ProfileUpdateHandler {

    private CheckBox heartRateInput;
    private CheckBox weightInput;
    private CheckBox shoesInput;
    private CheckBox weatherInput;
    private TextBox nicknameInput;
    
    private Profile profile;
    
    public ProfileForm(Context _context) {
        super(_context, _context.strings.profileFormTitle());
        nicknameInput = addTextBoxWithLabel(strings().profileFormNickname());
        addButton(strings().profileFormDeleteAccountButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                context.getService().deleteProfile(new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage(strings().profileFormDeleteProfileSuccess());
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage(strings().profileFormDeleteProfileFailure());
                    }
                });
            }
        });
        addSubtitle(strings().profileFormSubtitle());
        heartRateInput = addCheckBoxWithLabel(strings().profileFormInputLabelHeartRate());
        weightInput = addCheckBoxWithLabel(strings().profileFormInputLabelWeight());
        weatherInput  = addCheckBoxWithLabel(strings().profileFormInputLabelWeather());
        shoesInput = addCheckBoxWithLabel(strings().profileFormInputLabelShoes());
        addButton(strings().profileFormSaveButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service().save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage(strings().profileFormSaveSuccess());
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage(strings().profileFormSaveFailure());
                    }
                });
            }
        });
        
        service().getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                showMessage(strings().profileFormLoadingProfileFailure());
            }
            @Override
            public void onSuccess(Profile result) {
                load(result);
            }
        });
        addFooterForMessages();
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

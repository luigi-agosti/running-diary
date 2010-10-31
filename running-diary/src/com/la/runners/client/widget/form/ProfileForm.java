package com.la.runners.client.widget.form;

import java.util.Arrays;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.la.runners.client.Context;
import com.la.runners.client.event.ProfileUpdateEvent;
import com.la.runners.client.event.ProfileUpdateHandler;
import com.la.runners.client.widget.dialog.DeleteProfileDialog;
import com.la.runners.client.widget.form.field.CheckBoxField;
import com.la.runners.client.widget.form.field.ListBoxField;
import com.la.runners.client.widget.form.field.TextBoxField;
import com.la.runners.shared.Profile;

public class ProfileForm extends CustomForm implements ProfileUpdateHandler {

    private CheckBoxField heartRateInput;
    private CheckBoxField weightInput;
    private CheckBoxField shoesInput;
    private CheckBoxField weatherInput;
    private TextBoxField nicknameInput;
    private ListBoxField unitSystemInput;
    
    private Profile profile;
    
    public ProfileForm(final Context _context) {
        super(_context, _context.strings.profileFormTitle());
        nicknameInput = addTextBoxField(strings().profileFormNickname());
        unitSystemInput = addListBoxField(strings().profileFormUnitSystem(), 
                Arrays.asList(strings().profileFormInternationalSystem(), 
                        strings().profileFormImperialSystem(), strings().profileFormUSSystem()));
        addSubtitle(strings().profileFormSubtitle());
        heartRateInput = addCheckBoxField(strings().profileFormInputLabelHeartRate());
        weightInput = addCheckBoxField(strings().profileFormInputLabelWeight());
        weatherInput  = addCheckBoxField(strings().profileFormInputLabelWeather());
        shoesInput = addCheckBoxField(strings().profileFormInputLabelShoes());
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
        addSubtitle(strings().profileFormSubtitlePrivacy());
        addButton(strings().profileFormDeleteAccountButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                DeleteProfileDialog dialogBox = new DeleteProfileDialog(_context);
                dialogBox.show();
            }
        });
        load(profile());
        addFooterForMessages();
    }
    
    public void load(Profile profile) {
        if (profile == null) {
            profile = new Profile();
        }
        this.profile = profile;
        nicknameInput.setValue(profile.getNickname());
        heartRateInput.setValue(profile.getHeartRate());
        weightInput.setValue(profile.getWeight());
        shoesInput.setValue(profile.getShoes());
        weatherInput.setValue(profile.getWeather());
        unitSystemInput.setValue(profile.getUnitSystem());
    }
    
    public Profile get() {
        profile.setNickname(nicknameInput.getValue());
        profile.setHeartRate(heartRateInput.getValue());
        profile.setWeight(weightInput.getValue());
        profile.setShoes(shoesInput.getValue());
        profile.setWeather(weatherInput.getValue());
        profile.setUnitSystem(unitSystemInput.getValue());
        return profile;
    }

    @Override
    public void updateProfile(ProfileUpdateEvent event) {
        //TODO
    }
    
}

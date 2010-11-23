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

    public static final String ID = "profileForm";
    public static final String SAVE_PROFILE_ID = "saveProfile";
    public static final String DELETE_PROFILE_ID = "deleteProfile";
    public static final String NICKNAME_ID = "nickname";
    public static final String UNIT_SYSTEM_ID = "unitSystemInput";
    public static final String HEART_RATE_ID = "heartRateInput";
    public static final String WEIGHT_ID = "weightInput";
    public static final String WEATHER_ID = "weatherInput";
    public static final String SHOES_ID = "shoesInput";
    
    private CheckBoxField heartRateInput;
    private CheckBoxField weightInput;
    private CheckBoxField shoesInput;
    private CheckBoxField weatherInput;
    private TextBoxField nicknameInput;
    private ListBoxField unitSystemInput;
    
    private Profile profile;
    
    public ProfileForm(final Context _context) {
        super(_context, _context.strings.profileFormTitle(), ID);
        nicknameInput = addTextBoxField(strings().profileFormNickname(), NICKNAME_ID);
        unitSystemInput = addListBoxField(strings().profileFormUnitSystem(), 
                Arrays.asList(strings().profileFormInternationalSystem(), 
                        strings().profileFormImperialSystem(), strings().profileFormUSSystem()), UNIT_SYSTEM_ID);
        addSubtitle(strings().profileFormSubtitle());
        heartRateInput = addCheckBoxField(strings().profileFormInputLabelHeartRate(), HEART_RATE_ID);
        weightInput = addCheckBoxField(strings().profileFormInputLabelWeight(), WEIGHT_ID);
        weatherInput  = addCheckBoxField(strings().profileFormInputLabelWeather(), WEATHER_ID);
        shoesInput = addCheckBoxField(strings().profileFormInputLabelShoes(), SHOES_ID);
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
        }, SAVE_PROFILE_ID);
        addSubtitle(strings().profileFormSubtitlePrivacy());
        addButton(strings().profileFormDeleteAccountButton(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                DeleteProfileDialog dialogBox = new DeleteProfileDialog(_context);
                dialogBox.center();
            }
        }, DELETE_PROFILE_ID);
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

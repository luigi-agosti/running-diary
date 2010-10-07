package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.Constants;
import com.la.runners.client.ServiceAsync;
import com.la.runners.shared.Profile;

public class ProfileEditor extends Composite {

    private static final Label TITLE = new Label("Profile Editor");
    private static final Label FIELD_SUBTITLE = new Label("Run settings");
    private static final Label HEART_RATE = new Label("Heart Rate");
    private static final Label SHOES = new Label("Shoes");
    private static final Label WEATHER = new Label("Wheather");
    private static final Label WEIGHT = new Label("Weight");
    private static final Label NICKNAME = new Label("Nickname");
    private static final Label DELETE_ACCOUNT = new Label("Delete account");
    private static final Label FOOTER = new Label(" ");
    
    static {
        HEART_RATE.setStyleName(Constants.Style.editorLabel);
        SHOES.setStyleName(Constants.Style.editorLabel);
        WEIGHT.setStyleName(Constants.Style.editorLabel);
        WEATHER.setStyleName(Constants.Style.editorLabel);
        DELETE_ACCOUNT.setStyleName(Constants.Style.editorLabel);
        NICKNAME.setStyleName(Constants.Style.editorLabel);
        FOOTER.setStylePrimaryName(Constants.Style.editorFooter);
        TITLE.setStylePrimaryName(Constants.Style.editorHeader);
        FIELD_SUBTITLE.setStylePrimaryName(Constants.Style.editorSubTitle);
    }
    
    private Button submit = new Button("Save"); {
        submit.setStyleName(Constants.Style.editorButton);
        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        FOOTER.setText("Successful saved!");
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        FOOTER.setText("Umm, something is wrong!");
                    }
                });
            }
        });
    }
    
    private Button delete = new Button("Delete account"); {
        delete.setStyleName(Constants.Style.editorButton);
        delete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.deleteProfile(new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        FOOTER.setText("Successful remove, please logout!");
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        FOOTER.setText("Umm, something is wrong!");
                    }
                });
            }
        });
    }
    
    private FlowPanel panel;
    
    private CheckBox heartRateInput = new CheckBox();
    private CheckBox weightInput = new CheckBox();
    private CheckBox shoesInput = new CheckBox();
    private CheckBox weatherInput = new CheckBox();
    private TextBox nicknameInput = new TextBox();
    {
        heartRateInput.setStyleName(Constants.Style.editorCheckBox);
        weightInput.setStyleName(Constants.Style.editorCheckBox);
        shoesInput.setStyleName(Constants.Style.editorCheckBox);
        weatherInput.setStyleName(Constants.Style.editorCheckBox);
        nicknameInput.setStyleName(Constants.Style.editorTextBox);
    }
    
    private ServiceAsync service;
    
    private Profile profile;
    
    public ProfileEditor(ServiceAsync _service) {
        this.service = _service;
        
        panel = new FlowPanel();
        panel.add(TITLE);
        panel.add(NICKNAME);
        panel.add(nicknameInput);
        panel.add(DELETE_ACCOUNT);
        panel.add(delete);
        panel.add(FIELD_SUBTITLE);
        panel.add(HEART_RATE);
        panel.add(heartRateInput);
        panel.add(WEIGHT);
        panel.add(weightInput);
        panel.add(WEATHER);
        panel.add(weatherInput);
        panel.add(SHOES);
        panel.add(shoesInput);
        panel.add(submit);
        panel.add(FOOTER);
        initWidget(panel);
        setStyleName(Constants.Style.editor);
        service.getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                FOOTER.setText("Umm, something is wrong!");
            }

            @Override
            public void onSuccess(Profile result) {
                load(result);
            }
        });
    }
    
    public void load(Profile profile) {
        if (profile == null) {
            profile = new Profile();
        }
        this.profile = profile;
        String nickname = profile.getNickname();
        if(nickname != null) {
            nicknameInput.setText(nickname);
        }
        Boolean heartRate = profile.getHeartRate();
        if(heartRate == null) {
            heartRate = Boolean.FALSE;
        }
        heartRateInput.setValue(heartRate);
        Boolean weight = profile.getWeight();
        if(weight == null) {
            weight = Boolean.FALSE;
        }
        weightInput.setValue(weight);
        Boolean shoes = profile.getShoes();
        if(shoes == null) {
            shoes = Boolean.FALSE;
        }
        shoesInput.setValue(shoes);
        Boolean weather = profile.getWeather();
        if(weather == null) {
            weather = Boolean.FALSE;
        }
        weatherInput.setValue(weather);
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
    
}

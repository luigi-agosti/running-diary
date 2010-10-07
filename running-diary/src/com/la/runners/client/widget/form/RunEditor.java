package com.la.runners.client.widget.form;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.la.runners.client.Constants;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.event.LoadRunEvent;
import com.la.runners.client.event.LoadRunHandler;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.shared.Run;

public class RunEditor extends Composite implements LoadRunHandler {

    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("yyyy");
    private static final DateTimeFormat MONTH_FORMATTER = DateTimeFormat.getFormat("MM");
    private static final DateTimeFormat DAY_FORMATTER = DateTimeFormat.getFormat("dd");
    
    private static final Label DATE_LABEL = new Label("Date");
    private static final Label DAY_TIME_LABEL = new Label("Day Time");
    private static final Label DISTANCE_LABEL = new Label("Distance (meters)");
    private static final Label TIME_LABEL = new Label("Time (minutes)");
    private static final Label NOTE_LABEL = new Label("Note");
    private static final Label SHOES_LABEL = new Label("Shoes");
    private static final Label WEIGHT_LABEL = new Label("Weight");
    private static final Label HEART_RATE_LABEL = new Label("Heart rate");
    private static final Label SHARE_LABEL = new Label("Share"); 
    private static final Label TITLE = new Label("Run Editor"); 
    private static final Label OPTIONAL_SUBTITLE = new Label("Optional");
    private static final Label FOOTER = new Label(" "); 
    private static final FlowPanel DATE_CONTAINER = new FlowPanel();
    
    static {
        DATE_LABEL.setStyleName(Constants.Style.editorLabel);
        DAY_TIME_LABEL.setStyleName(Constants.Style.editorLabel);
        DISTANCE_LABEL.setStyleName(Constants.Style.editorLabel);
        NOTE_LABEL.setStyleName(Constants.Style.editorLabel);
        TIME_LABEL.setStyleName(Constants.Style.editorLabel);
        TITLE.setStylePrimaryName(Constants.Style.editorHeader);
        FOOTER.setStylePrimaryName(Constants.Style.editorFooter);
        DATE_CONTAINER.setStylePrimaryName(Constants.Style.editorDateContainer);
        SHOES_LABEL.setStylePrimaryName(Constants.Style.editorLabel);
        WEIGHT_LABEL.setStylePrimaryName(Constants.Style.editorLabel);
        HEART_RATE_LABEL.setStylePrimaryName(Constants.Style.editorLabel);
        SHARE_LABEL.setStylePrimaryName(Constants.Style.editorLabel);
        OPTIONAL_SUBTITLE.setStylePrimaryName(Constants.Style.editorSubTitle);
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
                        eventBus.fireEvent(new RunListUpdateEvent());
                        reset();
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
    
    private TextBox idInput = new TextBox();
    
    private DatePicker dateInput = new DatePicker();
    private TextBox distanceInput = new TextBox();
    private TextArea noteInput = new TextArea();
    private TextBox timeInput = new TextBox();
    private TextBox dayTimeInput = new TextBox();
    private TextBox shoesInput = new TextBox();
    private TextBox heartRateInput = new TextBox();
    private TextBox weightInput = new TextBox();
    private CheckBox shareInput = new CheckBox();
    {
        idInput.setStyleName(Constants.Style.editorTextBox);
        distanceInput.setStyleName(Constants.Style.editorTextBox);
        noteInput.setStyleName(Constants.Style.editorTextBox);
        timeInput.setStyleName(Constants.Style.editorTextBox);
        dayTimeInput.setStyleName(Constants.Style.editorTextBox);
        heartRateInput.setStyleName(Constants.Style.editorTextBox);
        shoesInput.setStyleName(Constants.Style.editorTextBox);
        weightInput.setStyleName(Constants.Style.editorTextBox);
    }
    
    private HandlerManager eventBus;
    
    private ServiceAsync service;
    
    private Run run;
    
    public RunEditor(HandlerManager eventBus, ServiceAsync _service) {
        this.service = _service;
        this.eventBus = eventBus;
        eventBus.addHandler(LoadRunEvent.TYPE, this);
        
        panel = new FlowPanel();
        panel.add(TITLE);
        panel.add(DATE_LABEL);
        DATE_CONTAINER.add(dateInput);
        panel.add(DATE_CONTAINER);
        panel.add(DAY_TIME_LABEL);
        panel.add(dayTimeInput);
        panel.add(DISTANCE_LABEL);
        panel.add(distanceInput);
        panel.add(TIME_LABEL);
        panel.add(timeInput);
        panel.add(OPTIONAL_SUBTITLE);
        panel.add(HEART_RATE_LABEL);
        panel.add(heartRateInput);
        panel.add(WEIGHT_LABEL);
        panel.add(weightInput);
        panel.add(SHOES_LABEL);
        panel.add(shoesInput);
        panel.add(NOTE_LABEL);
        panel.add(noteInput);
        panel.add(SHARE_LABEL);
        panel.add(shareInput);
        panel.add(submit);
        panel.add(FOOTER);
        initWidget(panel);
        setStyleName(Constants.Style.editor);
    }
    
    public void load(Run run) {
        this.run = run;
        idInput.setText("" + run.getId());
        dateInput.setValue(run.getDate());
        dayTimeInput.setText("" + run.getDayTime());
        distanceInput.setText("" + run.getDistance());
        noteInput.setText("" + run.getNote());
        timeInput.setText("" + run.getTime());
        if(run.getHeartRate() == null) {
            heartRateInput.setText("");
        } else {
            heartRateInput.setText("" + run.getHeartRate());
        }
        if(run.getWeight() == null) {
            weightInput.setText("");
        } else {
            weightInput.setText("" + run.getWeight());
        }
        if(run.getShoes() == null) {
            shoesInput.setText("");
        } else {
            shoesInput.setText("" + run.getShoes());
        }
        if(run.getShare() == null) {
            shareInput.setValue(Boolean.TRUE);
        } else {
            shareInput.setValue(run.getShare());
        }
    }

    public void reset() {
        run = new Run();
        idInput.setText("");
        dateInput.setValue(new Date());
        dayTimeInput.setText("");
        distanceInput.setText("");
        noteInput.setText("");
        timeInput.setText("");
        heartRateInput.setText("");
        weightInput.setText("");
        shoesInput.setText("");
        shareInput.setValue(Boolean.TRUE);
    }
    
    public Run get() {
        if (run == null) {
            run = new Run();
        }
        Date date = dateInput.getValue();
        run.setYear(Integer.parseInt(YEAR_FORMATTER.format(date)));
        run.setMonth(Integer.parseInt(MONTH_FORMATTER.format(date)));
        run.setMonth(run.getMonth() - 1);
        run.setDay(Integer.parseInt(DAY_FORMATTER.format(date)));
        run.setDate(date);
        run.setDistance(Integer.valueOf(distanceInput.getText()));
        run.setNote(noteInput.getText());
        run.setTime(Long.valueOf(timeInput.getText()));
        run.setDayTime(Long.valueOf(dayTimeInput.getText()));
        run.setModified(new Date());
        
        setHeartRateValue(run);
        setWeightValue(run);
        setShoesValue(run);
        
        run.setShare(shareInput.getValue());
        return run;
    }
    
    private void setHeartRateValue(Run run) {
        String value = heartRateInput.getText(); 
        if(value != null && !"".equals(value)) {
            run.setHeartRate(Integer.parseInt(value));
        }
    }
    
    private void setWeightValue(Run run) {
        String value = weightInput.getText(); 
        if(value != null && !"".equals(value)) {
            run.setWeight(Integer.parseInt(value));
        }
    }
    
    private void setShoesValue(Run run) {
        String value = shoesInput.getText(); 
        if(value != null && !"".equals(value)) {
            run.setShoes(value);
        }
    }

    @Override
    public void loadRun(LoadRunEvent event) {
        service.getRun(event.getId(), new AsyncCallback<Run>() {
            @Override
            public void onFailure(Throwable caught) {
                FOOTER.setText("Umm, something is wrong!");
            }

            @Override
            public void onSuccess(Run result) {
                load(result);
            }
        });
    }
    
}

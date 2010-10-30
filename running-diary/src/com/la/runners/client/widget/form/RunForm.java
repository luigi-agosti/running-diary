package com.la.runners.client.widget.form;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.la.runners.client.Context;
import com.la.runners.client.event.LoadRunEvent;
import com.la.runners.client.event.LoadRunHandler;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.widget.form.field.CheckBoxField;
import com.la.runners.client.widget.form.field.DatePickerField;
import com.la.runners.client.widget.form.field.TextAreaField;
import com.la.runners.client.widget.form.field.TextBoxField;
import com.la.runners.client.widget.form.field.TimePickerField;
import com.la.runners.shared.Run;

public class RunForm extends CustomForm implements LoadRunHandler {

    private Run run;

    private DatePickerField dateInput;
    private TextBoxField distanceInput;
    private TimePickerField dayTimeInput;
    private TextAreaField noteInput;
    private TimePickerField timeInput;
    private TextBoxField shoesInput;
    private TextBoxField heartRateInput;
    private TextBoxField weightInput;
    private CheckBoxField shareInput;
    
    public RunForm(Context context) {
        super(context, context.strings.runFormTitle());
        eventBus().addHandler(LoadRunEvent.TYPE, this);
        dateInput = addDatePickerField(strings().runFormDate());
        dayTimeInput = addTimePickerField(strings().runFormStart(), new Date(0));
        distanceInput = addTextBoxField(strings().runFormDistance());
        timeInput = addTimePickerField(strings().runFormTime(), new Date(0));
        addSubtitle(strings().runFormOptional());
        if(profile().getHeartRate()) {
            heartRateInput = addTextBoxField(strings().runFormHeartRate());
        }
        if(profile().getWeight()) {
            weightInput = addTextBoxField(strings().runFormWeight());
        }
        if(profile().getShoes()) {
            shoesInput = addTextBoxField(strings().runFormShoes());
        }
        noteInput = addTextAreaField(strings().runFormNote());
        shareInput = addCheckBoxField(strings().runFormShare());
        
        addButton(strings().runFormSave(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service().save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage(strings().runFormSuccess());
                        eventBus().fireEvent(new RunListUpdateEvent());
                        reset();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage(strings().runFormFailure());
                    }
                });
            }
        });
        addFooterForMessages();
    }

    public void load(Run run) {
        this.run = run;
        distanceInput.setValue(run.getDistance());
        noteInput.setValue(run.getNote());
        timeInput.setValue(run.getTime());
        if(heartRateInput != null) {
            heartRateInput.setValue(run.getHeartRate());
        }
        if(weightInput != null) {
            weightInput.setValue(run.getWeight());
        }
        if(shoesInput != null) {
            shoesInput.setValue(run.getShoes());
        }
        shareInput.setValue(run.getShare());
    }

    public void reset() {
        run = new Run();
        dateInput.setValue(new Date());
        dayTimeInput.reset();
        distanceInput.reset();
        noteInput.reset(); 
        timeInput.reset();
        if(heartRateInput != null) {
            heartRateInput.reset();
        }
        if(weightInput != null) {
            weightInput.reset();
        }
        if(shoesInput != null) {
            shoesInput.reset();
        }
        shareInput.reset();
    }
    
    public Run get() {
        if (run == null) {
            run = new Run();
        }
        Date date = new Date();
        run.setCreated(date);
        run.setModified(date);
        run.setYear(dateInput.getYear());
        run.setMonth(dateInput.getMonth());
        run.setDay(dateInput.getDay());
        run.setHour(dayTimeInput.getHours());
        run.setDistance(distanceInput.asLong());
        run.setNote(noteInput.getValue());
        run.setTime(timeInput.getLongValue());
        run.setStartDate(new Date(dateInput.getLongValue() + dayTimeInput.getLongValue()));
        run.setEndDate(new Date(run.getStartDate().getTime() + timeInput.getLongValue()));
        if(heartRateInput != null) {
            run.setHeartRate(heartRateInput.asInteger());
        }
        if(weightInput != null) {
            run.setWeight(weightInput.asInteger());
        }
        if(shoesInput != null) {
            run.setShoes(shoesInput.getValue());
        }
        run.setShare(shareInput.getValue());
        return run;
    }

    @Override
    public void loadRun(LoadRunEvent event) {
        service().getRun(event.getId(), new AsyncCallback<Run>() {
            @Override
            public void onFailure(Throwable caught) {
                showMessage(strings().runFormFailure());
            }

            @Override
            public void onSuccess(Run result) {
                load(result);
            }
        });
    }
    
}

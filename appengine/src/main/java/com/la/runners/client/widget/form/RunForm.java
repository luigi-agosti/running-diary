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
import com.la.runners.client.widget.form.field.NumericMandatoryBoxField;
import com.la.runners.client.widget.form.field.TextAreaField;
import com.la.runners.client.widget.form.field.TextBoxField;
import com.la.runners.client.widget.form.field.TimePickerField;
import com.la.runners.shared.Run;

public class RunForm extends CustomForm implements LoadRunHandler {

	private static final double E6_MULTI = 1000000D;

	private Run run;
    
    private DatePickerField startDateField;
    private NumericMandatoryBoxField distanceField;
    private TimePickerField startTimeField;
    private TextAreaField noteField;
    private TimePickerField timeField;
    private TextBoxField shoesField;
    private TextBoxField heartRateField;
    private TextBoxField weightField;
    private CheckBoxField shareField;
    
    public RunForm(Context context) {
        super(context, context.strings.runFormTitle());
        eventBus().addHandler(LoadRunEvent.TYPE, this);
        startDateField = addDatePickerField(strings().runFormDate(), new Date());
        startTimeField = addTimePickerField(strings().runFormStart(), new Date());
        distanceField = addNumericMandatoryBoxField(strings().runFormDistance());
        timeField = addTimePickerField(strings().runFormTime(), new Date(0));
        addSubtitle(strings().runFormOptional());
        if(profile().getHeartRate()) {
            heartRateField = addTextBoxField(strings().runFormHeartRate());
        }
        if(profile().getWeight()) {
            weightField = addTextBoxField(strings().runFormWeight());
        }
        if(profile().getShoes()) {
            shoesField = addTextBoxField(strings().runFormShoes());
        }
        noteField = addTextAreaField(strings().runFormNote());
        shareField = addCheckBoxField(strings().runFormShare());
        
        addButton(strings().runFormSave(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(!distanceField.isValid()){
                    return;
                }
                distanceField.resetValidation();
                if(!timeField.isValid()){
                    return;
                }
                timeField.resetValidation();
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
        addButton(strings().runFormCancel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                reset();
            }
        });
        addFooterForMessages();
    }

    public void load(Run run) {
        this.run = run;
        startDateField.setValue(run.getStartDate());
        startTimeField.setValue(run.getStartDate());
        distanceField.setValue(unitConverter().customUnitDistance(run.getDistance()));
        noteField.setValue(run.getNote());
        timeField.setValue(run.getTime());
        if(heartRateField != null) {
            heartRateField.setValue(run.getHeartRate());
        }
        if(weightField != null) {
            weightField.setValue(run.getWeight());
        }
        if(shoesField != null) {
            shoesField.setValue(run.getShoes());
        }
        shareField.setValue(run.getShare());
    }

    public void reset() {
        run = new Run();
        startDateField.reset();
        startTimeField.reset(new Date());
        distanceField.reset();
        noteField.reset(); 
        timeField.reset();
        if(heartRateField != null) {
            heartRateField.reset();
        }
        if(weightField != null) {
            weightField.reset();
        }
        if(shoesField != null) {
            shoesField.reset();
        }
        shareField.reset();
    }
    
    private Run get() {
        if (run == null) {
            run = new Run();
        }
        Date date = new Date();
        run.setCreated(date);
        run.setModified(date);
        run.setYear(startDateField.getYear());
        run.setMonth(startDateField.getMonth());
        run.setDay(startDateField.getDay());
        run.setHour(startTimeField.getHours());
        run.setDistance(unitConverter().indipendentUnitDistance(distanceField.asLong()));
        run.setNote(noteField.getValue());
        run.setTime(timeField.getLongValue());
        run.setStartDate(new Date(startDateField.getLongValue() + startTimeField.getLongValue()));
        run.setEndDate(new Date(run.getStartDate().getTime() + timeField.getLongValue()));
        run.setSpeed((long)((run.getDistance()*E6_MULTI)/run.getTime()));
        if(heartRateField != null) {
            run.setHeartRate(heartRateField.asInteger());
        }
        if(weightField != null) {
            run.setWeight(weightField.asInteger());
        }
        if(shoesField != null) {
            run.setShoes(shoesField.getValue());
        }
        run.setShare(shareField.getValue());
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

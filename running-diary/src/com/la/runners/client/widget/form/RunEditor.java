package com.la.runners.client.widget.form;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.la.runners.client.Context;
import com.la.runners.client.event.LoadRunEvent;
import com.la.runners.client.event.LoadRunHandler;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.shared.Run;

public class RunEditor extends BaseForm implements LoadRunHandler {

    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("yyyy");
    private static final DateTimeFormat MONTH_FORMATTER = DateTimeFormat.getFormat("MM");
    private static final DateTimeFormat DAY_FORMATTER = DateTimeFormat.getFormat("dd");
    
    private Run run;

    private DatePicker dateInput;
    private TextBox idInput;
    private TextBox distanceInput;
    private TextBox dayTimeInput;
    private TextArea noteInput;
    private TextBox timeInput;
    private TextBox shoesInput;
    private TextBox heartRateInput;
    private TextBox weightInput;
    private CheckBox shareInput;
    
    public RunEditor(Context context) {
        super(context, "Run Editor");
        eventBus().addHandler(LoadRunEvent.TYPE, this);
        
        dateInput = addDateWithLabel("Date");
        dayTimeInput = addTextBoxWithLabel("Day Time");
        distanceInput = addTextBoxWithLabel("Distance (meters)");
        timeInput = addTextBoxWithLabel("Time (minutes)");
        addSubtitle("Optional");
        heartRateInput = addTextBoxWithLabel("Heart rate");
        weightInput = addTextBoxWithLabel("Weight");
        shoesInput = addTextBoxWithLabel("Shoes");
        noteInput = addTextAreaWithLabel("Note");
        shareInput = addCheckBoxWithLabel("Share");
        
        addButton("Save", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service().save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        showMessage("Successful saved!");
                        eventBus().fireEvent(new RunListUpdateEvent());
                        reset();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage("Umm, something is wrong!");
                    }
                });
            }
        });
        showMessage("");
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
        run.setDistance(Long.valueOf(distanceInput.getText()));
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
        service().getRun(event.getId(), new AsyncCallback<Run>() {
            @Override
            public void onFailure(Throwable caught) {
                showMessage("Umm, something is wrong!");
            }

            @Override
            public void onSuccess(Run result) {
                load(result);
            }
        });
    }
    
}

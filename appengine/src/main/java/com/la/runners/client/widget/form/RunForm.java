package com.la.runners.client.widget.form;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.la.runners.client.Context;
import com.la.runners.client.event.RunLoadEvent;
import com.la.runners.client.event.RunLoadHandler;
import com.la.runners.client.event.LocationsUpdateEvent;
import com.la.runners.client.event.LocationsUpdateHandler;
import com.la.runners.client.event.RunCloneEvent;
import com.la.runners.client.event.RunCloneHandler;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.event.ShowMapEvent;
import com.la.runners.client.widget.form.field.CheckBoxField;
import com.la.runners.client.widget.form.field.DatePickerField;
import com.la.runners.client.widget.form.field.NumericMandatoryBoxField;
import com.la.runners.client.widget.form.field.TextAreaField;
import com.la.runners.client.widget.form.field.TextBoxField;
import com.la.runners.client.widget.form.field.TimePickerField;
import com.la.runners.shared.Location;
import com.la.runners.shared.Run;

public class RunForm extends CustomForm implements RunLoadHandler, LocationsUpdateHandler, RunCloneHandler {

    public static final String ID = "runForm";
    private static final String EDIT_MAP = "editMap";
    private static final String SAVE_RUN = "saveRun";
    private static final String CANCEL_RUN_EDIT = "cancelRunEdit";
    
	private static final double E6_MULTI = 1000000D;
    private static final String SHARE_ID = "share";
    private static final String NOTE_ID = "note";
    private static final String SHOES_ID = "shoes";
    private static final String WEIGHT_ID = "weight";
    private static final String HEART_RATE_ID = "heartRate";
    private static final String TIME_ID = "time";
    private static final String DISTANCE_ID = "distance";
    private static final String START_TIME_ID = "startTime";
    private static final String START_DATE_ID = "startDate";

	private List<Location> locations;
	
	private Run run;
	private Long cloneRunId;
    
    private DatePickerField startDateField;
    private NumericMandatoryBoxField distanceField;
    private TimePickerField startTimeField;
    private TextAreaField noteField;
    private TimePickerField timeField;
    private TextBoxField shoesField;
    private TextBoxField heartRateField;
    private TextBoxField weightField;
    private CheckBoxField shareField;
    
    public RunForm(final Context context) {
        super(context, context.strings.runFormTitle(), ID);
        eventBus().addHandler(RunLoadEvent.TYPE, this);
        eventBus().addHandler(LocationsUpdateEvent.TYPE, this);
        eventBus().addHandler(RunCloneEvent.TYPE, this);
        
        startDateField = addDatePickerField(strings().runFormDate(), new Date(), START_DATE_ID);
        startTimeField = addTimePickerField(strings().runFormStart(), new Date(), START_TIME_ID);
        distanceField = addNumericMandatoryBoxField(strings().runFormDistance(), DISTANCE_ID);
        timeField = addTimePickerField(strings().runFormTime(), new Date(0), TIME_ID);
        addButton(strings().runFormEditMap(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	Long runId = run.getId();
            	if(cloneRunId != null) {
            		runId = cloneRunId;
            	}
            	ShowMapEvent eventMap = new ShowMapEvent(runId, Boolean.TRUE);
            	eventMap.setLocations(locations);
            	eventBus().fireEvent(eventMap);
            }
        }, EDIT_MAP);
        addSubtitle(strings().runFormOptional());
        if(profile().getHeartRate()) {
            heartRateField = addTextBoxField(strings().runFormHeartRate(), HEART_RATE_ID);
        }
        if(profile().getWeight()) {
            weightField = addTextBoxField(strings().runFormWeight(), WEIGHT_ID);
        }
        if(profile().getShoes()) {
            shoesField = addTextBoxField(strings().runFormShoes(), SHOES_ID);
        }
        noteField = addTextAreaField(strings().runFormNote(), NOTE_ID);
        shareField = addCheckBoxField(strings().runFormShare(), SHARE_ID);
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
                service().save(get(), new AsyncCallback<Long>() {
                    @Override
                    public void onSuccess(Long runId) {
                        showMessage(strings().runFormSuccess());
                        eventBus().fireEvent(new RunListUpdateEvent());
                        reset();
                        if(locations != null) {                	
                        	service().updateLocations(locations, runId, new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable caught) {
									showMessage(strings().runFormLocationFailure());
								}

								@Override
								public void onSuccess(Void result) {
									showMessage(strings().runFormLocationSuccess());	
								}
                        	});
                        }
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage(strings().runFormFailure());
                    }
                });
            }
        }, SAVE_RUN);
        addButton(strings().runFormCancel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                reset();
            }
        }, CANCEL_RUN_EDIT);
        addFooterForMessages();
        reset();
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
        cloneRunId = null;
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
    public void loadRun(RunLoadEvent event) {
    	load(event.getId(), false);
    }
    
	@Override
	public void update(LocationsUpdateEvent event) {
		locations = event.getLocations(); 
		distanceField.setValue(unitConverter().metersToCustom(event.getDistance()));
	}

	@Override
	public void clone(RunCloneEvent event) {
		load(event.getId(), true);
	}
	
	private void load(Long id, final boolean removeId) {
		service().getRun(id, new AsyncCallback<Run>() {
            @Override
            public void onFailure(Throwable caught) {
                showMessage(strings().runFormFailure());
            }

            @Override
            public void onSuccess(Run result) {
            	if(removeId) {
            		cloneRunId = result.getId();
            		result.setStartDate(new Date());
            		result.setCreated(new Date());
            		result.setId(null);
            	}
                load(result);
            }
        });
	}
    
}

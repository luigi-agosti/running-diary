package com.la.runners.client.widget.form.field;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.res.Resources;

public class TimePickerField extends FormField {
    
    private static final DateTimeFormat HOURS_FORMATTER = DateTimeFormat.getFormat("HH");
    private static final DateTimeFormat MINUTES_FORMATTER = DateTimeFormat.getFormat("mm");
    private static final DateTimeFormat SECONDS_FORMATTER = DateTimeFormat.getFormat("ss");
    
    private static final TimeZone TIME_ZONE = TimeZone.createTimeZone(0);
    
    private ZoneIndipendentTimePicker field;
    
    public TimePickerField(String name, Date defaultValue) {
        super(null);
        field = new ZoneIndipendentTimePicker(defaultValue, null, HOURS_FORMATTER, MINUTES_FORMATTER, SECONDS_FORMATTER);
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(new Label(name + LABEL_SEPARATOR));
        panel.setStyleName(Resources.INSTANCE.form().editorTimePickerContainer());
        panel.add(field);
        setField(panel);
    }

    @Override
    public Object getValue() {
        return field.getDateTime();
    }

    @Override
    public void setValue(Object value) {
        if(value instanceof Long) {
            field.setDateTime(new Date((Long)value));    
        } else {
            field.setDateTime((Date)value);
        }
    }

    @Override
    public void reset() {
        reset(new Date(0));
    }
    
    public void reset(Date defaultValue) {
        setValue(defaultValue);
    }
    
    public Long getHours() {
        String hours = HOURS_FORMATTER.format((Date)getValue(), TIME_ZONE);
        return Long.parseLong(hours);
    }

    public Long getLongValue() {
        Date value = (Date)getValue();
        String hours = HOURS_FORMATTER.format(value, TIME_ZONE);
        String minutes = MINUTES_FORMATTER.format(value, TIME_ZONE);
        String seconds = SECONDS_FORMATTER.format(value, TIME_ZONE);
        return Long.parseLong(hours)*3600*1000 + Long.parseLong(minutes)*60*1000 + Long.parseLong(seconds)*1000;
    }
    
    @Override
    public boolean isValid() {
        if(getLongValue().longValue() > 1000) {
            return true;
        } else {
            showValidationError();
            return false;
        }
    }
    
}
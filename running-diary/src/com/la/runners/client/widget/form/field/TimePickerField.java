package com.la.runners.client.widget.form.field;

import java.util.Date;

import com.google.gwt.gen2.picker.client.TimePicker;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.res.Styles;

public class TimePickerField extends FormField {
    
    private static final DateTimeFormat HOURS_FORMATTER = DateTimeFormat.getFormat("HH");
    
    private TimePicker field;
    
    public TimePickerField(String name, Date defaultValue) {
        super(null);
        field = new TimePicker(defaultValue, null, DateTimeFormat.getFormat("HH"), DateTimeFormat.getFormat("mm"), null);
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(new Label(name + LABEL_SEPARATOR));
        panel.setStyleName(Styles.Form.editorTimePickerContainer);
        panel.add(field);
        setField(panel);
    }

    @Override
    public Object getValue() {
        return field.getDateTime();
    }

    @Override
    public void setValue(Object value) {
        field.setDate((Date)value);
    }

    @Override
    public void reset() {
        setValue(new Date(0));
    }
    
    public Long getHours() {
        return Long.parseLong(HOURS_FORMATTER.format((Date)getValue()));
    }

    public Long getLongValue() {
        Date date = (Date)getValue();
        if(date == null) {
            return Long.valueOf(0L);
        }
        return date.getTime();
    }
    
}
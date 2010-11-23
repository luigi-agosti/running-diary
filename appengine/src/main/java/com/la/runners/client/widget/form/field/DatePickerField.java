package com.la.runners.client.widget.form.field;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.la.runners.client.res.Resources;

public class DatePickerField extends FormField {

    private static final TimeZone TIME_ZONE = TimeZone.createTimeZone(0);
    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("yyyy");
    private static final DateTimeFormat MONTH_FORMATTER = DateTimeFormat.getFormat("MM");
    private static final DateTimeFormat DAY_FORMATTER = DateTimeFormat.getFormat("dd");
    private static final DateTimeFormat HOURS_FORMATTER = DateTimeFormat.getFormat("HH");
    private static final DateTimeFormat MINUTES_FORMATTER = DateTimeFormat.getFormat("mm");
    
    private DatePicker field;
    
    public DatePickerField(String name, Date defaultValue, String debugId) {
        super(name, debugId);
        field = new DatePicker();
        field.setStyleName(Resources.INSTANCE.form().editorTextBox());
        setField(field);
        setValue(defaultValue);
    }

    @Override
    public Object getValue() {
        return field.getValue();
    }

    @Override
    public void setValue(Object value) {
        field.setValue((Date)value);
    }

    @Override
    public void reset() {
        setValue(new Date());
    }

    public Integer getYear() {
        return Integer.parseInt(YEAR_FORMATTER.format((Date)getValue(), TIME_ZONE));
    }
    
    public Integer getMonth() {
        return Integer.parseInt(MONTH_FORMATTER.format((Date)getValue(), TIME_ZONE));
    }

    public Integer getDay() {
        return Integer.parseInt(DAY_FORMATTER.format((Date)getValue(), TIME_ZONE));
    }

    public Long getLongValue() {
        Date value = (Date)getValue();
        return value.getTime() - getLongValueOfTime(value);
    }

    private Long getLongValueOfTime(Date value) {
        String hours = HOURS_FORMATTER.format(value, TIME_ZONE);
        String minutes = MINUTES_FORMATTER.format(value, TIME_ZONE);
        return Long.parseLong(hours)*3600*1000 + Long.parseLong(minutes)*60*1000;
    }
    
}

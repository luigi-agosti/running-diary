package com.la.runners.client.widget.form.field;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.la.runners.client.res.Styles;

public class DatePickerField extends FormField {

    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("yyyy");
    private static final DateTimeFormat MONTH_FORMATTER = DateTimeFormat.getFormat("MM");
    private static final DateTimeFormat DAY_FORMATTER = DateTimeFormat.getFormat("dd");
    
    private DatePicker field;
    
    public DatePickerField(String name) {
        super(name);
        field = new DatePicker();
        field.setStyleName(Styles.Form.editorTextBox);
        setField(field);
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
        return Integer.parseInt(YEAR_FORMATTER.format((Date)getValue()));
    }
    
    public Integer getMonth() {
        return Integer.parseInt(MONTH_FORMATTER.format((Date)getValue()));
    }

    public Integer getDay() {
        return Integer.parseInt(DAY_FORMATTER.format((Date)getValue()));
    }

    public Long getLongValue() {
        Date date = (Date)getValue();
        if(date == null) {
            return Long.valueOf(0L);
        }
        return date.getTime();
    }
    
}

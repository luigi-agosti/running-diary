package com.la.runners.client.widget.form.field;

import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.res.Styles;

public class TextBoxField extends FormField {

    private TextBox field;
    
    public TextBoxField(String name) {
        super(name);
        field = new TextBox();
        field.setStyleName(Styles.Form.editorTextBox);
        setField(field);
    }

    @Override
    public String getValue() {
        if(isEmpty()) {
            return null;
        } else {
            return field.getText();
        }
    }
    
    protected boolean isNotEmpty() {
        if(!isEmpty()) {
            return Boolean.TRUE;
        }
        showValidationError();
        return Boolean.FALSE;
    }
    
    @Override
    public void setValue(Object value) {
        if(value == null) {
            field.setText(EMPTY_LABEL);
        }
        field.setText((String)value);
    }
    
    public boolean isEmpty() {
        String value = getValue();
        if(value != null && value.length() > 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
    @Override
    public void reset() {
        setValue(null);
    }
    
    public Long asLong() {
        return Long.valueOf(field.getText());
    }

    public Integer asInteger() {
        return Integer.valueOf(field.getText());
    }

}

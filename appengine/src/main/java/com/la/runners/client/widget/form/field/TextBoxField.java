package com.la.runners.client.widget.form.field;

import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.res.ResourceBundle;

public class TextBoxField extends FormField {

    private TextBox field;
    
    public TextBoxField(String name) {
        super(name);
        field = new TextBox();
        field.setStyleName(ResourceBundle.INSTANCE.form().editorTextBox());
        setField(field);
    }

    @Override
    public String getValue() {
        String text = field.getText();
        if(text != null && text.length() > 0) {
            return field.getText();
        } else {
            return null;
        }
    }
    
    @Override
    public void setValue(Object value) {
        if(value == null) {
            field.setText(EMPTY_LABEL);
        } else {
            field.setText(String.valueOf(value));
        }
    }
    
    public boolean isEmpty() {
        String value = getValue();
        if(value != null) {
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
    
    protected boolean isNotEmpty() {
        return !isEmpty();
    }

}

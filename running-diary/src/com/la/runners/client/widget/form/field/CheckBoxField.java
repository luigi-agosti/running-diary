package com.la.runners.client.widget.form.field;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.res.Styles;

public class CheckBoxField extends FormField {

    private CheckBox field;
    
    public CheckBoxField(String name) {
        super(null);
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(new Label(name + LABEL_SEPARATOR));
        panel.setStyleName(Styles.Form.editorCheckBoxContainer);
        field = new CheckBox();
        field.setStyleName(Styles.Form.editorCheckBox);
        panel.add(field);
        setField(panel);
    }

    @Override
    public Boolean getValue() {
        return field.getValue();
    }
    
    @Override
    public void setValue(Object value) {
        if(value == null) {
            value = Boolean.FALSE;
        }
        field.setValue((Boolean)value);
    }

    @Override
    public void reset() {
        setValue(Boolean.FALSE);
    }

}

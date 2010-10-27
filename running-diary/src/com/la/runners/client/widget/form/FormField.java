package com.la.runners.client.widget.form;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class FormField extends Composite {
    
    private static final String LABEL_SEPARATOR = " : ";
    
    private String name;
    
    private Widget field;
    
    private FlowPanel panel;
    
    public FormField(String name, Widget field) {
        this.name = name;
        this.field = field;
        panel = new FlowPanel();
        panel.add(new Label(name + LABEL_SEPARATOR));
        panel.add(field);
    }
    
    public boolean isValid() {
        return Boolean.TRUE;
    }

    public String getName() {
        return name;
    }

    public Widget getField() {
        return field;
    }
    
    public abstract void showValidationMessage(String message);

}

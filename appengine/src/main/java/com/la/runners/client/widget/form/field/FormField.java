package com.la.runners.client.widget.form.field;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.la.runners.client.res.Resources;

public abstract class FormField extends Composite {
    
    protected static final String LABEL_SEPARATOR = " : ";
    
    protected static final String EMPTY_LABEL = "";
    
    private String name;
    
    private Widget field;
    
    private Label errorMessage;
    
    private FlowPanel panel;
    
    private boolean enabled = Boolean.TRUE;
    
    public FormField(String name) {
        this.name = name;
        panel = new FlowPanel();
        panel.setStyleName(Resources.INSTANCE.form().editorField());
        if(name != null) {
            panel.add(new Label(name + LABEL_SEPARATOR));
        }
        errorMessage = new Label();
        errorMessage.setVisible(Boolean.FALSE);
        panel.add(errorMessage);
        initWidget(panel);
    }
    
    public void setField(Widget field) {
        this.field = field;
        panel.add(getField());
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
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void showValidationError() {
        errorMessage.setVisible(Boolean.TRUE);
        addStyleName(Resources.INSTANCE.form().editorNotValidField());
        
    }
    
    public void showValidationError(String customMessage) {
        errorMessage.setVisible(Boolean.TRUE);
        errorMessage.setText(customMessage);
        addStyleName(Resources.INSTANCE.form().editorNotValidField());
    }
    

    public abstract Object getValue();
    
    public abstract void setValue(Object value);
    
    public void setValue(Object value, Object defaultValue) {
        if(value != null) {
            setValue(value);
            return;
        }
        setValue(defaultValue);
    }
    
    public void resetValidation() {
        errorMessage.setVisible(Boolean.FALSE);
        errorMessage.setText(EMPTY_LABEL);
        removeStyleName(Resources.INSTANCE.form().editorNotValidField());
    }
    
    public abstract void reset();

}

package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.la.runners.client.Context;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.res.ResourceBundle;
import com.la.runners.client.res.Strings;

@Deprecated
public class BaseForm extends Composite {
    
    private FlowPanel panel = new FlowPanel();
    private Label footer;
    
    protected Context context;
    
    public BaseForm(Context context, String title) {
        this.context = context;
        panel = new FlowPanel();
        addLabel(title, ResourceBundle.INSTANCE.form().editorHeader());
        initWidget(panel);
        setStyleName(ResourceBundle.INSTANCE.form().editor());
    }
    
    protected Strings strings() {
        return context.strings;
    }
    
    protected ServiceAsync service() {
        return context.getService();
    }
    
    protected HandlerManager eventBus() {
        return context.getEventBus();
    }
    
    protected void addLabel(String text) {
        addLabel(text, ResourceBundle.INSTANCE.form().editorLabel());
    }

    protected void addSubtitle(String text) {
        addLabel(text, ResourceBundle.INSTANCE.form().editorSubTitle());
    }

    protected TextBox addTextBox() {
        TextBox tb = new TextBox();
        tb.setStyleName(ResourceBundle.INSTANCE.form().editorTextBox());
        panel.add(tb);
        return tb;
    }

    protected DatePicker addDateWithLabel(String text) {
        FlowPanel dateContainer = new FlowPanel();
        dateContainer.setStylePrimaryName(ResourceBundle.INSTANCE.form().editorDateContainer());
        
        DatePicker dateInput = new DatePicker();
        dateInput.setStyleName(ResourceBundle.INSTANCE.form().editorTextBox());
        dateContainer.add(dateInput);
        
        panel.add(dateContainer);
        return dateInput;
    }

    protected TextArea addTextArea() {
        TextArea tb = new TextArea();
        tb.setStyleName(ResourceBundle.INSTANCE.form().editorTextArea());
        panel.add(tb);
        return tb;
    }
    
    protected TextBox addTextBoxWithLabel(String labelText) {
        addLabel(labelText);
        return addTextBox();
    }

    protected TextArea addTextAreaWithLabel(String labelText) {
        addLabel(labelText);
        return addTextArea();
    }

    protected CheckBox addCheckBox() {
        CheckBox cb = new CheckBox();
        cb.setStyleName(ResourceBundle.INSTANCE.form().editorCheckBox());
        panel.add(cb);
        return cb;
    }
    
    protected CheckBox addCheckBoxWithLabel(String labelText) {
        addLabel(labelText);
        return addCheckBox();
    }
    
    protected void addFooterForMessages() {
        showMessage("");
    }
    
    protected void showMessage(String message) {
        if(footer == null) {
            footer = new Label();
            footer.setStyleName(ResourceBundle.INSTANCE.form().editorFooter());
            panel.add(footer);
        }
        footer.setText(message);
    }
    
    protected void addButton(String text, ClickHandler clickHandler) {
        Button b = new Button(text);
        b.setStyleName(ResourceBundle.INSTANCE.form().editorButton());
        b.addClickHandler(clickHandler);
        panel.add(b);
    }
    
    protected void setValue(Widget w, Object value) {
        if(w instanceof TextBox) {
            if(value == null) {
                return;
            }
            ((TextBox)w).setText((String)value);
        } else if(w instanceof CheckBox) {
            if(value == null) {
                value = Boolean.FALSE;
            }
            ((CheckBox)w).setValue((Boolean)value);
        } else {
            throw new RuntimeException("Widget of type " + w.getClass() + "not yet implemented");
        }
    }
    
    private void addLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyleName(style);
        panel.add(label);
    }
    
}

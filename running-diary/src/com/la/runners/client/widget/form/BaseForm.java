package com.la.runners.client.widget.form;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.la.runners.client.Constants;

public class BaseForm extends Composite {
    
    private FlowPanel panel = new FlowPanel();
    private Label footer;
    
    public BaseForm(String title) {
        panel = new FlowPanel();
        addLabel(title, Constants.Style.editorHeader);
        initWidget(panel);
        setStyleName(Constants.Style.editor);
    }
    
    protected void addLabel(String text) {
        addLabel(text, Constants.Style.editorLabel);
    }

    protected void addSubtitle(String text) {
        addLabel(text, Constants.Style.editorSubTitle);
    }

    protected TextBox addTextBox() {
        TextBox tb = new TextBox();
        tb.setStyleName(Constants.Style.editorTextBox);
        panel.add(tb);
        return tb;
    }

    protected TextArea addTextArea() {
        TextArea tb = new TextArea();
        tb.setStyleName(Constants.Style.editorTextArea);
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
        cb.setStyleName(Constants.Style.editorCheckBox);
        panel.add(cb);
        return cb;
    }
    
    protected CheckBox addCheckBoxWithLabel(String labelText) {
        addLabel(labelText);
        return addCheckBox();
    }
    
    protected void showMessage(String message) {
        if(footer == null) {
            footer = new Label();
            footer.setStyleName(Constants.Style.editorFooter);
            panel.add(footer);
        }
        footer.setText(message);
    }
    
    protected void addButton(String text, ClickHandler clickHandler) {
        Button b = new Button(text);
        b.setStyleName(Constants.Style.editorButton);
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

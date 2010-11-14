package com.la.runners.client.widget.form;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.Context;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.res.Resources;
import com.la.runners.client.res.Strings;
import com.la.runners.client.widget.form.field.CheckBoxField;
import com.la.runners.client.widget.form.field.DatePickerField;
import com.la.runners.client.widget.form.field.FormField;
import com.la.runners.client.widget.form.field.ListBoxField;
import com.la.runners.client.widget.form.field.MandatoryTextBoxField;
import com.la.runners.client.widget.form.field.NumericMandatoryBoxField;
import com.la.runners.client.widget.form.field.TextAreaField;
import com.la.runners.client.widget.form.field.TextBoxField;
import com.la.runners.client.widget.form.field.TimePickerField;
import com.la.runners.client.widget.form.field.converter.UnitConverter;
import com.la.runners.shared.Profile;

public class CustomForm extends Composite {

    private FlowPanel panel = new FlowPanel();
    private Label footer;
    
    private Context context;
    
    public CustomForm(Context context, String title) {
        this.context = context;
        panel = new FlowPanel();
        addLabel(title, Resources.INSTANCE.form().editorHeader());
        initWidget(panel);
        setStyleName(Resources.INSTANCE.form().editor());
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

    protected Profile profile() {
        return context.getProfile();
    }

    protected UnitConverter unitConverter() {
        return context.getUnitConverter();
    }
    
    private void addLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyleName(style);
        panel.add(label);
    }
    
    protected FormField addField(FormField field) {
        panel.add(field);
        return field;
    }
    
    protected void addButton(String text, ClickHandler clickHandler) {
        Button b = new Button(text);
        b.setStyleName(Resources.INSTANCE.form().editorButton());
        b.addClickHandler(clickHandler);
        panel.add(b);
    }
    
    protected void addSubtitle(String text) {
        addLabel(text, Resources.INSTANCE.form().editorSubTitle());
    }
    
    protected void showMessage(String message) {
        if(footer == null) {
            footer = new Label();
            footer.setStyleName(Resources.INSTANCE.form().editorFooter());
            panel.add(footer);
        }
        footer.setText(message);
    }
    
    protected void addFooterForMessages() {
        showMessage("");
    }
    
    protected TextBoxField addTextBoxField(String name) {
        return (TextBoxField)addField(new TextBoxField(name));
    }

    protected NumericMandatoryBoxField addNumericMandatoryBoxField(String name) {
        return (NumericMandatoryBoxField)addField(new NumericMandatoryBoxField(name, context));
    }

    protected CheckBoxField addCheckBoxField(String name) {
        return (CheckBoxField)addField(new CheckBoxField(name));
    }
    
    protected ListBoxField addListBoxField(String name, List<String> list) {
        return (ListBoxField)addField(new ListBoxField(name, list));
    }
    
    protected MandatoryTextBoxField addMandatoryTextBoxField(String name) {
        return (MandatoryTextBoxField)addField(new MandatoryTextBoxField(name, context));
    }

    protected TextAreaField addTextAreaField(String name) {
        return (TextAreaField)addField(new TextAreaField(name));
    }

    protected TimePickerField addTimePickerField(String name, Date defaultValue) {
        return (TimePickerField)addField(new TimePickerField(name, defaultValue));
    }

    protected DatePickerField addDatePickerField(String name, Date defaultValue) {
        return (DatePickerField)addField(new DatePickerField(name, defaultValue));
    }
    
}

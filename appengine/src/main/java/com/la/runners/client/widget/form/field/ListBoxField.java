package com.la.runners.client.widget.form.field;

import java.util.List;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.la.runners.client.res.Resources;

public class ListBoxField extends FormField {

    private ListBox field;
    
    public ListBoxField(String name, List<String> values, String debugId) {
        super(null, debugId);
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(new Label(name + LABEL_SEPARATOR));
        panel.setStyleName(Resources.INSTANCE.form().editorListBoxContainer());
        field = new ListBox();
        for(String value : values) {
            field.addItem(value);
        }
        field.setStyleName(Resources.INSTANCE.form().editorListBox());
        panel.add(field);
        setField(panel);
    }

    @Override
    public Integer getValue() {
        return field.getSelectedIndex();
    }

    @Override
    public void setValue(Object value) {
        if(value == null) {
            field.setSelectedIndex(0);
        } else {
            field.setSelectedIndex((Integer)value);
        }
    }

    @Override
    public void reset() {
    }

}

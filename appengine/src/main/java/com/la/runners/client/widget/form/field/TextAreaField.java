package com.la.runners.client.widget.form.field;

import com.google.gwt.user.client.ui.TextArea;
import com.la.runners.client.res.Resources;

public class TextAreaField extends FormField {

    private TextArea textArea;
    
    public TextAreaField(String name, String debugId) {
        super(name, debugId);
        textArea = new TextArea();
        textArea.setStyleName(Resources.INSTANCE.form().editorTextArea());
        setField(textArea);
    }

    @Override
    public String getValue() {
        return textArea.getValue();
    }

    @Override
    public void setValue(Object value) {
        if(value == null) {
            textArea.setValue(EMPTY_LABEL);
        }
        textArea.setValue((String)value);
    }

    @Override
    public void reset() {
        setValue(null);
    }
}

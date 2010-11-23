package com.la.runners.client.widget.form.field;

import com.la.runners.client.Context;


public class MandatoryTextBoxField extends TextBoxField {
    
    protected Context context;
    
    public MandatoryTextBoxField(String name, Context context, String debugId) {
        super(name, debugId);
        this.context = context;
    }

    @Override
    public boolean isValid() {
        if(isEmpty()) {
            showValidationError(context.strings.validationMandatoryField());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

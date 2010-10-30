package com.la.runners.client.widget.form.field;

import com.la.runners.client.Context;


public class MandatoryTextBoxField extends TextBoxField {
    
    private Context context;
    
    public MandatoryTextBoxField(String name, Context context) {
        super(name);
        this.context = context;
    }

    @Override
    public boolean isValid() {
        boolean valid = isNotEmpty();
        if(!valid) {
            showValidationError(context.strings.validationMandatoryField());
        }
        return valid;
    };
}

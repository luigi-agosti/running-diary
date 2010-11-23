package com.la.runners.client.widget.form.field;

import com.la.runners.client.Context;

public class NumericMandatoryBoxField extends MandatoryTextBoxField {

    public NumericMandatoryBoxField(String name, Context context, String debugId) {
        super(name, context, debugId);
    }

    @Override
    public boolean isValid() {
        if(super.isValid()) {
            if(isNumeric()) {
                return Boolean.TRUE;
            }
            showValidationError(context.strings.validationNumericField());
        }
        return Boolean.FALSE;
    };
    
    protected boolean isNumeric() {
        String obj = getValue();
        try {
            Integer.parseInt(obj);
            return Boolean.TRUE;
        } catch(NumberFormatException nfe) {
            return Boolean.FALSE;
        }
    }
    
}

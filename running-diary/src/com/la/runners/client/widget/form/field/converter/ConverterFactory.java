package com.la.runners.client.widget.form.field.converter;

import com.la.runners.shared.Profile;

public class ConverterFactory {
    
    public static final UnitConverter getUnitConvert(Profile profile) {
        if(profile == null || profile.getUnitSystem() == null || profile.getUnitSystem() == 0) {
            return new InternationalConverter();
        } else if(profile.getUnitSystem() == 1) {
            return new ImperialConverter();
        } else if(profile.getUnitSystem() == 2) {
            return new AmericanConverter();
        } else {
            return new InternationalConverter();
        }
    }

}

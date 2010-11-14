package com.la.runners.client.widget.form.field.converter;

import com.la.runners.client.res.Strings;

public interface UnitConverter {

    Long customUnitDistance(Long value);
    
    Long indipendentUnitDistance(Long value);

    String customUnitSpeed(Long value);
    
    Long indipendentUnitSpeed(Long value);

    String getDistanceUnit(Strings strings);

    String getSpeedUnit(Strings strings);
    
    String metersToCustom(Double value);

}

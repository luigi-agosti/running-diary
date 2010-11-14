package com.la.runners.client.widget.form.field.converter;

import com.la.runners.client.res.Strings;

public class InternationalConverter extends BaseConverter implements UnitConverter {

    private static final int CENTIMETERS_TO_METERS_FACTOR = 100;
    
    private static final double E6SPEED_CENTIMETER_TO_KM_HOUR = 36D/1000000D;
    
    @Override
    public Long indipendentUnitDistance(Long value) {
        return new Long(value)*CENTIMETERS_TO_METERS_FACTOR;
    }
    
    @Override
    public Long customUnitDistance(Long value) {
        return Long.valueOf(value / CENTIMETERS_TO_METERS_FACTOR);
    }

    @Override
    public String customUnitSpeed(Long value) {
        return formatDoubleTo2Decimals(Double.valueOf(value * E6SPEED_CENTIMETER_TO_KM_HOUR));
    }

    @Override
    public Long indipendentUnitSpeed(Long value) {
        return value;
    }

    @Override
    public String getDistanceUnit(Strings strings) {
        return strings.distanceInternational();
    }

    @Override
    public String getSpeedUnit(Strings strings) {
        return strings.speedInternational();
    }

    @Override
    public String metersToCustom(Double value) {
        if(value == null) {
            value = 0D;
        }
        return "" + value.longValue();
    }
    
    

}

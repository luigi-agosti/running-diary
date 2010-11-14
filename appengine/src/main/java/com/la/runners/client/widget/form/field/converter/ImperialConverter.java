package com.la.runners.client.widget.form.field.converter;

import com.la.runners.client.res.Strings;

public class ImperialConverter extends BaseConverter implements UnitConverter {

    private static final double CENTIMETERS_TO_YEARD_FACTOR = 100*0.9144;

    private static final double METERS_TO_YEARD_FACTOR = 1.0936133;
    
    private static final double E6SPEED_CENTIMETER_TO_MILE_HOUR = (36D/1000000D)/1.609334;
    
    @Override
    public Long indipendentUnitDistance(Long value) {
        return Math.round(new Long(value)*CENTIMETERS_TO_YEARD_FACTOR);
    }
    
    @Override
    public Long customUnitDistance(Long value) {
        return Math.round(value / CENTIMETERS_TO_YEARD_FACTOR);
    }

    @Override
    public String customUnitSpeed(Long value) {
        return formatDoubleTo2Decimals(Double.valueOf(value * E6SPEED_CENTIMETER_TO_MILE_HOUR));
    }

    @Override
    public Long indipendentUnitSpeed(Long value) {
        return value;
    }

    @Override
    public String getDistanceUnit(Strings strings) {
        return strings.distanceImperial();
    }

    @Override
    public String getSpeedUnit(Strings strings) {
        return strings.speedImperial();
    }
    
    @Override
    public String metersToCustom(Double value) {
        if(value == null) {
            value = 0D;
        }
        return "" + (new Double(value * METERS_TO_YEARD_FACTOR).longValue());
    }

}

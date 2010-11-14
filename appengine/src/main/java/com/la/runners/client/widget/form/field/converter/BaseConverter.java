package com.la.runners.client.widget.form.field.converter;

import com.google.gwt.i18n.client.NumberFormat;


/**
 * Some util fact to remember 
 * 
 * - distance is stored as centimeters
 * - speed is calculated millisec and centimeters  in E6
 * 
 * @author luigi.agosti
 *
 */
public abstract class BaseConverter implements UnitConverter {
	
	private static final NumberFormat DECIMAL_FORMATTER = NumberFormat.getFormat("#.##");
	
	protected String formatDoubleTo2Decimals(double value) {
		return DECIMAL_FORMATTER.format(value);
	}
  
}

package com.la.runners.client.widget.custom;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ListBox;

public class YearListBox extends ListBox {
    
    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("yyyy");
    
    public static final int END_YEAR = new Integer(YEAR_FORMATTER.format(new Date()));

    private static final int BEGIN_YEAR = 1979;
    
    public YearListBox() {
        for(int i = END_YEAR; i >= BEGIN_YEAR; i--) {
            addItem("" + i);
        }
    }
    
    public void setCurrentYear(){
        setSelectedIndex(0);
    }
    
    public void setYear(int year) {
        if(BEGIN_YEAR <  year && year < END_YEAR) {
            setSelectedIndex(END_YEAR-year);
        } else {
            setCurrentYear();
        }
    }
    
    public int getYear() {
        return Integer.parseInt(getValue(getSelectedIndex())); 
    }

}

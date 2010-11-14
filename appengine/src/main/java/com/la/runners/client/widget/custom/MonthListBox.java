package com.la.runners.client.widget.custom;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ListBox;

public class MonthListBox extends ListBox {
    
    private static final DateTimeFormat MONTH_FORMATTER = DateTimeFormat.getFormat("M");
    
    private static final List<String> MONTHS_LIST = Arrays.asList("January", "February", "March", "April", 
            "May", "June", "July", "August", "September", "October", "November", "December");
    
    private static final String EMPTY = "";
    
    public MonthListBox() {
        int index = 0;
        for(String label : MONTHS_LIST) {
            addItem(label, EMPTY + index);
        }
    }
    
    public void setCurrentMonth(){
        setMonth(Integer.valueOf(MONTH_FORMATTER.format(new Date())));
    }
    
    /**
     * Return a value between 1-12
     * @return
     */
    public void setMonth(int month) {
        if(0 < month && month < 13) {
            setSelectedIndex(month - 1);
        } else {
            setCurrentMonth();
        }
    }
    
    /**
     * Return a value between 1-12
     * @return
     */
    public int getMonth() {
        return getSelectedIndex() + 1;
    }

}

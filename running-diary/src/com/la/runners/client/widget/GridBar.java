package com.la.runners.client.widget;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.la.runners.client.event.RunListUpdateEvent;

public class GridBar extends Composite {

    private static final DateTimeFormat YEAR_FORMATTER = DateTimeFormat.getFormat("yyyy");
    private static final DateTimeFormat MONTH_FORMATTER = DateTimeFormat.getFormat("MMM");
    
    private static final List<String> MONTHS_LIST = Arrays.asList("January", "February", "March", "April", 
            "May", "June", "July", "August", "September", "October", "November", "December");
    
    private static final int BEGIN_YEAR = 1979;
    
    private static final int END_YEAR = new Integer(YEAR_FORMATTER.format(new Date()));

    private HandlerManager eventBus;
    
    private FlowPanel panel;
    
    private Label messageLabel;
    
    private ListBox monthListBox;
    
    private ListBox yearListBox;
    
    public GridBar(HandlerManager eventBus) {
        this(eventBus, false);
    }
    
    public GridBar(HandlerManager eventBus, boolean isBottom) {
        this.eventBus = eventBus;
        yearListBox = new ListBox();
        for(int i = END_YEAR; i >= BEGIN_YEAR; i--) {
            yearListBox.addItem("" + i);
        }
        yearListBox.setSelectedIndex(0);
        yearListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                onSearchFilterChanges();
            }
        });
        String currentMonth = MONTH_FORMATTER.format(new Date());
        monthListBox = new ListBox();
        int index = 0;
        for(String value : MONTHS_LIST) {
            monthListBox.addItem(value, "" + index);
            if(value.startsWith(currentMonth)) {
                monthListBox.setSelectedIndex(index);
            }
            index++;
        }
        monthListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                onSearchFilterChanges();
            }
        });
        panel = new FlowPanel();
        messageLabel = new Label();
        panel.add(messageLabel);
        panel.add(yearListBox);
        panel.add(monthListBox);
        
        initWidget(panel);
        if(isBottom) {
            setStyleName("GridBar-bottom");
        } else {
            setStyleName("GridBar-top");
        }
    }
    
    private void onSearchFilterChanges() {
        eventBus.fireEvent(new RunListUpdateEvent(getMonth(), getYear()));
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public void setYear(Integer year) {
        if(year == null) {
            return;
        }
        yearListBox.setSelectedIndex(END_YEAR-year);
    }

    public void setMonth(Integer month) {
        if(month == null) {
            return;
        }
        monthListBox.setSelectedIndex(month);
    }
    
    public Integer getYear() {
        return Integer.parseInt(yearListBox.getValue(yearListBox.getSelectedIndex()));
    }

    public Integer getMonth() {
        return Integer.parseInt(monthListBox.getValue(monthListBox.getSelectedIndex()));
    }
    
}

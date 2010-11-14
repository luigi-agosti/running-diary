package com.la.runners.client.widget.grid.toolbar;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.la.runners.client.event.DeleteRunEvent;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.client.widget.custom.MonthListBox;
import com.la.runners.client.widget.custom.YearListBox;

public class RunGridBar extends MessageBar {

    private HandlerManager eventBus;
    
    private MonthListBox monthListBox;
    
    private YearListBox yearListBox;
    
    public RunGridBar(HandlerManager eventBus) {
        this(eventBus, false);
    }
    
    public RunGridBar(HandlerManager _eventBus, boolean isBottom) {
        super(isBottom);
        this.eventBus = _eventBus;
        Button btn = new Button("Delete selection");
        btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new DeleteRunEvent());                
            }
        });
        panel.add(btn);
        yearListBox = new YearListBox();
        monthListBox = new MonthListBox();
        panel.add(yearListBox);
        monthListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                onSearchFilterChanges();
            }
        });
        panel.add(monthListBox);
        yearListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                onSearchFilterChanges();
            }
        });
    }
    
    private void onSearchFilterChanges() {
        eventBus.fireEvent(new RunListUpdateEvent(getMonth(), getYear()));
    }

    public void setYear(int year) {
        yearListBox.setYear(year);
    }

    public void setMonth(int month) {
        monthListBox.setMonth(month);        
    }
    
    public int getYear() {
        return yearListBox.getYear();
    }

    public int getMonth() {
        return monthListBox.getMonth();
    }
    
}

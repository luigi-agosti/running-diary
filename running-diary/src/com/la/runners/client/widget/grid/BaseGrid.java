package com.la.runners.client.widget.grid;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.Context;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.res.Strings;
import com.la.runners.client.res.Styles;
import com.la.runners.client.widget.form.field.converter.UnitConverter;
import com.la.runners.client.widget.grid.toolbar.MessageBar;
import com.la.runners.shared.Profile;

public abstract class BaseGrid extends Composite {

    protected FlexTable grid;
    
    private Context context;
    
    protected MessageBar topBar;
    
    protected MessageBar bottomBar;
    
    private FlowPanel container;
    
    public BaseGrid(Context context) {
        this.context = context;
        container = new FlowPanel();
        topBar = getTopBar();
        container.add(topBar);

        grid = new FlexTable();
        grid.setStyleName(Styles.Grid.gridTable);
        container.add(grid);
        
        bottomBar = getBottomBar();
        container.add(bottomBar);
        
        initWidget(container);
        setMainColorStyle(Styles.Grid.gridGreen);
    }
    
    protected Strings strings() {
        return context.strings;
    }
    
    protected ServiceAsync service() {
        return context.getService();
    }
    
    protected HandlerManager eventBus() {
        return context.getEventBus();

    }
    protected UnitConverter unitConverter() {
        return context.getUnitConverter();
    }

    protected Profile profile() {
        return context.getProfile();
    }
    
    protected void showMessage(String message) {
        topBar.showMessage(message);
        bottomBar.showMessage(message);
    }
    
    protected Label createLabel(Object obj, String style) {
        String c = ""; 
        if(obj instanceof String) {
            c = (String)obj;
        } else if(obj instanceof Long) {
            c = ((Long)obj).toString();
        } else if(obj instanceof Integer) {
            c = ((Integer)obj).toString();
        }
        Label l = new Label((String)c);
        l.setStyleName(style);
        return l;
    }
    
    protected MessageBar getBottomBar() {
        return new MessageBar(true);
    }
    
    protected MessageBar getTopBar() {
        return new MessageBar();
    }

    public void setMainColorStyle(String mainColorStyle) {
        setStyleName(Styles.Grid.grid);
        addStyleName(mainColorStyle);
    }

}

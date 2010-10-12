package com.la.runners.client.widget.grid;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.Constants;
import com.la.runners.client.ServiceAsync;
import com.la.runners.client.widget.grid.toolbar.MessageBar;

public abstract class BaseGrid extends Composite {

    protected FlexTable grid;
    
    protected ServiceAsync service;
    
    protected MessageBar topBar;
    
    protected MessageBar bottomBar;
    
    private FlowPanel container;
    
    public BaseGrid(ServiceAsync service) {
        this.service = service;
        container = new FlowPanel();
        topBar = getTopBar();
        container.add(topBar);

        grid = new FlexTable();
        grid.setStyleName(Constants.Style.gridTable);
        container.add(grid);
        
        bottomBar = getBottomBar();
        container.add(bottomBar);
        
        initWidget(container);
        setStyleName(Constants.Style.grid);
    }
    
    protected void showMessage(String message) {
        topBar.showMessage(message);
        bottomBar.showMessage(message);
    }
    
    protected Label createLabel(String content, String style) {
        Label l = new Label(content);
        l.setStyleName(style);
        return l;
    }
    
    protected MessageBar getBottomBar() {
        return new MessageBar(true);
    }
    
    protected MessageBar getTopBar() {
        return new MessageBar();
    }
}

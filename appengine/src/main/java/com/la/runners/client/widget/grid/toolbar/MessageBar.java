package com.la.runners.client.widget.grid.toolbar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.la.runners.client.res.ResourceBundle;

public class MessageBar extends Composite {

    protected FlowPanel panel;
    
    private Label messageLabel;
    
    public MessageBar() {
        this(false);
    }
    
    public MessageBar(boolean isBottom) {
        panel = new FlowPanel();
        messageLabel = new Label();
        panel.add(messageLabel);
        
        initWidget(panel);
        if(isBottom) {
            setStyleName(ResourceBundle.INSTANCE.grid().barBottom());
        } else {
            setStyleName(ResourceBundle.INSTANCE.grid().barTop());
        }
    }
    
    public void showMessage(String message) {
        messageLabel.setText(message);
    }
    
}

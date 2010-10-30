package com.la.runners.client.widget.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class CenteredDialog extends DialogBox {
    
    private FlowPanel panel;
    
    public CenteredDialog() {
        setGlassEnabled(Boolean.TRUE);
        setAnimationEnabled(Boolean.TRUE);
        panel = new FlowPanel();
        panel.add(new Button("Close", new ClickHandler() {
            public void onClick(ClickEvent event) {
                hide();
            }
        }));
    }
    
    @Override
    public void add(Widget w) {
        add(w);
    }
}

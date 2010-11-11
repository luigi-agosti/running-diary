package com.la.runners.client.widget.dialog;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class CenteredDialog extends DialogBox {
    
    private FlowPanel panel;
    
    public CenteredDialog() {
        setGlassEnabled(Boolean.TRUE);
        setAnimationEnabled(Boolean.TRUE);
        panel = new FlowPanel();
        super.add(panel);
    }
    
    @Override
    public void add(Widget w) {
        panel.add(w);
    }
    
}

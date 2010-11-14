package com.la.runners.client.widget.dialog;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.la.runners.client.res.Resources;

public class CenteredDialog extends DialogBox {
    
    private FlowPanel content;
    private FlowPanel toolbar;
    
    public CenteredDialog() {
        setGlassEnabled(Boolean.TRUE);
        setAnimationEnabled(Boolean.TRUE);
        FlowPanel panel = new FlowPanel();
        content = new FlowPanel();
        content.setStyleName(Resources.INSTANCE.dialog().content());
        toolbar = new FlowPanel();
        toolbar.setStyleName(Resources.INSTANCE.dialog().toolbar());
        panel.add(content);
        panel.add(toolbar);
        super.add(panel);
    }
        
    public Button addToolbarButton(Button w) {
        toolbar.add(w);
        return w;
    }
    
    @Override
    public void add(Widget w) {
        content.add(w);
    }
    
}

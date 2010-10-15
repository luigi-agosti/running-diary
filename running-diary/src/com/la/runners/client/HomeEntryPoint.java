
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.widget.form.RunEditor;
import com.la.runners.client.widget.grid.RunGrid;

public class HomeEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        Context context = new Context();
        RunEditor runEditor = new RunEditor(context);
        RunGrid runGrid = new RunGrid(context);
        FlowPanel panel = new FlowPanel();
        panel.add(runGrid);
        panel.add(runEditor);
        panel.setStyleName(Styles.Form.entryPoint);
        RootPanel.get(context.strings.gwtHook()).add(panel);
    }

}

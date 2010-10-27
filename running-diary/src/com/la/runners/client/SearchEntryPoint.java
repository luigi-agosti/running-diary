
package com.la.runners.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.la.runners.client.res.Styles;
import com.la.runners.client.widget.form.SearchForm;
import com.la.runners.client.widget.grid.SearchGrid;

public class SearchEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        FlowPanel panel = new FlowPanel();
        Context context = new Context();
        panel.add(new SearchGrid(context));
        panel.add(new SearchForm(context));
        panel.setStyleName(Styles.Form.entryPoint);
        RootPanel.get(context.strings.gwtHook()).add(panel);
    }

}


package com.la.runners.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.shared.Run;

public class EditorEntryPoint implements EntryPoint {

    private final EditorServiceAsync editorService = GWT.create(EditorService.class);

    private static final String GWT_HOOK_ID = "gwtHook";

    private Button submit = new Button("submit");

    private Button load = new Button("load run");

    private Button reset = new Button("reset run");

    private Label message = new Label();
    
    private RunEditor runEditor = new RunEditor();

    @Override
    public void onModuleLoad() {
        FlowPanel panel = new FlowPanel();
        panel.add(new Label("Edit"));
        panel.add(id);
        reset.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                reset();
            }
        });
        panel.add(reset);
        load.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String stringId = id.getText();
                Long id = null;
                if (stringId != null) {
                    id = Long.valueOf(stringId);
                }
                editorService.get(id, new AsyncCallback<Run>() {
                    @Override
                    public void onSuccess(Run result) {
                        if (result != null) {
                            load(result);
                            message.setText("success loading page");
                        } else {
                            message.setText("success loading page, but is null");
                        }
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        message.setText("failure loading page");
                    }
                });
            }
        });
        panel.add(load);
        addWidget(panel);
        panel.add(new Label("Edit"));

        panel.add(new Label("Save"));
        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editorService.save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        message.setText("success in saving page");
                        reset();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        message.setText("failure in saving page");
                        reset();
                    }
                });
            }
        });
        panel.add(submit);
        panel.add(message);
        RootPanel.get(GWT_HOOK_ID).add(panel);
    }

}

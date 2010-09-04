
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

    private Run run;

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
    
    /**
     * Specific fields for the manipulated model
     */

    private TextBox id = new TextBox();

    private TextBox date = new TextBox();

    private TextBox distance = new TextBox();

    private TextBox note = new TextBox();

    private TextBox time = new TextBox();

    private void addWidget(FlowPanel panel) {
        panel.add(new Label("Date"));
        panel.add(date);
        panel.add(new Label("Distance"));
        panel.add(distance);
        panel.add(new Label("Note"));
        panel.add(note);
        panel.add(new Label("Time"));
        panel.add(time);
    }

    private void load(Run run) {
        this.run = run;
        id.setText("" + run.getId());
        date.setText("" + run.getDate());
        distance.setText("" + distance);
        note.setText("" + run.getNote());
        time.setText("" + run.getTime());
    }

    private void reset() {
        run = new Run();
        id.setText("");
        date.setText("");
        distance.setText("");
        note.setText("");
        time.setText("");
    }

    private Run get() {
        if (run == null) {
            run = new Run();
        }
        //TODO
        run.setDate(new Date());
        run.setDistance(Double.valueOf(distance.getText()));
        run.setNote(note.getText());
        run.setTime(Long.valueOf(time.getText()));
        return run;
    }

}

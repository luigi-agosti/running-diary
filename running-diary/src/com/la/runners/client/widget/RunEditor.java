package com.la.runners.client.widget;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.client.EditorServiceAsync;
import com.la.runners.client.event.RunListUpdateEvent;
import com.la.runners.shared.Run;

public class RunEditor extends Composite {

    private static final Label DATE_LABEL = new Label("Date");
    private static final Label DISTANCE_LABEL = new Label("Distance");
    private static final Label NOTE_LABEL = new Label("Note");
    private static final Label TIME_LABEL = new Label("Time");
    private static final Label TITLE = new Label("Run Editor"); 
    private static final Label FOOTER = new Label(" "); 
    
    static {
        DATE_LABEL.setStyleName("RunEditorLabel");
        DISTANCE_LABEL.setStyleName("RunEditorLabel");
        NOTE_LABEL.setStyleName("RunEditorLabel");
        TIME_LABEL.setStyleName("RunEditorLabel");
        TITLE.setStylePrimaryName("RunEditorTitle");
        FOOTER.setStylePrimaryName("RunEditorFooter");
    }
    
    private Button submit = new Button("Save"); {
        submit.setStyleName("RunEditorButton");
        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        FOOTER.setText("Successful saved!");
                        eventBus.fireEvent(new RunListUpdateEvent());
                        reset();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        FOOTER.setText("Umm, something wrong!");
                    }
                });
            }
        });
    }
    
    private FlowPanel panel;
    
    private TextBox id = new TextBox();
    private TextBox date = new TextBox();
    private TextBox distance = new TextBox();
    private TextBox note = new TextBox();
    private TextBox time = new TextBox();
    {
        id.setStyleName("RunEditorInput");
        date.setStyleName("RunEditorInput");
        distance.setStyleName("RunEditorInput");
        note.setStyleName("RunEditorInput");
        time.setStyleName("RunEditorInput");
    }
    
    private HandlerManager eventBus;
    
    private EditorServiceAsync service;
    
    private Run run;
    
    public RunEditor(HandlerManager eventBus, EditorServiceAsync _service) {
        this.service = _service;
        this.eventBus = eventBus;
        panel = new FlowPanel();
        panel.add(TITLE);
        panel.add(DATE_LABEL);
        panel.add(date);
        panel.add(DISTANCE_LABEL);
        panel.add(distance);
        panel.add(NOTE_LABEL);
        panel.add(note);
        panel.add(TIME_LABEL);
        panel.add(time);
        panel.add(submit);
        panel.add(FOOTER);
        initWidget(panel);
        setStyleName("RunEditor");
    }
    
    public void load(Run run) {
        this.run = run;
        id.setText("" + run.getId());
        //date.setText("" + run.getDate());
        distance.setText("" + run.getDistance());
        note.setText("" + run.getNote());
        time.setText("" + run.getTime());
    }

    public void reset() {
        run = new Run();
        id.setText("");
        date.setText("");
        distance.setText("");
        note.setText("");
        time.setText("");
    }
    
    public Run get() {
        if (run == null) {
            run = new Run();
        }
        //TODO
        //run.setDate(new Date());
        run.setYear(2010);
        run.setMonth(10);
        run.setDay(13);
        run.setDate(new Date());
        run.setDistance(Double.valueOf(distance.getText()));
        run.setNote(note.getText());
        run.setTime(Long.valueOf(time.getText()));
        return run;
    }
    
}

package com.la.runners.client;

import java.util.Date;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.la.runners.shared.Run;

public class RunEditor extends Composite {

    private FlowPanel panel;
    
    private Run run;
    
    private TextBox id = new TextBox();
    private TextBox date = new TextBox();
    private TextBox distance = new TextBox();
    private TextBox note = new TextBox();
    private TextBox time = new TextBox();
    
    public RunEditor() {
        panel = new FlowPanel();
        panel.add(new Label("Date"));
        panel.add(date);
        panel.add(new Label("Distance"));
        panel.add(distance);
        panel.add(new Label("Note"));
        panel.add(note);
        panel.add(new Label("Time"));
        panel.add(time);
    }
    
    public void load(Run run) {
        this.run = run;
        id.setText("" + run.getId());
        date.setText("" + run.getDate());
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
        run.setDate(new Date());
        run.setDistance(Double.valueOf(distance.getText()));
        run.setNote(note.getText());
        run.setTime(Long.valueOf(time.getText()));
        return run;
    }
    
}

package com.la.runners.client.widget.grid;

import com.google.gwt.user.client.ui.CheckBox;

public class ExtraCheckBox extends CheckBox {

    private Long id;
    
    public ExtraCheckBox(Long id) {
        super();
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
}

package com.la.runners.client.res;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.la.runners.client.res.style.Form;
import com.la.runners.client.res.style.Grid;
import com.la.runners.client.res.style.Map;

public interface ResourceBundle extends ClientBundle {
    
    public static final ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);
    
    @Source("com/la/runners/client/res/style/map.css")
    public Map map();
    
    @Source("com/la/runners/client/res/style/form.css")
    public Form form();

    @Source("com/la/runners/client/res/style/grid.css")
    public Grid grid();

}

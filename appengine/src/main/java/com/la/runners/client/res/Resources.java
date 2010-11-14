package com.la.runners.client.res;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.la.runners.client.res.style.Dialog;
import com.la.runners.client.res.style.Form;
import com.la.runners.client.res.style.Chart;
import com.la.runners.client.res.style.Grid;
import com.la.runners.client.res.style.Map;

public interface Resources extends ClientBundle {
    
    public static final Resources INSTANCE = GWT.create(Resources.class);
    
    @Source("com/la/runners/client/res/style/map.css")
    public Map map();
    
    @Source("com/la/runners/client/res/style/form.css")
    public Form form();

    @Source("com/la/runners/client/res/style/grid.css")
    public Grid grid();
    
    @Source("com/la/runners/client/res/style/dialog.css")
    public Dialog dialog();
    
    @Source("com/la/runners/client/res/style/chart.css")
    public Chart chart();

    @Source("com/la/runners/client/res/image/locationIcon.png")
    ImageResource locationIcon();
    
    @Source("com/la/runners/client/res/image/locationIconShadow.png")
    ImageResource locationIconShadow();


}

package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.UserWithProfileSetPageTest;
import com.la.runners.client.widget.form.RunForm;
import com.la.runners.client.widget.grid.RunGrid;

public class HomeMapTest extends UserWithProfileSetPageTest {

    public HomeMapTest() {
        super(Constants.Jsp.home);
    }

    @Override
    protected String[] getIds() {
        return new String[] {RunForm.ID, RunGrid.ID};
    }
    
    @Test
    public void saveMapInNewRun() {
        //
        
    }
    
    @Test
    public void loadMapFromRun() {
        //
        
    }
    
    @Test
    public void editMapFromExistingRun() {
        //
        
    }
    
}

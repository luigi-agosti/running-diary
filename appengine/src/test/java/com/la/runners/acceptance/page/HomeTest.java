package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.AuthenticatedPageTest;
import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.framework.AssertThatPageHas;
import com.la.runners.acceptance.framework.Session;
import com.la.runners.client.widget.dialog.NewProfileDialog;
import com.la.runners.client.widget.form.RunForm;
import com.la.runners.client.widget.grid.RunGrid;

public class HomeTest extends AuthenticatedPageTest {

    public HomeTest() {
        super(Constants.Jsp.home);
    }

    @Test
    public void askedToSetTheProfile() {
        assertThatPageHas.elementWithId(gwt(NewProfileDialog.ID));
    }

    @Test
    public void beAbleToSetProfileAndLoadTheHome() {
        assertThatPageHas.elementWithId(gwt(NewProfileDialog.ID));
        Method.saveNewProfileAndVerify(session, RunForm.ID, RunGrid.ID);
    }
    
    public static class Method {
        
        public static final void saveNewProfileAndVerify(Session session, String...ids) {
            session.clickById(gwt(NewProfileDialog.CONTINUE_ID));
            for(String id: ids) {
                AssertThatPageHas.elementWithId(session, gwt(id));
            }
        }
        
    }
    
}

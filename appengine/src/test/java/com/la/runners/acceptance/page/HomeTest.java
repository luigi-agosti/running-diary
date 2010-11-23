package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.AuthenticatedPageTest;
import com.la.runners.acceptance.Constants;
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
        session.shouldHaveElementWithGwtDebugId(NewProfileDialog.ID);
    }

    @Test
    public void beAbleToSetProfileAndLoadTheHome() {
        session.shouldHaveElementWithGwtDebugId(NewProfileDialog.ID);
        Method.saveNewProfileAndVerify(session, RunForm.ID, RunGrid.ID);
//        WebElement element = null;
//        while(element == null) {
//            element = session.getElementByIdWithoutFail(RunGrid.ID);
//        }
        //session.implicitWait(1);
    }
    
    public static class Method {
        
        public static final void saveNewProfileAndVerify(Session session, String...ids) {

            session.clickByDebugId(NewProfileDialog.CONTINUE_ID);
            for(String id: ids) {
                session.shouldHaveElementWithGwtDebugId(id);
            }
        }
        
    }
    
}

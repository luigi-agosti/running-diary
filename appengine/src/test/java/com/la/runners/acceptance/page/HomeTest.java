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
    public void shouldAskToSetABasicProfile() {
        session.shouldHaveElementWithGwtDebugId(NewProfileDialog.ID);
    }

    @Test
    public void shouldBeAbleToSetAProfileAndLoadTheHome() {
        session.shouldHaveElementWithGwtDebugId(NewProfileDialog.ID);
        //session.shouldHaveElementWithText("");
        //session.shouldHaveInputWithIdAndValue("", ""); //populate the input with the mail stripped of the domain
        //String username = "pippo";
        //session.fillInputById(username);
        Method.saveNewProfile(session);
        session.shouldHaveElementWithGwtDebugId(RunGrid.ID);
        session.shouldHaveElementWithGwtDebugId(RunForm.ID);
    }
    
    public static class Method {
        
        public static final void saveNewProfile(Session session) {
            session.clickByDebugId(NewProfileDialog.CONTINUE_ID);
            session.confirmDialog();
        }
        
    }
    
}

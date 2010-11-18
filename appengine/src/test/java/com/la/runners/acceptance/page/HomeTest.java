package com.la.runners.acceptance.page;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.la.runners.acceptance.AuthenticatedPageTest;
import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.framework.Session;
import com.la.runners.client.widget.dialog.NewProfileDialog;

public class HomeTest extends AuthenticatedPageTest {

    public HomeTest() {
        super(Constants.Jsp.home);
    }

    @Test
    public void shouldAskToSetABasicProfile() {
        session.shouldHaveElementWithId(NewProfileDialog.ID);
        session.shouldHaveElementWithText();
        session.shouldHaveInputWithIdAndValue("", "");
    }

    @Ignore
    @Test
    public void shouldBeAbleToSetAProfile() {
        session.shouldHaveElementWithId(NewProfileDialog.ID);
        session.shouldHaveElementWithText("");
        session.shouldHaveInputWithIdAndValue("", ""); //populate the input with the mail stripped of the domain
        String username = "pippo";
        session.fillInputById(username);
        session.clickById("");
        session.shouldHaveElementWithIdAndText("", username);
    }
    
    public static class Method {

        public static final void shouldAskForProfile(Session session) {
            
        }
        
        public static final void saveNewProfile() {
            
        }
        
        public static final void deleteProfile() {
            
        }
        
    }
    
}

package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.AuthenticatedPageTest;
import com.la.runners.acceptance.Constants;

public class HomeTest extends AuthenticatedPageTest {

    public HomeTest(String page) {
        super(Constants.Jsp.home);
    }

    @Test
    public void shouldAskToSetABasicProfile() {
        shouldHaveElementWithId();
        shouldHaveElementWithText();
        shouldHaveInputWithIdAndValue("", ""); //populate the input with the mail stripped of the domain
    }
    

    @Test
    public void shouldBeAbleToSetAProfile() {
        shouldHaveElementWithId();
        shouldHaveElementWithText("");
        shouldHaveInputWithIdAndValue("", ""); //populate the input with the mail stripped of the domain
        String username = "pippo";
        fillInputById(username);
        clickById("");
        shouldHaveElementWithIdAndText("", username);
    }
    
}

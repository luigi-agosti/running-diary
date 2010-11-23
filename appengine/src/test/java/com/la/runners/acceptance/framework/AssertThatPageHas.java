package com.la.runners.acceptance.framework;

import static org.junit.Assert.assertNotNull;
import junit.framework.Assert;

import org.openqa.selenium.WebElement;

public class AssertThatPageHas {

    private Session session;
    
    public AssertThatPageHas(Session session) {
        this.session = session;
    }
    
    public AssertThatPageHas elementWithIdAndValue(String id, String value) {
        WebElement element = session.findElementById(id);
        assertNotNull("Can't find element for id : " + id, element);
        return this;
    }
    
    public static final AssertThatPageHas elementWithIdAndValue(Session session, String id, String value) {
        return new AssertThatPageHas(session).elementWithIdAndValue(id, value);
    }

    public AssertThatPageHas elementWithId(String id) {
        session.findElementById(id);
        return this;
    }
    
    public AssertThatPageHas divWithText(String text) {
        session.findDivElementByText(text);
        return this;
    }
    
    public static final AssertThatPageHas elementWithId(Session session, String id) {
        return new AssertThatPageHas(session).elementWithId(id);
    }
    
    public void assertPresenceOfButton(String index, String href,
            String text) {
        WebElement element = session.findElementById(index);
        assertNotNull(element);
        Assert.assertEquals(href, element.getAttribute("href"));
        Assert.assertEquals(text, element.getText());
    }
    
}

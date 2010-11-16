
package com.la.runners.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class IndexJspTest {

    private static final String INDEXPAGE_GOTOHOME = "indexPage_goToHome";

    private static final String INDEXPAGE_SIGNOUT_LINK = "indexPage_signOutLink";

    private static final String INDEXPAGE_SINGIN_LINK = "indexPage_singInLink";

    private WebDriver driver = new FirefoxDriver();

    private void clearCurrentLogIn() {
        driver.get(Constants.Hosts.local + Constants.Jsp.index);
        if (isSignedIn()) {
            signOut();
        }
        driver.get(Constants.Hosts.local + Constants.Jsp.index);
    }
    
    @Before
    public void before() {
        clearCurrentLogIn();
    }

    @After
    public void after() {
        clearCurrentLogIn();
        driver.close();
    }

    @Test
    public void shouldBeLoggedOutByDefaultRun() {
        assertPresenceOfButton(INDEXPAGE_SINGIN_LINK, Constants.Jsp.home, "try it now!");
    }

    @Test
    public void shouldBeAbleToSignIn() {
        signInWithDefaultUser();
        assertTrue(isSignedIn());
    }
    
    @Test
    public void shouldBeAbleToGoToHomeIfLoggedIn() {
        signInWithDefaultUser();
        assertPresenceOfButton(INDEXPAGE_GOTOHOME, Constants.Jsp.home, "go to your home");
    }
    
    private WebElement assertPresenceOfButton(String index, String href, String text) {
        WebElement element = getElementById(index);
        assertNotNull(element);
        assertEquals(href, element.getAttribute("href"));
        assertEquals(text, element.getText());
        return element;
    }

    private void signInWithDefaultUser() {
        String currentUrl = driver.getCurrentUrl();
        WebElement element = getElementById(INDEXPAGE_SINGIN_LINK);
        assertNotNull(element);
        element.click();
        String url = driver.getCurrentUrl();
        assertTrue(url + " is not as expected", url.startsWith(Constants.Hosts.local + "_ah/login"));
        assertTrue(url + " is not as expected", url.endsWith(Constants.Jsp.home));
        element = getElementByXpath("//input[@type='submit' and @value='Log Out']");
        assertNotNull(element);
        element.click();
        //resetting to the beggining state
        driver.get(currentUrl);
    }

    private void signOut() {
        try {
            WebElement element = getElementById(INDEXPAGE_SIGNOUT_LINK);
            assertNotNull(element);
            element.click();
        } catch (NoSuchElementException nsee) {
            //TODO
        }
    }

    private boolean isSignedIn() {
        try {
            WebElement element = getElementById(INDEXPAGE_SIGNOUT_LINK);
            assertNotNull(element);
            return Boolean.TRUE;
        } catch (NoSuchElementException nsee) {
            return Boolean.FALSE;
        }
    }

    private WebElement getElementById(String id) {
        return driver.findElement(By.id(INDEXPAGE_SINGIN_LINK));
    }
    
    private WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

}

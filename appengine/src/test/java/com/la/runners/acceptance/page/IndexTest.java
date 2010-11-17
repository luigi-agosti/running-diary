
package com.la.runners.acceptance.page;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.la.runners.acceptance.AppEngineUserModule;
import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.framework.ChromeSession;
import com.la.runners.acceptance.framework.Session;

public class IndexTest {
    
    private static final String INDEXPAGE_GOTOHOME = "indexPage_goToHome";

    private static final String INDEXPAGE_SIGNOUT_LINK = "indexPage_signOutLink";

    private static final String INDEXPAGE_SINGIN_LINK = "indexPage_singInLink";

    private ChromeSession session = new ChromeSession(Constants.Hosts.local);
    private AppEngineUserModule userModule = new AppEngineUserModule();

    private void clearCurrentLogIn() {
        session.go(Constants.Jsp.index);
        userModule.signOut(session);
    }
    
    @Before
    public void before() {
        clearCurrentLogIn();
    }

    @After
    public void after() {
        session.end();
    }

    @Test
    public void shouldBeLoggedOutByDefaultRun() {
        session.assertPresenceOfButton(INDEXPAGE_SINGIN_LINK, Constants.Jsp.home, "try it now!");
    }

    @Test
    public void shouldBeAbleToSignIn() {
        String currentUrl = session.getRelativePath();
        session.clickById(INDEXPAGE_SINGIN_LINK);
        String url = session.getCurrentUrl();
        assertTrue(url + " is not as expected", url.startsWith(Constants.Hosts.local + "_ah/login"));
        assertTrue(url + " is not as expected", url.endsWith(Constants.Jsp.home));
        session.clickOnItemByXpath(AppEngineUserModule.LOG_IN_XPATH);
        //resetting to the beggining state
        session.go(currentUrl);
        assertTrue(Method.isSignedIn(session));
    }
    
    @Test
    public void shouldBeAbleToGoToHomeIfLoggedIn() {
        userModule.signIn(session);
        session.assertPresenceOfButton(INDEXPAGE_GOTOHOME, Constants.Jsp.home, "go to your home");
    }
    
    @Test
    public void shouldBeAbleToLogOfFromHome() {
        userModule.signIn(session);
        WebElement element = session.getElementById(INDEXPAGE_SIGNOUT_LINK);
        assertNotNull(element);
        element.click();
        assertFalse(Method.isSignedIn(session));
    }

    /**
     * Methods that are useful to other test are organized in this way for now.
     */
    public static class Method {
        public static boolean isSignedIn(Session session) {
            try {
                WebElement element = session.getElementById(INDEXPAGE_SIGNOUT_LINK);
                assertNotNull(element);
                return Boolean.TRUE;
            } catch (NoSuchElementException nsee) {
                return Boolean.FALSE;
            }
        }
    }
    
}
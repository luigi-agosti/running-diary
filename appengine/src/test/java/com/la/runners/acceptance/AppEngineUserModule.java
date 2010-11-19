package com.la.runners.acceptance;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;

import com.la.runners.acceptance.framework.Session;

public class AppEngineUserModule {
    
    public static final String LOG_IN_XPATH = "//input[@type='submit' and @value='Log In']";

    public static final String LOG_OUT_XPATH = "//input[@type='submit' and @value='Log Out']";
    
    private static final String ADMIN = "_ah/login";
    
    public void signOut(Session session) {
        signOut(session, session.getCurrentUrl());
    }
    
    public void signOut(Session session, String page) {
        try {
            session.go(ADMIN + "?continue=" + page);
            session.clickByXpath(LOG_OUT_XPATH);
        } catch (NoSuchElementException nsee) {
            Assert.fail("Can't finish the sign out procedure : " + nsee.getMessage());
        }
    }
    
    public void signIn(Session session) {
        signIn(session, session.getCurrentUrl());
    }
    
    public void signIn(Session session, String page) {
        try {
            session.go(ADMIN + "?continue=" + page);
            session.clickByXpath(LOG_IN_XPATH);
        } catch (NoSuchElementException nsee) {
            Assert.fail("Can't finish the sign in procedure : " + nsee.getMessage());
        }
    }
    
    public void tryToClearProfile(Session session) {
        try {
            String path = session.getRelativePath();
            session.go("_ah/admin/datastore?kind=Profile");
            try {
                session.clickById("allkeys");
                session.clickById("delete_button");
                session.confirmDialog();
            } catch(Throwable e) {
                //Fine if fail
            }
            session.go(path);
        } catch (NoSuchElementException nsee) {
            Assert.fail("Can't finish the sign in procedure : " + nsee.getMessage());
        }
    }
    

}

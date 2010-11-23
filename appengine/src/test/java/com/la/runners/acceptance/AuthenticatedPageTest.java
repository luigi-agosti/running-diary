package com.la.runners.acceptance;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;

import com.la.runners.acceptance.framework.ChromeSession;

public abstract class AuthenticatedPageTest {

    private static final Logger logger = Logger.getLogger(AuthenticatedPageTest.class.getName());
    protected AppEngineUserModule userModule = new AppEngineUserModule();
    protected ChromeSession session;
    private String page;
    
    public AuthenticatedPageTest(String page) {
        this.page = page;
        session = new ChromeSession(Constants.Hosts.local);
    }
    
    @Before
    public void beforeAuthentication() {
        logger.info("Authentication");
        userModule.signOut(session, "/" + page);
        userModule.signIn(session, "/" + page);
        userModule.tryToClearProfile(session);
    }

    @After
    public void afterAuthentication() {
        session.end();
    }
    
}

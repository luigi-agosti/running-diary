package com.la.runners.acceptance;

import org.junit.After;
import org.junit.Before;

import com.la.runners.acceptance.framework.ChromeSession;

public abstract class AuthenticatedPageTest {

    protected AppEngineUserModule userModule = new AppEngineUserModule();
    protected ChromeSession session;
    private String page;
    
    public AuthenticatedPageTest(String page) {
        this.page = page;
        session = new ChromeSession(Constants.Hosts.local);
    }
    
    @Before
    public void before() {
        userModule.signOut(session, "/" + page);
        userModule.signIn(session, "/" + page);
    }

    @After
    public void after() {
        session.end();
    }
    
}

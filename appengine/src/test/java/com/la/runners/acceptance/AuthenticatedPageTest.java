package com.la.runners.acceptance;

import org.junit.After;
import org.junit.Before;

import com.la.runners.acceptance.framework.ChromeSession;

public class AuthenticatedPageTest {

    protected AppEngineUserModule userModule = new AppEngineUserModule();
    protected ChromeSession session;
    private String page;
    
    public AuthenticatedPageTest(String page) {
        this.page = page;
        session = new ChromeSession(Constants.Hosts.local);
    }
    
    private void clearCurrentLogIn() {
        session.go(page);
        userModule.signOut(session);
    }
    
    @Before
    public void before() {
        clearCurrentLogIn();
        userModule.signIn(session);
    }

    @After
    public void after() {
        session.end();
    }
    
    protected void shouldHaveInputWithIdAndValue(String string, String string2) {
        // TODO Auto-generated method stub
        
    }
    
    protected void shouldHaveElementWithText() {
        // TODO Auto-generated method stub
        
    }
    
    protected void shouldHaveElementWithId() {
        // TODO Auto-generated method stub
        
    }

    protected void clickById(String string) {
        // TODO Auto-generated method stub
        
    }

    protected void fillInputById(String username) {
        // TODO Auto-generated method stub
        
    }

    protected void shouldHaveElementWithIdAndText(String string, String username) {
        // TODO Auto-generated method stub
        
    }

    protected void shouldHaveElementWithText(String string) {
        // TODO Auto-generated method stub
        
    }
    
}

package com.la.runners.acceptance.page;

import org.junit.Ignore;
import org.junit.Test;

import com.la.runners.acceptance.AppEngineUserModule;
import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.UserWithProfileSetPageTest;
import com.la.runners.acceptance.framework.ChromeSession;
import com.la.runners.acceptance.framework.Session;
import com.la.runners.client.widget.form.SearchForm;

public class SearchTest extends UserWithProfileSetPageTest {

    public SearchTest() {
        super(Constants.Jsp.search);
    }
    
    @Override
    protected String[] getIds() {
        return new String[] {};
    }
    
    @Test
    public void findUser() {
        String userToSearchFor = "pluto";
        Session plutoSession = Method.createProfileOnDifferentSession(userToSearchFor);
        plutoSession.end();
        session.setValueOnInputById(gwt(SearchForm.NICKNAME_ID), userToSearchFor);
        session.clickById(gwt(SearchForm.SEARCH));
        assertThatPageHas.divWithText(userToSearchFor);
    }
    
    @Ignore
    @Test
    public void inviteUser() {
        
    }
    
    @Ignore
    @Test
    public void receivedInvite() {
        
    }
    
    @Ignore
    @Test
    public void getFriedOnTheList() {
        
    }
    
    public static class Method {
    
        public static final Session createProfileOnDifferentSession(String nickname) {
            ChromeSession session = new ChromeSession(Constants.Hosts.local);
            AppEngineUserModule userModule = new AppEngineUserModule();
            userModule.signIn(session, Constants.Hosts.local + Constants.Jsp.profile, nickname);
            HomeTest.Method.saveNewProfileAndVerify(session);
            ProfileTest.Method.setProfileNickname(session, nickname);
            return session;
        }
    }

}

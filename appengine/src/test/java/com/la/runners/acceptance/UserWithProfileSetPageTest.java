package com.la.runners.acceptance;

import org.junit.After;
import org.junit.Before;

import com.la.runners.acceptance.page.HomeTest;

public abstract class UserWithProfileSetPageTest extends AuthenticatedPageTest {

    public UserWithProfileSetPageTest(String page) {
        super(page);
    }
    
    @Before
    public void beforeSettingProfile() {
        HomeTest.Method.saveNewProfileAndVerify(session);
    }

    @After
    public void afterSettingProfile() {
    }
    
}

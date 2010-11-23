package com.la.runners.acceptance;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;

import com.la.runners.acceptance.page.HomeTest;

public abstract class UserWithProfileSetPageTest extends AuthenticatedPageTest {

    private static final Logger logger = Logger.getLogger(UserWithProfileSetPageTest.class.getName());
    
    public UserWithProfileSetPageTest(String page) {
        super(page);
    }
    
    @Before
    public void beforeSettingProfile() {
        logger.info("Sessting profile");
        HomeTest.Method.saveNewProfileAndVerify(session, getIds());
    }

    protected abstract String[] getIds();

    @After
    public void afterSettingProfile() {
    }
    
}

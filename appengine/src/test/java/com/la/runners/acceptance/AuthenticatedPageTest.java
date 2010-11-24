package com.la.runners.acceptance;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.i18n.client.Constants.DefaultStringValue;
import com.la.runners.acceptance.framework.AssertThatPageHas;
import com.la.runners.acceptance.framework.ChromeSession;
import com.la.runners.client.res.Strings;

public abstract class AuthenticatedPageTest {

    private static final Logger logger = Logger.getLogger(AuthenticatedPageTest.class.getName());

    private static final String GWT_DEBUG_ID = "gwt-debug-";
    
    protected static Properties strings = new Properties();
    static {
        try {
            InputStream is = Strings.class.getResourceAsStream("Strings.properties");
            strings.load(is);
        } catch(Exception e) {
            logger.severe("Problem loading property file : " + e.getMessage());
        }
    }
    
    protected AppEngineUserModule userModule = new AppEngineUserModule();
    protected ChromeSession session;
    private String page;
    protected AssertThatPageHas assertThatPageHas;
    
    public AuthenticatedPageTest(String page) {
        this.page = page;
        session = new ChromeSession(Constants.Hosts.local);
        assertThatPageHas = new AssertThatPageHas(session);
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
    
    /**
     * Return the standard gwt debug id.
     * @param id
     * @return
     */
    public static final String gwt(String id) {
        return GWT_DEBUG_ID + id;
    }
    
    protected static String getString(String key) {
        return strings.getProperty(key);
    }

    protected static String getString(Class<?> clazz, String key) {
        try {
            Method method = clazz.getDeclaredMethod(key);
            DefaultStringValue defaultValue = method.getAnnotation(DefaultStringValue.class);
            return defaultValue.value();
        } catch (Exception e) {
            throw new RuntimeException("default value is not present " + e.getMessage());
        }
    }
    
    protected static final String PROFILE_FORM_SAVE_SUCCESS = "profileFormSaveSuccess";
    
}

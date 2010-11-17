package com.la.runners.acceptance.framework;

import org.openqa.selenium.firefox.FirefoxDriver;


public class FirefoxSession extends Session {
    
    public FirefoxSession(String host) {
        super(host, new FirefoxDriver());
    }

}

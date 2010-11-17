package com.la.runners.acceptance.framework;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeSession extends Session {

    public ChromeSession(String host) {
        super(host, new ChromeDriver());
    }
    
}

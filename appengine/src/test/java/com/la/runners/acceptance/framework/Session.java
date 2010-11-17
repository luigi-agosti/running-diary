package com.la.runners.acceptance.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Session {
    
    private WebDriver driver;
    
    private String host;
    
    private String stashedRelativePath;
    
    public Session(String host, WebDriver driver) {
        this.driver = driver;
        //Doesn't work properly
        //this.driver.manage().setSpeed(Speed.SLOW);
        this.host = host;
    }
    
    public WebElement getElementById(String id) {
        return driver.findElement(By.id(id));
    }
    
    public WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }
    
    public void go(String page) {
        driver.get(host + page);
    }
    
    public void end() {
        driver.close();
    }
    
    public String getRelativePath() {
        String url = driver.getCurrentUrl();
        return url.replace(host, "");
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public WebElement assertPresenceOfButton(String index, String href, String text) {
        WebElement element = getElementById(index);
        assertNotNull(element);
        assertEquals(href, element.getAttribute("href"));
        assertEquals(text, element.getText());
        return element;
    }
    
//    public void clickAndWait(WebElement element, String endUrl) {
//        element.click();
//        boolean wait = true;
//        while(wait) {
//            String urlNextPage = getRelativePath();
//            if(urlNextPage.contains(endUrl)) {
//               wait = false; 
//            }
//        }
//    }
    
    public void stashRelativePath() {
        stashedRelativePath = getRelativePath();
    }
    
    public void applayStashedRelativePath() {
        go(stashedRelativePath);
    }

    public void clickOnItemByXpath(String xpath) {
        WebElement element = getElementByXpath(xpath);
        Assert.assertNotNull(element);
        element.click();
    }
    
    public void clickById(String id) {
        WebElement element = getElementById(id);
        Assert.assertNotNull(element);
        element.click();
    }
    
}
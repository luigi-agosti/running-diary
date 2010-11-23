package com.la.runners.acceptance.framework;

import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Session {
	
	private static final Logger logger = Logger.getLogger(Session.class.getName());

	private static final int STANDARD_DELAY = 1500;
	
	private static final int CLICK_DELAY = STANDARD_DELAY;
	
	private static final int SMALL_DELAY = 100;
	
	private WebDriver driver;

	private String host;

	public Session(String host, WebDriver driver) {
		this.driver = driver;
		// Doesn't work properly
		// this.driver.manage().setSpeed(Speed.SLOW);
		this.host = host;
	}

	/**
	 * Go to the page passed as parameter. page is the relative path to
	 * the resource that is needed.
	 * @param page
	 */
	public void go(String page) {
		driver.get(host + page);
	}

	/**
	 * Close the current driver.
	 */
	public void end() {
		driver.close();
	}

	/**
	 * Return the current relative path.
	 * @return
	 */
	public String getRelativePath() {
		return getCurrentUrl().replace(host, "");
	}

	/**
	 * Get current full url.
	 * @return
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Click on a element by XPath
	 * @param xpath
	 */
	public void clickByXpath(String xpath) {
		WebElement element = getElementByXpath(xpath);
		click(element);
	}

	/**
	 * Click on a element by Id
	 * @param id
	 */
	public void clickById(String id) {
		WebElement element = findElementById(id);
		click(element);
	}

	/**
	 * Trick to overcome confirm dialog problem.
	 * Run this before executing the code that trigger the confirmation
	 * to not have it in the middle of the test.
	 */
	public void removeConfirmDialog() {
		((JavascriptExecutor) driver)
				.executeScript("window.confirm = function(msg){return true;}");
	}

	/**
	 * Fill an input element by Id.
	 * @param id
	 * @param value
	 */
	public void setValueOnInputById(String id, Object value) {
		WebElement element = findElementById(id);
		if (value instanceof String) {
			element.sendKeys((String) value);
		}
	}

	/**
	 * Find div element by text is applying xpath //div[text()='X']. 
	 * @param text
	 * @return
	 */
	public WebElement findDivElementByText(String text){
	    return findElementByXpath("//div[text() = '" + text + "']");
	}

	/**
	 * Find a element by XPath
	 * @param xpath
	 * @return
	 */
    public WebElement findElementByXpath(String xpath) {
        delay(STANDARD_DELAY);
        try {
            return getElementByXpath(xpath);
        } catch(Throwable e) {
            Assert.fail("failure to find element with xpath : " + xpath);
            throw new RuntimeException();
        }
    }
    
    /**
     * Find a element by id
     * @param id
     * @return
     */
    public WebElement findElementById(String id) {
        delay(STANDARD_DELAY);
        try {
            return driver.findElement(By.id(id));
        } catch (Exception e) {
            Assert.fail("can't find element with id : " + id);
            throw new RuntimeException();
        }
    }

    /**
     * 
     * @param string
     */
    public void dropdownSelectByDebugId(String id) {
        // TODO Auto-generated method stub
        
    }
    
    /*
     * =============================================================
     * Low level helpers
     * =============================================================
     */
    
    private void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            logger.severe(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
    private WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }
    
    private void click(WebElement element) {
        Assert.assertNotNull(element);
        delay(CLICK_DELAY);
        element.click();
        delay(SMALL_DELAY);
    }

}

package com.la.runners.acceptance.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Session {
	
	private static final Logger logger = Logger.getLogger(Session.class.getName());

	private static final int TIME_OUT = 1500;
	
	private static final int CLICK_DELAY = 1500;
	
	private static final String GWT_DEBUG_ID = "gwt-debug-";

	private WebDriver driver;

	private String host;

	private String stashedRelativePath;

	public Session(String host, WebDriver driver) {
		this.driver = driver;
		// Doesn't work properly
		// this.driver.manage().setSpeed(Speed.SLOW);
		this.host = host;
	}
	

	public WebElement getElementById(String id) {
		try {
			return findElement(By.id(id), TIME_OUT);
//			return driver.findElement();
		} catch (Exception e) {
			Assert.fail("can't find element with id : " + id);
			throw new RuntimeException();
		}
	}

	public WebElement getElementByIdWithoutFail(String id) {
		try {
			return getElementById(id);
		} catch (Exception e) {
			return null;
		}
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

	public WebElement assertPresenceOfButton(String index, String href,
			String text) {
		WebElement element = getElementById(index);
		assertNotNull(element);
		assertEquals(href, element.getAttribute("href"));
		assertEquals(text, element.getText());
		return element;
	}

	// public void clickAndWait(WebElement element, String endUrl) {
	// element.click();
	// boolean wait = true;
	// while(wait) {
	// String urlNextPage = getRelativePath();
	// if(urlNextPage.contains(endUrl)) {
	// wait = false;
	// }
	// }
	// }

	public void stashRelativePath() {
		stashedRelativePath = getRelativePath();
	}

	public void applayStashedRelativePath() {
		go(stashedRelativePath);
	}

	public void clickByXpath(String xpath) {
		WebElement element = getElementByXpath(xpath);
		Assert.assertNotNull(element);
		element.click();
	}

	public void clickById(String id) {
		WebElement element = getElementById(id);
		Assert.assertNotNull(element);
		element.click();
	}

	public void replaceConfirmDialog() {
		((JavascriptExecutor) driver)
				.executeScript("window.confirm = function(msg){return true;}");
	}

	public void fillInputById(String id, Object value) {
		WebElement element = getElementById(id);
		if (value instanceof String) {
			element.sendKeys((String) value);
		}
	}

	public void implicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Asserts
	 */
	public void shouldHaveInputWithIdAndValue(String id, String value) {
		WebElement element = getElementById(id);
		assertNotNull("Can't find element for id : " + id, element);

	}

	public void shouldHaveElementWithText() {
		// TODO Auto-generated method stub

	}

	public void shouldHaveElementWithId(String id) {
	    getElementById(id);
	}

	public void shouldHaveElementWithGwtDebugId(String id) {
		shouldHaveElementWithId(GWT_DEBUG_ID + id);
	}

	public void clickByDebugId(String id) {
	    try {
            Thread.sleep(CLICK_DELAY);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
		clickById(GWT_DEBUG_ID + id);
	}

	public void shouldHaveElementWithIdAndText(String string, String username) {
		// TODO Auto-generated method stub

	}

	public void shouldHaveElementWithText(String string) {
		// TODO Auto-generated method stub

	}

	private WebElement findElement(By by, int timeout) {
		int iSleepTime = 1000;
		for (int i = 0; i < timeout; i += iSleepTime) {
			List<WebElement> oWebElements = driver.findElements(by);
			if (oWebElements.size() > 0) {
				return oWebElements.get(0);
			} else {
				try {
					Thread.sleep(iSleepTime);
					logger.info(String
							.format("%s: Waited for %d milliseconds.[%s]",
									new java.util.Date().toString(), i
											+ iSleepTime, by));
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			}
		}

		// Can't find 'by' element. Therefore throw an exception.
		String sException = String.format(
				"Can't find %s after %d milliseconds.", by, timeout);
		throw new RuntimeException(sException);
	}

}

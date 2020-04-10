package com.wiley.selenium.helpers;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.wiley.selenium.utils.PropertyFileReader;

public class TestBase {

	private WebDriver driver = null;
	private static int timeOut = 120;
	private static int visableWaitTimeout = 120;

	public TestBase(WebDriver driver) {
		this.driver = driver;
	}

	public void Open(String key) {
		PropertyFileReader propertyFileReader = new PropertyFileReader(
				"configs" + File.separator + "config.properties");
		String url = propertyFileReader.getPropertyValue(key);
		driver.navigate().to(url);
	}

	public void Click(By byLocater) {
		WebElement element = findWebElement(byLocater);
		new WebDriverWait(driver, visableWaitTimeout).until(ExpectedConditions.visibilityOfElementLocated(byLocater));
		new WebDriverWait(driver, visableWaitTimeout).until(ExpectedConditions.elementToBeClickable(byLocater));
		element.click();
	}

	public void onMouseHover(By byLocater) {
		Actions actions = new Actions(driver);
		WebElement element = driver.findElement(byLocater);
		new WebDriverWait(driver, visableWaitTimeout).until(ExpectedConditions.visibilityOfElementLocated(byLocater));
		actions.moveToElement(element).perform();
	}

	public void Type(By byLocater, String value) {
		WebElement element = findWebElement(byLocater);
		new WebDriverWait(driver, visableWaitTimeout).until(ExpectedConditions.visibilityOfElementLocated(byLocater));
		new WebDriverWait(driver, visableWaitTimeout).until(ExpectedConditions.elementToBeClickable(byLocater));
		element.sendKeys(value);
	}

	public String getText(By byLocater) {
		WebElement element = findWebElement(byLocater);
		new WebDriverWait(driver, visableWaitTimeout).until(ExpectedConditions.visibilityOfElementLocated(byLocater));
		return element.getText();
	}

	public String getInnerHtml(By byLocater) {
		WebElement element = findWebElement(byLocater);
		new WebDriverWait(driver, visableWaitTimeout).until(ExpectedConditions.visibilityOfElementLocated(byLocater));
		return element.getAttribute("innerHTML");
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getInnerHtmlForStaleElements(By byLocater) {
		boolean staleElement = true;
		String innerHtml = null;
		while (staleElement) {
			try {
				WebElement element = findWebElement(byLocater);
				innerHtml = element.getAttribute("innerHTML");
				;
				staleElement = false;

			} catch (StaleElementReferenceException e) {
				staleElement = true;
			}
		}
		return innerHtml;
	}

	public List<WebElement> getAllListItems(By byLocater) {
		boolean staleElement = true;
		List<WebElement> allElements = null;

		while (staleElement) {
			try {
				allElements = driver.findElements(byLocater);
				staleElement = false;

			} catch (StaleElementReferenceException e) {
				staleElement = true;
			}
		}

		return allElements;
	}

	private WebElement findWebElement(By byLocater) {
		WebElement element = (new WebDriverWait(driver, timeOut))
				.until(ExpectedConditions.presenceOfElementLocated(byLocater));
		return element;
	}

}

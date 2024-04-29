package main;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtility {
	static int defaultWaitTime = 1; // Default wait time to set after performing any actions
	
	/***
	 * Click element by ID
	 * @param driver
	 * @param id ID of element
	 */
	public static void clickElementByID(WebDriver driver, String id) {
		driver.findElement(By.id(id)).click();
		sleep(defaultWaitTime);
	}
	
	/***
	 * Input text into element by ID
	 * @param driver
	 * @param id ID of element
	 * @param text String to input to element
	 */
	public static void inputByID(WebDriver driver, String id, String text) {
		driver.findElement(By.id(id)).sendKeys(text);
		sleep(defaultWaitTime);
	}
	
	/***
	 * Wait a set time in seconds
	 * @param seconds Number of seconds to wait
	 */
	public static void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * Explicitly wait for an element by id to be clickable
	 * @param driver
	 * @param id ID of element to wait for
	 */
	public static void waitForElement(WebDriver driver, String id) {
		WebElement element = (new WebDriverWait(driver,Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		sleep(defaultWaitTime);
	}
}

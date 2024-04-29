package main;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPageObject {

	static String alertFound = "Yay! You find it!";	
	
	/***
	 * Click weigh button
	 * @param driver
	 */
	public static void clickWeighButton(WebDriver driver) {
		System.out.println("Click the Weigh Button");
		driver.findElement(By.id("weigh")).click();
		CommonUtility.sleep(5);
	}
	
	/***
	 * Click Reset button
	 * @param driver
	 */
	public static void clickResetButton(WebDriver driver) {
		System.out.println("Click the Reset Button");
		driver.findElement(By.xpath("//button[text()=\"Reset\"]")).click();
		CommonUtility.sleep(1);
	}
	
	/***
	 * Get measurements results from the weighing between the two bowls
	 * @param driver
	 * @return measurements results as '=','<', or '>'
	 */
	public static String getMeasurementResults(WebDriver driver) {
		String results = driver.findElement(By.xpath("//div[@class=\"result\"]/button")).getText();
		System.out.println("Weighing result: " + results);
		return results;
	}
	
	/***
	 * Populate the left bowl with item
	 * @param driver
	 * @param list List of item to fill the bowl
	 */
	public static void fillLeftBowl(WebDriver driver, String[] list) {
		if(list.length== 0) return;
		
		for (int i=0; i<list.length; i++) {
			System.out.println("Insert number " + list[i] +" into left bowl's grid");
			CommonUtility.inputByID(driver, "left_"+i , list[i]);
		}
	}
	
	/***
	 * Populate the right bowl with item
	 * @param driver
	 * @param list List of item to fill the bowl
	 */
	public static void fillRightBowl(WebDriver driver, String[] list) {
		if(list.length== 0) return;
	
		for (int i=0; i<list.length; i++) {
			System.out.println("Insert number " + list[i] +" into right bowl's grid");
			CommonUtility.inputByID(driver, "right_"+i , list[i]);
		}
	}
	
	/***
	 * Print number and list of all weighings
	 * @param driver
	 */
	public static void printWeighings(WebDriver driver) {
		List<WebElement> listOfElements = driver.findElements(By.xpath("//div[@class=\"game-info\"]/ol/li"));
		System.out.println("Number of Weighings: " + listOfElements.size());
		System.out.println("**List of Weighings**");
		for (int i = 0; i < listOfElements.size(); i++) {
			System.out.println(listOfElements.get(i).getText());
		}
	}

	/***
	 * Check the gold bar by clicking on the number and verify results
	 * @param driver
	 * @param num Gold bar number to check
	 */
	public static void checkGoldBar(WebDriver driver, String num) {
		System.out.println("Press button " + num);
		CommonUtility.clickElementByID(driver, "coin_" + num);
		
		String mainWindowHandles = driver.getWindowHandle();
		
		// Switch to alert and extract message
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		System.out.println("Alert: " + alertText);
		if (alertText.equals(alertFound)) {
			System.out.println("Successfully found fake goldbar to be " + num);
		}
		
		// Accept and close alert
		alert.accept();
		
		// Switch back to main window
		driver.switchTo().window(mainWindowHandles);
	}
	
	/***
	 * Process to find the fake goldbar using recursion
	 * @param driver
	 * @param listOfGoldbars List of all the goldbars
	 */
	public static void findFakeGoldbarAlgo(WebDriver driver, String[] listOfGoldbars) {
		//Base Case
		if (listOfGoldbars.length <= 1) {
			System.out.println("List too small to process");
			return;
		}
		
		String check = "";
		String[] leftList; 
		String[] rightList;

		// Check if list length is odd or even and split the list evenly to weigh
		if(listOfGoldbars.length % 2 == 1)
			rightList = Arrays.copyOfRange(listOfGoldbars,listOfGoldbars.length/2 + 1,listOfGoldbars.length);
		else
			rightList = Arrays.copyOfRange(listOfGoldbars,listOfGoldbars.length/2,listOfGoldbars.length);
		leftList = Arrays.copyOfRange(listOfGoldbars,0,listOfGoldbars.length/2);
		
		// Fill and weigh Left and right bowl
		MainPageObject.fillLeftBowl(driver, leftList);
		MainPageObject.fillRightBowl(driver, rightList);
		MainPageObject.clickWeighButton(driver);
		
		// Check Measurement Results
		check = MainPageObject.getMeasurementResults(driver);
		MainPageObject.clickResetButton(driver);
		
		// If list length is odd and results are equal, the goldbar we didn't weigh is the fake
		if (check.equals("=") && (listOfGoldbars.length % 2 == 1)) {
			System.out.println("Fake bar detected to be " + listOfGoldbars.length/2);
			MainPageObject.checkGoldBar(driver,Integer.toString(listOfGoldbars.length/2));
		}
		
		// If left list weigh less, the fake goldbar is in the left list
		else if (check.equals("<")) {
			if(leftList.length == 1) {
				System.out.println("Fake bar detected to be " + leftList[0]);
				MainPageObject.checkGoldBar(driver,leftList[0]);
			} else {
				MainPageObject.findFakeGoldbarAlgo(driver, leftList);
			}
		}
		
		// If right list weigh less, the fake goldbar is in the right list
		else if (check.equals(">")) {
			if(leftList.length == 1) {
				System.out.println("Fake bar detected to be " + rightList[0]);
				MainPageObject.checkGoldBar(driver,rightList[0]);
			} else {
				MainPageObject.findFakeGoldbarAlgo(driver,rightList);
			}
		}
	}
}

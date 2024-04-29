package main;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class main {

	public static void main(String[] args) {
		//Initiate chromedriver
		System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();

		//Maximize window
		driver.manage().window().maximize();

		//Open browser with URL
		driver.get("http://sdetchallenge.fetch.com");
		CommonUtility.waitForElement(driver,"weigh");

		//Start Algorithm to find fake goldbar
		String[] listOfGoldbars = {"0","1","2","3","4","5","6","7","8"}; // List of all gold bars
		MainPageObject.findFakeGoldbarAlgo(driver,listOfGoldbars);
		MainPageObject.printWeighings(driver);
		
		//Close the browser
		driver.quit();
	}

}

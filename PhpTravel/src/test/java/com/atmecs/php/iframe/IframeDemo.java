package com.atmecs.php.iframe;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.atmecs.php.base.Base;
import com.atmecs.php.config.Constant;
import com.atmecs.php.dateAndTime.DateAndTime;
import com.atmecs.php.fingObject.FindObject;
import com.atmecs.php.helper.Helper;
import com.atmecs.php.helper.Waits;
import com.atmecs.php.reports.Reports;

public class IframeDemo extends Base {

	List<String> leastPrice;
	String split;
	int index = 0;
	public Reports report = new Reports();
	public Helper helper = new Helper();
	public Waits wait = new Waits();
	public DateAndTime dateAndTime = new DateAndTime();
	public FindObject find = new FindObject();
	String detail = "(//button[contains(text(),'Details')])[*]";

	@Test
	public void iframe() {

		report.logInfo("Step 1 : Browser Opening");
		getBrowser(utils.propertyRead(Constant.config_file, "browserName"));
		report.logInfo("Step 2 : Enter url");
		getUrl(utils.propertyRead(Constant.config_file, "url"));
		
		
	
		driver.switchTo().parentFrame();
		List<WebElement> elements = driver.findElements(By.tagName("iframe"));
		int numberOfTags = elements.size();
		System.out.println("No. of Iframes on this Web Page are: " + numberOfTags);

		driver.switchTo().frame("chat-widget");
		helper.isAlertPresent(driver);
		System.out.println("Switching to the frame");
		Actions actions = new Actions(driver);
		WebElement findElement = driver.findElement(By.cssSelector(".lc-2xnp29.e1m5b1js0 .lc-1mpchac"));
		actions.moveToElement(findElement).perform();

		findElement.click();

		

	}
}

package com.atmecs.php.test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.atmecs.php.base.Base;
import com.atmecs.php.config.Constant;
import com.atmecs.php.dateAndTime.DateAndTime;
import com.atmecs.php.fingObject.FindObject;
import com.atmecs.php.helper.Helper;
import com.atmecs.php.helper.Waits;
import com.atmecs.php.reports.Reports;

public class PhpTravels extends Base {
	List<String> leastPrice;
	String split;
	int index = 0;
	public Reports report = new Reports();
	public Helper helper = new Helper();
	public Waits wait = new Waits();
	public DateAndTime dateAndTime = new DateAndTime();
	public FindObject find = new FindObject();
	String detail="(//button[contains(text(),'Details')])[*]";
	

	@BeforeClass
	public void browserLaunch() {
		report.logInfo("Step 1 : Browser Opening");
		getBrowser(utils.propertyRead(Constant.config_file, "browserName"));
		report.logInfo("Step 2 : Enter url");
		getUrl(utils.propertyRead(Constant.config_file, "url"));
	}
	
	
	@BeforeMethod
	public void checkFrames()
	{
		boolean alertPresent = helper.isAlertPresent(driver);
		if(alertPresent) {
			helper.findFrame(driver,"chat-widget");
		}
		else
		{
			report.logInfo("No ad's");
		}
		
	}

	@Test(priority = 1)
	public void bookingHotel() throws Exception {
		wait.implicitlyWait(driver);
		report.logInfo("Step 3:Click on hotel");
	    helper.clickOnWebElement(driver, Constant.homePageLoc_file, "loc_hotel");
		
		  
		 

		report.logInfo("Step 4:Verify user is landed into Home page ");
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
		if (currentUrl.contains("net")) {
			System.out.println("user landed successfully");
		}

		else {
			System.out.println("User not landed ");
		}

		report.logInfo("Step 5:Select Destination City as Bangalore");
		WebElement findElement = driver.findElement(By.xpath("(//input[@type='text'])[1]"));
		findElement.sendKeys("bangalore");
		Thread.sleep(2000);
		Actions action=new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(5000);

		report.logInfo("Step 6:Check-In Date should be greater than 10days");
		helper.clickOnWebElement(driver, Constant.homePageLoc_file, "loc_checkin");

		String moreThanTenDays = dateAndTime.gettingDay("11");
		helper.inputValuesToTheWebelement(driver, Constant.homePageLoc_file, "loc_checkin", moreThanTenDays);

		report.logInfo("Step 7:Check-out Date should be +2 days of Check-In Date");
		helper.clickOnWebElement(driver, Constant.homePageLoc_file, "loc_checkout");

		String plusTwoDays = dateAndTime.gettingDay("13");
		helper.inputValuesToTheWebelement(driver, Constant.homePageLoc_file, "loc_checkout", plusTwoDays);

		find.findObject(driver, utils.propertyRead(Constant.homePageLoc_file, "loc_adult")).click();
		report.logInfo("Step 8:Increased Passengers list  Adults : 4 ");
		find.findObject(driver, utils.propertyRead(Constant.homePageLoc_file, "loc_adult")).click();

		find.findObject(driver, utils.propertyRead(Constant.homePageLoc_file, "loc_child")).click();
		report.logInfo("Step 9:Increased Passengers list  child : 2 ");
		find.findObject(driver, utils.propertyRead(Constant.homePageLoc_file, "loc_child")).click();

		report.logInfo("Step 10:Perform Search for the Hotels");
		helper.clickOnWebElement(driver, Constant.homePageLoc_file, "loc_search");

		driver.findElement(By.cssSelector(".btn.btn-secondary.btn-wide.btn-toggle.btn-sm.btn-change-search")).click();
		String destination = driver.findElement(By.cssSelector(".select2-input")).getAttribute("value");
		System.out.println(destination);
		String checkin = driver.findElement(By.cssSelector("div #checkin")).getAttribute("value");
		System.out.println(checkin);
		String checkout = driver.findElement(By.cssSelector("div #checkout")).getAttribute("value");
		System.out.println(checkout);
		String adult = driver.findElement(By.xpath("//input[@name='adults']")).getAttribute("value");
		System.out.println(adult);
		String children = driver.findElement(By.xpath("//input[@name='children']")).getAttribute("value");
		System.out.println(children);

	}

	/**
	 * booking hotel having 4 star with low price
	 * 
	 * @throws InterruptedException
	 * 
	 */

	@Test(priority = 2)
	public void identifyHotel() throws InterruptedException {

		driver.findElement(By.cssSelector(".cc-btn.cc-dismiss")).click();
		
		for (int viewmore = 0; viewmore < 4; viewmore++) {
			WebElement findElement = driver.findElement(By.cssSelector(".btn.btn-block.btn-primary.btn-lg"));

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1000)");
			findElement.click();
			System.out.println("click" + viewmore + "view more");
		}

		report.logInfo("Step 14 :Identifing hotel having 4-star rating with low cost");
		List<WebElement> ratingFromWebsite = driver.findElements(By.cssSelector("[data-stars='4']"));
		for (WebElement fourStarRating : ratingFromWebsite) {

			String allRatingValues = fourStarRating.getText();
			System.out.println(allRatingValues);

			

		}

		List<WebElement> priceFromWebSite = driver.findElements(By.cssSelector("div[data-stars='4'] .text-secondary"));
		
		List<Double> newPrice = new ArrayList<Double>();

		for (WebElement price : priceFromWebSite) {
			String text = price.getText();
			String newValue = text.replace("USD ", "");
			double var = Double.parseDouble(newValue);
			newPrice.add(index, var);
			index++;

		}
		Collections.sort(newPrice);
		String lowPrice="USD "+newPrice.get(0);
		
		List<WebElement>priceList=driver.findElements(By.xpath("//span[contains(text(),'USD')]"));
		for(int integer=1;integer<=priceList.size();integer++) {
			String getPrice=driver.findElement(By.xpath("(//span[contains(text(),'USD')])["+integer+"]")).getText();
		
			if(getPrice.equalsIgnoreCase(lowPrice)) {
				
				String replace = detail.replace("*",integer+"");
				WebElement element=driver.findElement(By.xpath(replace));
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				js.executeScript("window.scrollBy(0,-75)");
				element.click();
				break;
			}
		}
	}

}


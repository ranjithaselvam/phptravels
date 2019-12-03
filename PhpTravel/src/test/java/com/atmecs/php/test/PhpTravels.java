package com.atmecs.php.test;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.atmecs.php.base.Base;
import com.atmecs.php.config.Constant;
import com.atmecs.php.dateAndTime.DateAndTime;
import com.atmecs.php.fingObject.FindObject;
import com.atmecs.php.helper.Helper;
import com.atmecs.php.helper.Waits;
import com.atmecs.php.reports.Reports;

public class PhpTravels extends Base {
	public Reports report = new Reports();
	public Helper helper = new Helper();
	public Waits wait = new Waits();
	public DateAndTime dateAndTime = new DateAndTime();
	public FindObject find = new FindObject();

	@BeforeClass
	public void browserLaunch() {
		report.logInfo("Step 1 : Browser Opening");
		getBrowser(utils.propertyRead(Constant.config_file, "browserName"));
		report.logInfo("Step 2 : Enter url");
		getUrl(utils.propertyRead(Constant.config_file, "url"));
	}

	@Test(priority = 1)
	public void bookingHotel() throws Exception {
		wait.implicitlyWait(driver);
		report.logInfo("Step 3:Click on hotel");
		helper.clickOnWebElement(driver, Constant.homePageLoc_file, "loc_hotel");
		Actions action = new Actions(driver);
		// action.sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);

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
		// findElement.sendKeys(Keys.ENTER);
		// helper.inputValuesToTheWebelement(driver, Constant.homePageLoc_file,
		// "loc_destination","bangalore");
		action.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(5000);

		report.logInfo("Step 6:Check-In Date should be greater than 10days");
		helper.clickOnWebElement(driver, Constant.homePageLoc_file, "loc_checkin");
		// String checkin = utils.propertyRead(Constant.homePageLoc_file,
		// "checkindays");
		String moreThanTenDays = dateAndTime.gettingDay("11");
		helper.inputValuesToTheWebelement(driver, Constant.homePageLoc_file, "loc_checkin", moreThanTenDays);

		report.logInfo("Step 7:Check-out Date should be +2 days of Check-In Date");
		helper.clickOnWebElement(driver, Constant.homePageLoc_file, "loc_checkout");
		// String checkout = utils.propertyRead(Constant.homePageLoc_file,
		// "checkoutdays");
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

	}

	/**
	 * booking hotel having 4 star with low price
	 * 
	 * @throws InterruptedException
	 * 
	 */

	@Test(priority = 2)
	public void identifyHotel() throws InterruptedException {
		for (int i = 0; i < 4; i++) {
			Thread.sleep(2000);
			WebElement findElement = driver.findElement(By.cssSelector(utils.propertyRead(Constant.hotelPageLoc_file, "loc_viewmore")));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(false);", findElement);
			findElement.click();
			report.logInfo("view more "+i+" loaded");
		}
		report.logInfo("Step 14 :Identifing hotel having 4-star rating with low cost");
		List<WebElement> ratingFromWebsite = driver
				.findElements(By.xpath(utils.propertyRead(Constant.hotelPageLoc_file, "loc_rating")));
		for (WebElement fourStarRating : ratingFromWebsite) {

			String allRatingValues = fourStarRating.getText();
			System.out.println(allRatingValues);

		}

	}

}

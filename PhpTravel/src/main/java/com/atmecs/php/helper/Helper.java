package com.atmecs.php.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.atmecs.php.fingObject.FindObject;
import com.atmecs.php.reports.Reports;
import com.atmecs.php.utils.Utils;

/**Purpose: To maintain all reusable method
 * 
 * @author ranjitha.selvam
 *
 */
public class Helper {
	public Reports report = new Reports();
	public FindObject locator = new FindObject();
	public Utils util=new Utils();
	public File file;
	public FileInputStream stream;
	public XSSFWorkbook book;
	public XSSFSheet sheet;
	public XSSFCell cell;
	


	/**Clear previous text in search box
	 * 
	 * @param driver
	 * @param element
	 */
	public void clearText(WebDriver driver,String element)
	{
		try {
			WebElement webElement = locator.findObject(driver, element);
			webElement.clear();
		} catch (Exception e) {
			e.toString();
		}
	}

	/**
	 * Determine the the state of the application whether it is the same what we are
	 * expecting or not.
	 * 
	 * @param actualDetails
	 * @param expectedDetails
	 */

	public void pageValidation(String actualDetails, String expectedDetails) {
		try {

			Assert.assertEquals(actualDetails, expectedDetails);
			report.logInfo(" Passed : " + " Expected : " + expectedDetails + " Actual : " + actualDetails);
		} catch (AssertionError assertionError) {
			report.logInfo(" Failed : " + " Expected : " + expectedDetails + " Actual : " + actualDetails);
		}

	}

	
	/**
	 * pass input values to the text box(enter the values).
	 * 
	 * @param element
	 * @param values
	 */
	public void inputValuesToTheWebelement(WebDriver driver,String path, String element, String values) {
		try {

			WebElement webElement = locator.findObject(driver,util.propertyRead(path, element));
			webElement.sendKeys(values);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handling mouse event(Clicks at the current mouse location). Click button or
	 * whatever
	 * 
	 * @param element
	 */

	public void clickOnWebElement(WebDriver driver,String path, String element) {
		try {
			
			WebElement webElement = locator.findObject(driver,util.propertyRead(path, element));
            webElement.click();
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		


	}

	/**
	 * Retrieving the specified elements Text(Get Text).
	 */

	public String getText(WebDriver driver, String element) throws Exception {
		String text = null;
		try {
			WebElement webElement = locator.findObject(driver, element);

			text = webElement.getText();

			System.out.println("Actual text :" + text);
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * Scroll to bottom of the page
	 * 
	 */
	public void scrollToBottomOfThePage(WebDriver driver,String element) {
		try {
			WebElement webElement = locator.findObject(driver, element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", webElement);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}



	/**
	 * mouse over to specific web element
	 * 
	 */
	public void move(WebDriver driver, String element) {

		WebElement mouseOver = locator.findObject(driver, element);
		Actions action = new Actions(driver);
		action.moveToElement(mouseOver).perform();

	}
	/**
	 * Read specific cell value via excel reader
	 * 
	 * @param path
	 */
		
		public String readSpreadSheet(String path,String sheetName,int row,int column)
		{
			
			String data=null;
			try {
				 file=new File(path);
				 stream=new FileInputStream (path);
;				
			stream=new FileInputStream(file);
				try {
					 book=new XSSFWorkbook(stream);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sheet=book.getSheet(sheetName);
				cell = sheet.getRow(row).getCell(column);
				data = cell.toString();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			return data;
			

		
		}
		
		
		 public static int findframenumber(WebDriver driver,By by) {
		        int i;
		        int framecount=driver.findElements(By.tagName("iframe")).size();

		        for (i=0;i<framecount;i++) {
		            driver.switchTo().frame(i);
		            int count=driver.findElements(by).size();
		            if(count>0) {
		                break;
		            } else{

		            }
		        }
		        driver.switchTo().defaultContent();
		        return i;
		    }
		

	public void findFrame(WebDriver driver,String name)
	{
		try {
			List<WebElement> elements = driver.findElements(By.tagName("iframe"));
			int numberOfTags = elements.size();
			System.out.println("No. of Iframes on this Web Page are: " + numberOfTags);

			driver.switchTo().frame(name);
			System.out.println("Switching to the frame");
			Actions actions = new Actions(driver);
			WebElement findElement = driver.findElement(By.cssSelector(".lc-2xnp29.e1m5b1js0 .lc-1mpchac"));
			actions.moveToElement(findElement).perform();
            findElement.click();

			driver.switchTo().parentFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public boolean isAlertPresent(WebDriver driver){
	    boolean foundAlert = false;
	    WebDriverWait wait = new WebDriverWait(driver,30);
	   try {
	        wait.until(ExpectedConditions.alertIsPresent());
	        foundAlert = true;
	       
	    } catch (TimeoutException eTO) {
	        foundAlert = false;
	    }
		return foundAlert;
		
	    
	}
	
	
	public  void isAlertPresents(WebDriver driver){
	    try{
	    Alert alert = driver.switchTo().alert();
	    System.out.println(alert.getText()+" Alert is Displayed"); 
	    }
	    catch(NoAlertPresentException ex){
	    System.out.println("Alert is NOT Displayed");
	    }
	    }
	

	
}


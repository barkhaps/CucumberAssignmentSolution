package com.stepdefinitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.pages.FinalPage;
import com.pages.HomePage;
import com.pages.SearchPage;
import com.sources.URLs;
import com.sources.Xpaths;
import com.utils.CommonMethods;
import com.utils.ContextPojo;

import io.cucumber.java.en.Given;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class MMTStepDefinition {

	protected WebDriver driver;
	ContextPojo context = new ContextPojo();

	@Before(order = 0, value = "@MMT_Automation")
	public void initialiseDriver() throws Throwable {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\barkha.sinha\\.m2\\repository\\webdriver\\chromedriver\\win32\\90.0.4430.24\\chromedriver.exe");
		HashMap<String, Object> prefs = new HashMap<>();
		ChromeOptions options = new ChromeOptions();
		// Allow Notifications and geolocation using chrome options
		prefs.put("profile.default_content_setting_values.geolocation", 1);
		prefs.put("profile.default_content_setting_values.notifications", 1);
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
	}

	@Before(order = 1, value = "@MMT_Automation")
	public void navigateToApplication() throws Throwable {
		driver.navigate().to(URLs.MMT_URL);
		HomePage.waitForPageToLoad(driver);
		if (CommonMethods.isElementVisible(driver, Xpaths.LOGIN_MODAL, 5)) {
			WebElement loginBtn = CommonMethods.waitForElementToBeClickable(driver, Xpaths.LOGIN_BUTTON, 20);
			Actions action = new Actions(driver);
			action.moveToElement(loginBtn).click().build().perform();
		}
	}

	@Given("^Click on Flights$")
	public void clickOnFlight() throws Throwable {
		CommonMethods.waitForElementToBePresent(driver, Xpaths.FLIGHT_MAIN_OPTION, 180);
		WebElement flight = CommonMethods.waitForElementToBeClickable(driver, Xpaths.FLIGHT_MAIN_OPTION, 180);
		flight.click();
	}

	@Given("^Select Trip Type and verify change$")
	public void selectTripType(Map<String, String> fieldList) throws Throwable {
		HomePage.selectAndVerifyChangeInTripType(driver, fieldList.get("Trip Type"));
		context.setTripType(fieldList.get("Trip Type"));
	}

	@Given("^Fill journey details and Continue$")
	public void fillJourneyDetAndContinue(Map<String, String> fieldList) throws Throwable {
		HomePage.fillTripDetails(driver, fieldList, context);
		WebElement searchButton = driver.findElement(By.xpath(Xpaths.SEARCH_BUTTON));
		Thread.sleep(2000);
		searchButton.click();
		SearchPage.waitForPageToLoad(driver);
	}

	@Given("^Select Airline filter and verify$")
	public void selectFlightFilter(Map<String, String> fieldList) throws Throwable {
		context.setAirlineName(fieldList.get("Airline Name"));
		SearchPage.selectAirlineFilterAndVerify(driver, context);
	}

	@Given("^Sort Grid and Select Nth Record$")
	public void sortAndSelect(Map<String, String> fieldList) throws Throwable {
		SearchPage.sortAndSelectFlight(driver, fieldList, context);
	}

	@Given("^Switch to Review Page Tab$")
	public void switchToNewTab() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, 180);
		boolean increaseTab = wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Assert.assertTrue(increaseTab, "Tab count did not increase");
		System.out.println("Switching to Review Tab");
		Set<String> winHandles = driver.getWindowHandles();
		winHandles.remove(driver.getWindowHandle());
		driver.switchTo().window(winHandles.iterator().next());
		FinalPage.waitForPageToLoad(driver);
	}

	@Given("^Switch to Home Tab$")
	public void closeCurrentTabAndSwitch() throws Throwable {
		List<String> winHandles = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Closing Review tab");
		driver.close();
		Thread.sleep(2000); // to see that tab is closing
		System.out.println("Switching to Home Tab");
		driver.switchTo().window(winHandles.get(0));
		WebDriverWait wait = new WebDriverWait(driver, 180);
		boolean decreaseTab = wait.until(ExpectedConditions.numberOfWindowsToBe(1));
		Assert.assertTrue(decreaseTab, "Tab count did not decrease");
	}

	@Given("^Verify Booking details$")
	public void verifyFlightDetails() throws Throwable {
		FinalPage.verifyFlightDetails(driver, context);
	}

	@Given("^Verify Fare Summary Data$")
	public void verifyFareSummaryData() throws Throwable {
		FinalPage.verifyFareSummaryData(driver, context);
	}

	@Given("^Uncheck and verify charity$")
	public void uncheckAndVerifyCharity() throws Throwable {
		FinalPage.removeCharityAndCheck(driver, context);
	}
	
	@Given("^Select insurance if available and verify$")
	public void checkAndVerifyInsurance() throws Throwable {
		FinalPage.addInsuranceAndCheck(driver, context);
	}

	@Given("^Navigate to Homepage$")
	public void navigateToHomePage() throws Throwable {
		if (context.getTripType().toLowerCase().contains("round")) {
			CommonMethods.waitForElementToBePresent(driver, Xpaths.CLOSE_FARE_SELECTION_MODAL_BTN, 10);
			CommonMethods.waitForElementToBeVisible(driver, Xpaths.CLOSE_FARE_SELECTION_MODAL_BTN, 10).click();
		}
		SearchPage.waitForPageToLoad(driver);
		CommonMethods.waitForElementToBePresent(driver, Xpaths.MMT_LOGO, 10);
		CommonMethods.waitForElementToBeVisible(driver, Xpaths.MMT_LOGO, 10).click();
		HomePage.waitForPageToLoad(driver);
	}

	@After("@MMT_Automation")
	public void closeDriver() throws Throwable {
		driver.quit();
	}

}

package com.pages;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.sources.Xpaths;
import com.utils.CommonMethods;
import com.utils.ContextPojo;

public class SearchPage {

	public static void waitForPageToLoad(WebDriver driver) throws Throwable {
		CommonMethods.waitForElementToBeInvisible(driver, Xpaths.LOADING_ICON_SEARCH1, 180);
		CommonMethods.waitForElementToBeInvisible(driver, Xpaths.LOADING_ICON_SEARCH2, 180);
		CommonMethods.waitForElementToBeInvisible(driver, Xpaths.SPINNING_LOADING_ICON, 180);
	}

	public static void selectAirlineFilterAndVerify(WebDriver driver, ContextPojo context) throws Throwable {
		waitForPageToLoad(driver);
		String airlineName = context.getAirlineName();
		String airlineXpath = Xpaths.AIRLINE_FILTER.replace(":flightName:", airlineName);
		WebElement flight = CommonMethods.waitForElementToBeVisible(driver, airlineXpath, 30);
		flight.click();
		waitForPageToLoad(driver);
		WebElement filtersApplied = CommonMethods.waitForElementToBeVisible(driver, Xpaths.APPLIED_FILTER, 180);
		System.out.println("Applied Filters: " + filtersApplied.getText() + "\nOurFilter: " + airlineName);
		Assert.assertTrue(filtersApplied.getText().toLowerCase().contains(airlineName.toLowerCase()),
				"Airline Filter not Applied");
		SearchPage.verifyAppliedFilterOnGrid(driver, context);
	}

	public static void verifyAppliedFilterOnGrid(WebDriver driver, ContextPojo context) throws Throwable {
		List<WebElement> flightCards = null;
		String tripType = context.getTripType(), airlineName = context.getAirlineName(),
				returnDate = context.getReturnDate();
		String departDate = context.getDepartDate(), fromCity = context.getFromCity(), toCity = context.getToCity();
		int cardCounter = 0;
		waitForPageToLoad(driver);
		if (tripType.toLowerCase().contains("oneway") || tripType.toLowerCase().contains("one way")) {
			String flightDeptDate = null;
			SimpleDateFormat reqFormatForSelection = new SimpleDateFormat("MMM d");
			String convertedDeptDate = reqFormatForSelection
					.format(new SimpleDateFormat("dd-MM-yyyy").parse(departDate));
			flightCards = driver.findElements(By.xpath(Xpaths.FLIGHT_LIST_ONEWAY));
			if(CommonMethods.isElementVisible(driver, Xpaths.CURRENT_SELECT_DATE, 5)) {
				flightDeptDate = driver.findElement(By.xpath(Xpaths.CURRENT_SELECT_DATE)).getText();
			} else {
				flightDeptDate = driver.findElement(By.xpath(Xpaths.SELECTED_DEPT_DATE)).getAttribute("value");
			}
			System.out.println("Selected date according to UI: " + flightDeptDate + "\nDate selected: " + convertedDeptDate);
			Assert.assertTrue(flightDeptDate.toLowerCase().contains(convertedDeptDate.toLowerCase()),
					"Departure Date Not Same as selected");

		} else if (tripType.toLowerCase().contains("round")) {
			SimpleDateFormat reqFormatForSelection = new SimpleDateFormat("d MMM");
			String convertedDeptDate = reqFormatForSelection
					.format(new SimpleDateFormat("dd-MM-yyyy").parse(departDate));
			String convertedRetDate = reqFormatForSelection
					.format(new SimpleDateFormat("dd-MM-yyyy").parse(returnDate));
			flightCards = driver.findElements(By.xpath(Xpaths.FLIGHT_LIST_ROUND));
			String flightDeptDate = driver.findElement(By.xpath(Xpaths.DEPART_DATE)).getText();
			Assert.assertTrue(flightDeptDate.toLowerCase().contains(convertedDeptDate.toLowerCase()),
					"Departure Date Not Same as selected");
			String flightRetDate = driver.findElement(By.xpath(Xpaths.RETURN_DATE)).getText();
			Assert.assertTrue(flightRetDate.toLowerCase().contains(convertedRetDate.toLowerCase()),
					"Return Date Not Same as selected");
		}
		if (flightCards.size() > 0) {
			for (WebElement flightCard : flightCards) {
				cardCounter++;
				String cardText = flightCard.getText();
				boolean flighCardFiltered = (cardText.toLowerCase().contains(airlineName.toLowerCase()))
						&& (cardText.toLowerCase().contains(fromCity.toLowerCase()))
						&& (cardText.toLowerCase().contains(toCity.toLowerCase()));
				if (!flighCardFiltered) {
					System.out.println("Flight filter not found in Grid Card number " + cardCounter);
					System.out.println("Expected Data: " + fromCity + ", " + toCity + ", " + airlineName);
					System.out.println("Found: " + cardText);
				}
				Assert.assertTrue(flighCardFiltered, "Cards not of applied filter");
			}
		}
	}

	public static void sortAndSelectFlight(WebDriver driver, Map<String, String> fieldList, ContextPojo context)
			throws Throwable {
		String tripType = context.getTripType();
		String sortType = fieldList.get("Sort Type");
		String sorterXpath = Xpaths.FLIGHT_SORTER;
		if (fieldList.containsKey("Sort Column")) {
			System.out.println(
					"Sorting both grids with column: " + fieldList.get("Sort Column") + "\nIn the order: " + sortType);
			sorterXpath = sorterXpath.replace(":sortWith:", fieldList.get("Sort Column").toLowerCase());
			List<WebElement> sorters = driver.findElements(By.xpath(sorterXpath));
			Thread.sleep(2000);
			for (WebElement sorter : sorters) {
				sorter.click();
				waitForPageToLoad(driver);
				if (sortType.equalsIgnoreCase("descending")) {
					sorter.click();
				}
				Thread.sleep(2000);
			}
			sorters = driver.findElements(By.xpath(sorterXpath));
			for (WebElement sorter : sorters) {
				if ((sorter.getAttribute("class").contains("up sort-arrow") && sortType.equalsIgnoreCase("ascending"))
						|| (sorter.getAttribute("class").contains("down sort-arrow")
								&& sortType.equalsIgnoreCase("descending"))) {
					System.out.println("Grids sorted successfully");
				} else {
					Assert.fail("Unable to sort");
				}
			}
		}
		if (tripType.toLowerCase().contains("oneway") || tripType.toLowerCase().contains("one way")) {
			String oneWayViewFare = Xpaths.NTH_FLIGHT_VIEW_FARE_BTN.replace(":N:", fieldList.get("N"));
			WebElement viewFlightFare = CommonMethods.waitForElementToBeClickable(driver, oneWayViewFare, 10);
			if(!(viewFlightFare == null)) {
			viewFlightFare.click();
			String bookOneWayBtn = Xpaths.NTH_FLIGHT_FARE_TYPE_BOOK_BTN.replace(":N:", fieldList.get("N"))
					.replace(":fareType:", fieldList.get("Fare Type"));
			CommonMethods.waitForElementToBePresent(driver, bookOneWayBtn, 20);
			WebElement bookNowBtn = CommonMethods.waitForElementToBeClickable(driver, bookOneWayBtn, 10);
			bookNowBtn.click();
			} else {
				String bookNowBtn = Xpaths.DIRECT_BOOK_NOW.replace(":N:", fieldList.get("N"));
				CommonMethods.waitForElementToBePresent(driver, bookNowBtn, 20);
				WebElement bookNowBtnWe = CommonMethods.waitForElementToBeClickable(driver, bookNowBtn, 10);
				bookNowBtnWe.click();
			}
		} else if (tripType.toLowerCase().contains("round")) {
			String deptFlight = Xpaths.NTH_FLIGHT_DEPART_CHCK.replace(":N:", fieldList.get("N"));
			String retFlight = Xpaths.NTH_FLIGHT_RETURN_CHCK.replace(":N:", fieldList.get("N"));
			String deptFare = Xpaths.DEPART_FLIGHT_FARE_TYPE.replace(":fareType:", fieldList.get("Fare Type"));
			String retFare = Xpaths.RETURN_FLIGHT_FARE_TYPE.replace(":fareType:", fieldList.get("Fare Type"));
			WebElement deptCheck = CommonMethods.waitForElementToBeClickable(driver, deptFlight, 10);
			deptCheck.click();
			waitForPageToLoad(driver);
			WebElement retCheck = CommonMethods.waitForElementToBeClickable(driver, retFlight, 10);
			retCheck.click();
			waitForPageToLoad(driver);
			WebElement bookNow = CommonMethods.waitForElementToBeVisible(driver, Xpaths.BOOK_ROUND_TRIP_BTN, 20);
			bookNow.click();
			waitForPageToLoad(driver);
			WebElement deptFareWe = CommonMethods.waitForElementToBeClickable(driver, deptFare, 20);
			deptFareWe.click();
			WebElement retFareWe = CommonMethods.waitForElementToBeVisible(driver, retFare, 20);
			retFareWe.click();
			WebElement continueBook = CommonMethods.waitForElementToBeVisible(driver, Xpaths.CONTINUE_WITH_FARE_BTN,
					20);
			continueBook.click();
		}
	}

}

package com.pages;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.sources.Xpaths;
import com.utils.CommonMethods;
import com.utils.ContextPojo;

public class HomePage {

	public static void waitForPageToLoad(WebDriver driver) throws Throwable {
		Thread.sleep(2000);
		CommonMethods.waitForElementToBeVisible(driver, Xpaths.FROM_INPUT_CLICK, 180);
		CommonMethods.waitForElementToBeVisible(driver, Xpaths.FLIGHT_MAIN_OPTION, 180);
		CommonMethods.waitForElementToBeInvisible(driver, Xpaths.LOADING_ICON_HOME, 180);
		Thread.sleep(2000);
	}

	public static void selectTripType(WebDriver driver, String tripType) throws Throwable {
		System.out.println("Selecting Trip Type as = " + tripType);
		String tripTypeXpath = Xpaths.TRIP_TYPE;
		if (tripType.toLowerCase().contains("oneway") || tripType.toLowerCase().contains("one way")) {
			tripTypeXpath = tripTypeXpath.replace(":tripType:", "oneWayTrip");
		} else if (tripType.toLowerCase().contains("round")) {
			tripTypeXpath = tripTypeXpath.replace(":tripType:", "roundTrip");
		} else {
			tripTypeXpath = tripTypeXpath.replace(":tripType:", "mulitiCityTrip");
		}
		try {
			WebElement tripTypeWe = CommonMethods.waitForElementToBeClickable(driver, tripTypeXpath, 180);
			tripTypeWe.click();

		} catch (Exception e) {
			System.out.println("Error while selecting Trip Type = " + tripType);
		}
	}

	public static void selectAndVerifyChangeInTripType(WebDriver driver, String tripType) throws Throwable {
		System.out.println("Checking change for Trip Type = " + tripType);
		selectTripType(driver, tripType);
		if (tripType.toLowerCase().contains("oneway") || tripType.toLowerCase().contains("one way")) {
			WebElement returnDatePickerWe = CommonMethods.waitForElementToBeClickable(driver, Xpaths.DATE_RETURN_CLICK,
					180);
			returnDatePickerWe.click();
			String tripTypeXpath = Xpaths.TRIP_TYPE.replace(":tripType:", "roundTrip");
			WebElement roundTrip = CommonMethods.waitForElementToBeVisible(driver, tripTypeXpath, 180);
			String checkedAttr = roundTrip.getAttribute("class");
			Assert.assertTrue(checkedAttr.equals("selected"),
					"After selecting 'return datePicker' from OneWay, Round Trip not enabled");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 4);
			Date twoDaysFromToday = c.getTime();
			selectDateFromPicker(driver, new SimpleDateFormat("dd-MM-yyyy").format(twoDaysFromToday));
		} else if (tripType.toLowerCase().contains("round")) {
			WebElement cancleCrossWe = CommonMethods.waitForElementToBeClickable(driver, Xpaths.CANCEL_RETURN_CROSS_BTN,
					180);
			cancleCrossWe.click();
			String tripTypeXpath = Xpaths.TRIP_TYPE.replace(":tripType:", "oneWayTrip");
			WebElement onewayTrip = CommonMethods.waitForElementToBeVisible(driver, tripTypeXpath, 180);
			String checkedAttr = onewayTrip.getAttribute("class");
			Assert.assertTrue(checkedAttr.equals("selected"),
					"After selecting return datePicker from OneWay, Round Trip not enabled");
		}
		selectTripType(driver, tripType);
	}

	public static void selectDateFromPicker(WebDriver driver, String date) throws Throwable {
		Thread.sleep(2000);
		Assert.assertTrue(new SimpleDateFormat("dd-MM-yyyy").parse(date).after(new Date()),
				"Cannot select a date that was before today");
		System.out.println("Selecting Date " + date);
		SimpleDateFormat reqFormatForSelection = new SimpleDateFormat("MMM dd yyyy");
		String convertedDateForSeletion = reqFormatForSelection.format(new SimpleDateFormat("dd-MM-yyyy").parse(date));
		String datePickerDateXpath = Xpaths.DATE_PICKER_DATE_TO_SELECT.replace(":dateIn-MMM dd yyyy:",
				convertedDateForSeletion);
		CommonMethods.waitForElementToBeVisible(driver, Xpaths.DATE_PICKER_NEXT_SET_OF_MONTHS, 60);
		boolean dateFound = false;
		int monthsPassed = 0;
		while (dateFound == false && monthsPassed < 10) {
			WebElement datePickerWe = CommonMethods.waitForElementToBeVisible(driver, datePickerDateXpath, 10);
			if (datePickerWe == null) {
				driver.findElement(By.xpath(Xpaths.DATE_PICKER_NEXT_SET_OF_MONTHS)).click();
			} else {
				dateFound = true;
				datePickerWe.click();
			}
			monthsPassed++;
		}
		if (dateFound == false) {
			new RuntimeException("Couldn't click on date " + convertedDateForSeletion + " as the date is invalid");
		}
	}

	public static void selectTravellersFromPicker(WebDriver driver, String numberOfAdults, String numberOfChildren,
			String numberOfInfants) throws Throwable {
		Thread.sleep(2000);
		CommonMethods.waitForElementToBePresent(driver, Xpaths.APPLY_TRAVELLERS, 30);
		String adultsToSelect = "1", childrenToSelect = "1", infantsToSelect = "1";
		String adultsTravellerXpath = Xpaths.PASSENGER_TYPE_QUANT.replace(":passengerType:", "adults");
		String childrenTravellerXpath = Xpaths.PASSENGER_TYPE_QUANT.replace(":passengerType:", "children");
		String infantsTravellerXpath = Xpaths.PASSENGER_TYPE_QUANT.replace(":passengerType:", "infants");
		if (!(numberOfAdults == null)) {
			if (Integer.parseInt(numberOfAdults) < 1) {
				new RuntimeException("There needs to be atleast 1 adult passenger");
			} else if (Integer.parseInt(numberOfAdults) > 9) {
				adultsToSelect = "last()";
			} else {
				adultsToSelect = numberOfAdults;
			}
			adultsTravellerXpath = adultsTravellerXpath.replace("Row", adultsToSelect);
			driver.findElement(By.xpath(adultsTravellerXpath)).click();
		}
		if (!(numberOfChildren == null)) {
			if (Integer.parseInt(numberOfChildren) < 0) {
				new RuntimeException("Invalid Selection number for selection of children = " + numberOfChildren);
			} else if (Integer.parseInt(numberOfChildren) > 6) {
				childrenToSelect = "last()";
			} else if (Integer.parseInt(numberOfChildren) > 0) {
				childrenToSelect = String.valueOf(Integer.parseInt(numberOfChildren) + 1);
			}
			childrenTravellerXpath = childrenTravellerXpath.replace("Row", childrenToSelect);
			driver.findElement(By.xpath(childrenTravellerXpath)).click();
		}
		if (!(numberOfInfants == null)) {
			if (Integer.parseInt(numberOfInfants) < 0) {
				new RuntimeException("Invalid Selection number for selection of infants = " + numberOfInfants);
			} else if (Integer.parseInt(numberOfInfants) > 6) {
				infantsToSelect = "last()";
			} else if (Integer.parseInt(numberOfInfants) > 0) {
				infantsToSelect = String.valueOf(Integer.parseInt(numberOfInfants) + 1);
			}
			infantsTravellerXpath = infantsTravellerXpath.replace("Row", infantsToSelect);
			driver.findElement(By.xpath(infantsTravellerXpath)).click();
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath(Xpaths.APPLY_TRAVELLERS)).click();
	}

	public static void fillTripDetails(WebDriver driver, Map<String, String> fieldList, ContextPojo context)
			throws Throwable {
		String tripType = context.getTripType();
		context.setFromCity(fieldList.get("From City"));
		context.setToCity(fieldList.get("To City"));
		context.setDepartDate(fieldList.get("Depart Date"));
		context.setReturnDate(fieldList.get("Return Date"));
		context.setNumberOfAdults(fieldList.get("No Of Adults"));
		context.setNumberOfChildren(fieldList.get("No Of Children"));
		context.setNumberOfInfants(fieldList.get("No Of Infants"));
		String deptDate = fieldList.get("Depart Date"), retDate = fieldList.get("Return Date");
		CommonMethods.waitForElementToBeClickable(driver, Xpaths.FROM_INPUT_CLICK, 30).click();
		WebElement inputTextBox = CommonMethods.waitForElementToBePresent(driver, Xpaths.INPUT_TEXTBOX, 30);
		inputTextBox.sendKeys(fieldList.get("From City"));
		WebElement suggestionWe = CommonMethods.waitForElementToBeVisible(driver,
				Xpaths.SUGGESTIONS_COMBOBOX.replace("Row", fieldList.get("Suggestion Index")), 10);
		if (suggestionWe == null || suggestionWe.getText().toLowerCase().contains("no data")) {
			Assert.fail("No Suggestions found for the provided city key");
		}
		suggestionWe.click();
		if (CommonMethods.waitForElementToBeVisible(driver, Xpaths.INPUT_TEXTBOX, 30) == null) {
			CommonMethods.waitForElementToBeClickable(driver, Xpaths.TO_INPUT_CLICK, 30).click();
		}
		inputTextBox = CommonMethods.waitForElementToBePresent(driver, Xpaths.INPUT_TEXTBOX, 30);
		inputTextBox.sendKeys(fieldList.get("To City"));
		suggestionWe = CommonMethods.waitForElementToBeVisible(driver,
				Xpaths.SUGGESTIONS_COMBOBOX.replace("Row", fieldList.get("Suggestion Index")), 10);
		if (suggestionWe == null || suggestionWe.getText().toLowerCase().contains("no data")) {
			Assert.fail("No Suggestions found for the provided city key");
		}
		suggestionWe.click();
		if (CommonMethods.waitForElementToBeVisible(driver, Xpaths.DATE_PICKER_NEXT_SET_OF_MONTHS, 10) == null) {
			CommonMethods.waitForElementToBeClickable(driver, Xpaths.DATE_FROM_CLICK, 30).click();
		}
		selectDateFromPicker(driver, deptDate);
		if (tripType.toLowerCase().contains("round")) {
			Assert.assertTrue(
					new SimpleDateFormat("dd-MM-yyyy").parse(retDate)
							.after(new SimpleDateFormat("dd-MM-yyyy").parse(deptDate)),
					"Return date " + retDate + " should be after departure date " + deptDate);
			selectDateFromPicker(driver, retDate);
		}
		CommonMethods.waitForElementToBeClickable(driver, Xpaths.TRAVELLERS_CLICK, 30).click();
		selectTravellersFromPicker(driver, fieldList.get("No Of Adults"), fieldList.get("No Of Children"),
				fieldList.get("No Of Infants"));

	}

}

package com.pages;

import java.text.SimpleDateFormat;
import java.util.List;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sources.Xpaths;
import com.utils.CommonMethods;
import com.utils.ContextPojo;

public class FinalPage {

	public static void waitForPageToLoad(WebDriver driver) throws Throwable {
		CommonMethods.waitForElementToBeInvisible(driver, Xpaths.PAGE_LOADING_ICON1, 180);
		CommonMethods.waitForElementToBeInvisible(driver, Xpaths.PAGE_LOADING_ICON2, 180);
		CommonMethods.waitForElementToBeInvisible(driver, Xpaths.SPINNING_LOADING_ICON, 180);
	}

	public static void verifyFlightDetails(WebDriver driver, ContextPojo context) throws Throwable {
		waitForPageToLoad(driver);
		String tripType = context.getTripType();
		String fromCity = context.getFromCity(), toCity = context.getToCity();
		String comparabledDeptDate = new SimpleDateFormat("d MMM yy")
				.format(new SimpleDateFormat("dd-MM-yyyy").parse(context.getDepartDate()));
		List<WebElement> deptFlightDet = driver.findElements(By.xpath(Xpaths.DEPART_FLIGHT_DETAILS));
		int deptFlightCards = deptFlightDet.size() - 1;
		// no matter how many number of lay overs, first card will have from trip
		// details and last will have to trip details
		String deptCardDet = deptFlightDet.get(0).getText();
		System.out.println("Dept card details : " + deptCardDet);
		System.out.println("Data expected in card : " + fromCity + ", " + comparabledDeptDate);
		Assert.assertTrue(deptCardDet.contains(fromCity), "Error while verifying from city in departure card");
		Assert.assertTrue(deptCardDet.contains(comparabledDeptDate),
				"Error while verifying departure date in departure card");
		deptCardDet = deptFlightDet.get(deptFlightCards).getText();// if only 1 card, it'll select 0th index
		System.out.println("Dept card details 2 : " + deptCardDet);
		System.out.println("Data expected in card 2 : " + toCity);
		Assert.assertTrue(deptCardDet.contains(toCity), "Error while verifying to city in departure card");
		if (tripType.toLowerCase().contains("round")) {
			String comparableRetDate = new SimpleDateFormat("dd MMM yy")
					.format(new SimpleDateFormat("dd-MM-yyyy").parse(context.getReturnDate()));
			List<WebElement> retFlightDet = driver.findElements(By.xpath(Xpaths.RETURN_FLIGHT_DETAILS));
			int retFlightCards = retFlightDet.size() - 1;
			String retCardDet = retFlightDet.get(0).getText();
			System.out.println("Ret card details : " + retCardDet);
			System.out.println("Data expected in card : " + toCity + ", " + comparableRetDate);
			Assert.assertTrue(retCardDet.contains(toCity), "Error while verifying from city in return card");
			Assert.assertTrue(retCardDet.contains(comparableRetDate),
					"Error while verifying departure date in departure card");
			retCardDet = retFlightDet.get(retFlightCards).getText();
			System.out.println("Ret card details 2 : " + retCardDet);
			System.out.println("Data expected in card 2 : " + fromCity);
			Assert.assertTrue(retCardDet.contains(fromCity), "Error while verifying to city in return card");
		}
	}

	public static void validatePsngrCntAndAmt(WebDriver driver, ContextPojo context) throws Throwable {
		int expectedAdultCount = Integer.parseInt(context.getNumberOfAdults());
		int expectedChildCount = context.getNumberOfChildren() == null ? 0
				: Integer.parseInt(context.getNumberOfChildren());
		int expectedInfantCount = context.getNumberOfInfants() == null ? 0
				: Integer.parseInt(context.getNumberOfInfants());
		String countFetchXpath = Xpaths.TRAVELLER_TYPE_DETAILS;
		int uiAdultCnt = CommonMethods.getMatchedInt(
				CommonMethods.waitForElementToBeVisible(driver, countFetchXpath.replace(":travlrType:", "Adult"), 20)
						.getText().trim(),
				"\\W(\\d{1})\\s+");
		System.out.println("Number of adults provided: " + expectedAdultCount
				+ "\nNumber of adults found on review page: " + uiAdultCnt);
		Assert.assertEquals(uiAdultCnt, expectedAdultCount, "Error while verifying number of adults");
		if (expectedChildCount == 0) {
			Assert.assertFalse(
					CommonMethods.isElementVisible(driver, countFetchXpath.replace(":travlrType:", "Children"), 5),
					"Expected no children, but found");
		} else {
			int uiChildCnt = CommonMethods.getMatchedInt(CommonMethods
					.waitForElementToBeVisible(driver, countFetchXpath.replace(":travlrType:", "Children"), 20)
					.getText().trim(), "\\W(\\d{1})\\s+");
			System.out.println("Number of children provided: " + expectedChildCount
					+ "\nNumber of children found on review page: " + uiChildCnt);
			Assert.assertEquals(uiChildCnt, expectedChildCount, "Error while verifying number of children");
		}
		if (expectedInfantCount == 0) {
			Assert.assertFalse(
					CommonMethods.isElementVisible(driver, countFetchXpath.replace(":travlrType:", "Infant"), 5),
					"Expected no Infants, but found");
		} else {
			int uiInfantCnt = CommonMethods.getMatchedInt(CommonMethods
					.waitForElementToBeVisible(driver, countFetchXpath.replace(":travlrType:", "Infant"), 20).getText()
					.trim(), "\\W(\\d{1})\\s+");
			System.out.println("Number of infants provided: " + expectedInfantCount
					+ "\nNumber of infants found on review page: " + uiInfantCnt);
			Assert.assertEquals(uiInfantCnt, expectedInfantCount, "Error while verifying number of infants");
		}
		validatePassengerAmounts(driver, context, expectedAdultCount, expectedChildCount, expectedInfantCount);

	}

	public static void validatePassengerAmounts(WebDriver driver, ContextPojo context, int adltCnt, int chldCnt,
			int infCnt) throws Throwable {
		String countFetchXpath = Xpaths.TRAVELLER_TYPE_DETAILS;
		String amtFetchXpath = Xpaths.FARE_TYPE_COST;
		int perAdultAmt = CommonMethods.getMatchedInt(
				CommonMethods.waitForElementToBeVisible(driver, countFetchXpath.replace(":travlrType:", "Adult"), 20)
						.getText().trim(),
				"\\W\\s*(\\d{2,})");
		int totalUIAdultAmt = CommonMethods.getMatchedInt(CommonMethods
				.waitForElementToBeVisible(driver, amtFetchXpath.replace(":FareType:", "Adult"), 20).getText().trim(),
				"\\W\\s*(\\d{2,})");
		System.out.println("For " + adltCnt + " adults and rate " + perAdultAmt + " per adult, expect amount: "
				+ perAdultAmt * adltCnt);
		System.out.println("Total amount on UI: " + totalUIAdultAmt);
		Assert.assertEquals(totalUIAdultAmt, (perAdultAmt * adltCnt), "Error while verifying total amt for adults");
		context.setAdultTotalAmt(totalUIAdultAmt);
		if (chldCnt > 0) {
			int perChldAmt = CommonMethods.getMatchedInt(CommonMethods
					.waitForElementToBeVisible(driver, countFetchXpath.replace(":travlrType:", "Children"), 20)
					.getText().trim(), "\\W\\s*(\\d{2,})");
			int totalUIChldAmt = CommonMethods.getMatchedInt(
					CommonMethods.waitForElementToBeVisible(driver, amtFetchXpath.replace(":FareType:", "Children"), 20)
							.getText().trim(),
					"\\W\\s*(\\d{2,})");
			System.out.println("For " + chldCnt + " children and rate " + perChldAmt + " per child, expect amount: "
					+ perChldAmt * chldCnt);
			System.out.println("Total amount on UI: " + totalUIChldAmt);
			Assert.assertEquals(totalUIChldAmt, (perChldAmt * chldCnt), "Error while verifying total amt for children");
			context.setChildTotalAmt(totalUIChldAmt);
		}
		if (infCnt > 0) {
			int perInfAmt = CommonMethods.getMatchedInt(CommonMethods
					.waitForElementToBeVisible(driver, countFetchXpath.replace(":travlrType:", "Infant"), 20).getText()
					.trim(), "\\W\\s*(\\d{2,})");
			int totalUIInfAmt = CommonMethods.getMatchedInt(
					CommonMethods.waitForElementToBeVisible(driver, amtFetchXpath.replace(":FareType:", "Infant"), 20)
							.getText().trim(),
					"\\W\\s*(\\d{2,})");
			System.out.println("For " + infCnt + " infants and rate " + perInfAmt + " per infant, expect amount: "
					+ perInfAmt * infCnt);
			System.out.println("Total amount on UI: " + totalUIInfAmt);
			Assert.assertEquals(totalUIInfAmt, (perInfAmt * infCnt), "Error while verifying total amt for infants");
			context.setInfantTotalAmount(totalUIInfAmt);
		}
	}

	public static void verifyFareSummaryData(WebDriver driver, ContextPojo context) throws Throwable {
		waitForPageToLoad(driver);
		WebElement baseFareExpandWe = CommonMethods.waitForElementToBeVisible(driver, Xpaths.EXPAND_ICON_BASE_FARE, 5);
		if (!(baseFareExpandWe.getAttribute("class").contains("open"))) {
			CommonMethods.waitForElementToBeClickable(driver, Xpaths.EXPAND_ICON_BASE_FARE, 5).click();
		}
		validatePsngrCntAndAmt(driver, context);
		validateTotalAmount(driver, context);

	}

	public static void validateTotalAmount(WebDriver driver, ContextPojo context) throws Throwable {
		waitForPageToLoad(driver);
		CommonMethods.hoverToElement(driver, Xpaths.EXPAND_ICON_BASE_FARE);
		String fareAmtXpath = Xpaths.FARE_TYPE_COST;
		int feesUI = 0, othersUI = 0;
		int totalAmt = CommonMethods.getMatchedInt(
				CommonMethods.waitForElementToBeVisible(driver, Xpaths.TOTAL_AMOUNT, 5).getText().trim(),
				"\\W\\s*(\\d{2,})");
		WebElement fees = CommonMethods.waitForElementToBeVisible(driver, fareAmtXpath.replace(":FareType:", "Fee"), 5);
		if (!(fees == null)) {
			feesUI = CommonMethods.getMatchedInt(fees.getText().trim(), "\\W\\s*(\\d{2,})");
		}
		WebElement other = CommonMethods.waitForElementToBeVisible(driver, fareAmtXpath.replace(":FareType:", "Other"),
				5);
		if (!(other == null)) {
			othersUI = CommonMethods.getMatchedInt(other.getText().trim(), "\\W\\s*(\\d{2,})");
		}
		int totalUISum = feesUI + othersUI + context.getAdultTotalAmt() + context.getChildTotalAmt()
				+ context.getInfantTotalAmount();
		Assert.assertEquals(totalAmt, totalUISum, "Sum of all amounts not same as total amount");
	}

	public static void removeCharityAndCheck(WebDriver driver, ContextPojo context) throws Throwable {
		waitForPageToLoad(driver);
		String fareAmtXpath = Xpaths.FARE_TYPE_COST;
		expandCollapseItnryNode(driver, Xpaths.EXPAND_ICON_OTHER_SERVICES, "expand");
		WebElement charity = CommonMethods.waitForElementToBeVisible(driver,
				fareAmtXpath.replace(":FareType:", "Charity"), 5);
		if (!(charity == null)) {
			CommonMethods.waitForElementToBeVisible(driver, Xpaths.CHARITY_CHECKBOX, 5);
			CommonMethods.waitForElementToBeClickable(driver, Xpaths.CHARITY_CHECKBOX, 5).click();
		}
		Assert.assertTrue(CommonMethods.waitForElementToBePresent(driver, fareAmtXpath.replace(":FareType:", "Charity"),
				5) == null, "Charity not removed");
		expandCollapseItnryNode(driver, Xpaths.EXPAND_ICON_OTHER_SERVICES, "expand");
		validateTotalAmount(driver, context);
	}

	public static void addInsuranceAndCheck(WebDriver driver, ContextPojo context) throws Throwable {
		waitForPageToLoad(driver);
		String fareAmtXpath = Xpaths.FARE_TYPE_COST;
		expandCollapseItnryNode(driver, Xpaths.EXPAND_ICON_OTHER_SERVICES, "expand");
		WebElement insurance = CommonMethods.waitForElementToBeVisible(driver,
				fareAmtXpath.replace(":FareType:", "Insurance"), 5);
		if (insurance == null) {
			WebElement insuranceRadio = CommonMethods.waitForElementToBePresent(driver, Xpaths.OPT_FOR_INSURANCE, 5);
			if (!(insuranceRadio == null)) {
				insuranceRadio.click();
				waitForPageToLoad(driver);
				CommonMethods.hoverToElement(driver, Xpaths.EXPAND_ICON_BASE_FARE);
				expandCollapseItnryNode(driver, Xpaths.EXPAND_ICON_OTHER_SERVICES, "expand");
				Assert.assertTrue(
						CommonMethods.isElementVisible(driver, fareAmtXpath.replace(":FareType:", "Insurance"), 5),
						"Insurance not applied");
			} else {
				System.out.println("Insurance opt panel not found");
			}
		} else {
			System.out.println("Since, user is opted for insurance, to check, removing it");
			WebElement insuranceRadio = CommonMethods.waitForElementToBePresent(driver, Xpaths.OPT_OUT_OF_INSURANCE, 5);
			insuranceRadio.click();
			waitForPageToLoad(driver);
			CommonMethods.hoverToElement(driver, Xpaths.EXPAND_ICON_BASE_FARE);
			expandCollapseItnryNode(driver, Xpaths.EXPAND_ICON_OTHER_SERVICES, "expand");
			Assert.assertTrue(
					!CommonMethods.isElementVisible(driver, fareAmtXpath.replace(":FareType:", "Insurance"), 5),
					"Insurance not applied");
		}
		expandCollapseItnryNode(driver, Xpaths.EXPAND_ICON_OTHER_SERVICES, "collapse");
		validateTotalAmount(driver, context);
	}

	public static void expandCollapseItnryNode(WebDriver driver, String nodeXpath, String action) throws Throwable {
		WebElement nodeBtnWe = CommonMethods.waitForElementToBeVisible(driver, nodeXpath, 5);
		if (!(nodeBtnWe == null))
			if (action.equalsIgnoreCase("expand")) {
				if (!(nodeBtnWe.getAttribute("class").contains("open"))) {
					CommonMethods.waitForElementToBeClickable(driver, nodeXpath, 5).click();
				}
			} else if (action.equalsIgnoreCase("collapse")) {
				if (nodeBtnWe.getAttribute("class").contains("open")) {
					CommonMethods.waitForElementToBeClickable(driver, nodeXpath, 5).click();
				}
			}
	}
}

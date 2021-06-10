package com.utils;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class CommonMethods {

	public static boolean waitForElementToBeInvisible(WebDriver driver, String xPath, int timeout) throws Throwable {
		try {
			Duration timeOut = Duration.ofSeconds(timeout);
			System.out.println("Waiting for element to be invisible - " + xPath);
			boolean result = new FluentWait<WebDriver>(driver).withTimeout(timeOut).pollingEvery(Duration.ofMillis(100))
					.ignoring(Throwable.class).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
			System.out.println("Element invisible - " + result);
			Thread.sleep(2000);
			return result;
		} catch (RuntimeException e) {
			System.out.println("error while performing wait operation - " + e);
			return false;
		}
	}
	
	public static WebElement waitForElementToBeVisible(WebDriver driver, String xPath, int timeout) throws Throwable {
		Thread.sleep(2000);
		try {
			Duration timeOut = Duration.ofSeconds(timeout);
			System.out.println("Waiting for element to be visible - " + xPath);

			WebElement wt = new FluentWait<WebDriver>(driver).withTimeout(timeOut)
					.pollingEvery(Duration.ofMillis(100)).ignoring(Throwable.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
			System.out.println("Element visible " + wt);
			return wt;
		} catch (RuntimeException e) {
			System.out.println("error while performing wait operation - " + e);
			return null;
		}
	}
	
	public static WebElement waitForElementToBeClickable(WebDriver driver, String xPath, int timeout) throws Throwable {
		Thread.sleep(2000);
		try {
			Duration timeOut = Duration.ofSeconds(timeout);
			System.out.println("Waiting for element to be clickable - " + xPath);

			WebElement wt = new FluentWait<WebDriver>(driver).withTimeout(timeOut)
					.pollingEvery(Duration.ofMillis(100)).ignoring(Throwable.class)
					.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
			System.out.println("Element clickable " + wt);
			return wt;
		} catch (RuntimeException e) {
			System.out.println("error while performing wait operation - " + e);
			return null;
		}
	}
	
	public static WebElement waitForElementToBePresent(WebDriver driver, String xPath, int timeout) throws Throwable {
		Thread.sleep(2000);
		try {
			Duration timeOut = Duration.ofSeconds(timeout);
			System.out.println("Waiting for element to be present - " + xPath);
			WebElement wt = new FluentWait<WebDriver>(driver).withTimeout(timeOut)
					.pollingEvery(Duration.ofMillis(100)).ignoring(Throwable.class)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
			System.out.println("Element present " + wt);
			return wt;
		} catch (RuntimeException e) {
			System.out.println("error while performing wait operation - " + e);
			return null;
		}
	}
	
	public static void hoverToElement(WebDriver driver,String xPath) throws Throwable {
		Thread.sleep(2000);
		WebElement we = waitForElementToBePresent(driver,xPath,60);
		try {
			Actions action = new Actions(driver);
			action.moveToElement(we).build().perform();
		} catch (StaleElementReferenceException staleEx) {
			System.out.println("Error while performing hover operation - " + staleEx + ".Trying again....");
			we = waitForElementToBePresent(driver, xPath, 180);			
			Actions action = new Actions(driver);
			action.moveToElement(we).build().perform();
		} catch (RuntimeException e) {
			System.out.println("Error while performing hover operation - " + e);
			throw e;
		}
	}
	
	public static boolean isElementVisible(WebDriver driver, String xPath, int timeout) throws Throwable {
		Thread.sleep(2000);
		try {
			Duration timeOut = Duration.ofSeconds(timeout);
			System.out.println("Waiting for element to be visible - " + xPath);
			WebElement wt = new FluentWait<WebDriver>(driver).withTimeout(timeOut)
					.pollingEvery(Duration.ofMillis(100)).ignoring(Throwable.class)
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
			System.out.println("Element visible " + wt);
			if(wt == null) {
				return false;
			}
			return true;
		} catch (RuntimeException e) {
			System.out.println("error while performing wait operation - " + e);
			return false;
		}
	}
	
	public static int getMatchedInt(String word, String regex) throws Throwable {
		String dataMatched = null;
		word = word.replace(",", "");
		Pattern extractionPattern = Pattern.compile(regex);
		Matcher matchedLine = extractionPattern.matcher(word);
		if(matchedLine.find()) {
			dataMatched = matchedLine.group(1);
			System.out.println("Number extracted: " + word);
		} else {
			new RuntimeException("No matches in string " + word + " for regex pattern " + regex);
		}
		try {
			return Integer.parseInt(dataMatched);
		} catch (Exception e) {
			new RuntimeException("Error while converting " + dataMatched + " to string");
		}
		return 0;
	}
}
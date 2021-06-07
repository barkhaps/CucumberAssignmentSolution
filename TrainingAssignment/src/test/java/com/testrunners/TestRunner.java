package com.testrunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;

@CucumberOptions(plugin = { "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"json:target/cucumber-test-reports/Cucumber.json",
		"rerun:reports/rerun.txt" }, features = "src/test/resources", glue = "com.stepdefinitions", tags = "@MMT_Automation")

public class TestRunner extends AbstractTestNGCucumberTests {
	
	String emailFrom = "";
	String emailFromPw = "";
	String toEmails = "";
	
	@BeforeSuite
	public void testStartedPointer() {
		System.out.println("Test Execution Started");
	}

	/**
	 * This method is used to connect to Gmail with username and password and send email
	 * 
	 * @param username
	 * @param password
	 * @throws Throwable
	 */
	public void sendEmail(String username, String password) throws Throwable {
		String emailSubject = "Test Report on " + new Date();
		String emailMsg = "This is an automatically generated email for test run";
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath("test-output/emailable-report.html");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Test Report");
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("imap.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setSSLCheckServerIdentity(true);
		email.setSSLOnConnect(true);
		System.out.println("Connected to gmail..");
		System.out.println("Sending mail to: " + toEmails);
		System.out.println("Subject: " + emailSubject + "\nEmail Body: " + emailMsg);
		email.setFrom(username);
		email.setSubject(emailSubject);
		email.setMsg(emailMsg);
		String[] emails = toEmails.split(",");
		email.addTo(emails);
		email.attach(attachment);
		email.send();
		System.out.println("Email Sent..");
	}
	
	@AfterSuite
	public void testEndPointer() throws Throwable {
		System.out.println("In After Suite. Sending emailable report");
		sendEmail(emailFrom, emailFromPw);
	}

}

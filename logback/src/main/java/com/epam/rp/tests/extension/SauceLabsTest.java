package com.epam.rp.tests.extension;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Extension example
 *
 * @author Andrei Varabyeu
 */
//@Listeners(ExtendedListener.class)
public class SauceLabsTest implements SauceOnDemandSessionIdProvider {

	private static WebDriver driver;

	private static final Logger LOGGER = LoggerFactory.getLogger(RetryTest.class);

	@BeforeClass
	public static void init() throws MalformedURLException {
		/** Here we set environment variables from your local machine, or IntelliJ run configuration,
		 *  and store these values in the variables below. Doing this is a best practice in terms of test execution
		 *  and security. If you're not sure how to use env variables, refer to this guide -
		 * https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials
		 * or check testng-README.md */

		/**
		 * Here we set DesiredCapabilities, in this exercise we set additional capabilities below that align with
		 * testing best practices such as timeouts, tags, and build numbers
		 */
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("username", sauceUserName);
		capabilities.setCapability("accessKey", sauceAccessKey);
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("version", "59.0");
		capabilities.setCapability("name", "4-best-practices");

		/** Tags are an excellent way to control and filter your test automation
		 * in Sauce Analytics. Get a better view into your test automation.
		 */
		List<String> tags = Arrays.asList("sauceDemo", "demoTest", "module4");
		capabilities.setCapability("tags", tags);

		/** Another of the most important things that you can do to get started
		 * is to set timeout capabilities for Sauce based on your organizations needs. For example:
		 * How long is the whole test allowed to run?*/
		capabilities.setCapability("maxDuration", 3600);
		/** A Selenium crash might cause a session to hang indefinitely.
		 * Below is the max time allowed to wait for a Selenium command*/
		capabilities.setCapability("commandTimeout", 600);
		/** How long can the browser wait for a new command */
		capabilities.setCapability("idleTimeout", 1000);

		/** Setting a build name is one of the most fundamental pieces of running
		 * successful test automation. Builds will gather all of your tests into a single
		 * 'test suite' that you can analyze for results.
		 * It's a best practice to always group your tests into builds. */
		capabilities.setCapability("build", "SauceDemo");

		/** If you're accessing the EU data center, use the following endpoint:.
		 * https://ondemand.eu-central-1.saucelabs.com/wd/hub
		 * */
		driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
	}

	@Test
	public void shouldOpenChrome() throws MalformedURLException {

		/** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
		LOGGER.info("Initialize Web Driver parameters");
		LOGGER.info("Opening a new browser session");
		LOGGER.info("Navigate to https://www.saucedemo.com");
		driver.navigate().to("https://www.saucedemo.com");

		LOGGER.info("Get page source");
		String pageSource = driver.getPageSource();

		Assert.assertTrue(true);

	}

	/**
	 * Below we are performing 2 critical actions. Quitting the driver and passing
	 * the test result to Sauce Labs user interface using the Javascript Executor.
	 */
	@AfterMethod
	public void cleanUpAfterTestMethod(ITestResult result) {
		((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
		driver.quit();
	}

	@Override
	public String getSessionId() {
		SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
		if (sessionId != null) {
			return sessionId.toString();
		}
		return null;
	}

	private static String sauceUserName = "Pavel_bortnik";
	private static String sauceAccessKey = "f8c124f5-8c9f-48ec-b5cd-de6d09c6b17b";
	private static String sauceURL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
}

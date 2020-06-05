package org.springframework.samples.bdd.stepdefs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.web.server.LocalServerPort;

public class CommonStep {
	// https://medium.com/@bcarunmail/sharing-state-between-cucumber-step-definitions-using-java-and-spring-972bc31117af

	@LocalServerPort
	public int port;

	private static WebDriver driver;

	public WebDriver getDriver() {
		if (driver == null || isDriverSessionIdNull()) {
			String value = System.getenv("webdriver.gecko.driver");
			System.setProperty("webdriver.gecko.driver", value);
			
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get("http://localhost:"+port);
		}
		
		return driver;
	}

	public Boolean isDriverSessionIdNull() {
		return ((FirefoxDriver)driver).getSessionId() == null;
	}
}

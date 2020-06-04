package org.springframework.samples.bdd.stepdefs;

import org.openqa.selenium.By;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;

public class LoginStepDefinitions extends CommonStep {

	private String getPassword(String user) {
		switch (user) {
			case "admin1":
				return "admin";
			case "pepegotera":
				return "pepegotera";
			case "elenanito":
				return "elenanito";
			case "professional1":
				return "professional1";
			default:
				return "password";
		}
	}
	
	@Given("I am authenticated as {string}")
	public void IAmAuthenticatedAs(String user) throws Exception {
		getDriver().findElement(By.linkText("Área clientes")).click();
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys(user);
		getDriver().findElement(By.id("password")).click();
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys(getPassword(user));
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}
	
	@After
	public void closeDriver() {
		getDriver().quit();
	}
	

}
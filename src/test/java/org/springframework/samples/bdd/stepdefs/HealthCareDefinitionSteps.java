package org.springframework.samples.bdd.stepdefs;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class HealthCareDefinitionSteps extends CommonStep {

	@And("I try to sign up with valid insurance")
	public void validBillAmount() throws Exception{		
		getDriver().findElement(By.id("firstName")).click();
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("P");
		getDriver().findElement(By.id("firstName")).sendKeys(Keys.DOWN);
		getDriver().findElement(By.id("firstName")).sendKeys(Keys.TAB);
		getDriver().findElement(By.id("lastName")).clear();
		getDriver().findElement(By.id("lastName")).sendKeys("G");
		getDriver().findElement(By.id("lastName")).sendKeys(Keys.DOWN);
		getDriver().findElement(By.id("lastName")).sendKeys(Keys.TAB);
		getDriver().findElement(By.id("document")).clear();
		getDriver().findElement(By.id("document")).sendKeys("2");
		getDriver().findElement(By.id("document")).click();
		getDriver().findElement(By.id("document")).sendKeys(Keys.DOWN);
		getDriver().findElement(By.id("document")).sendKeys(Keys.TAB);
		getDriver().findElement(By.id("birthDate")).click();
		getDriver().findElement(By.id("birthDate")).clear();
		getDriver().findElement(By.id("birthDate")).sendKeys("1983-05-25");
		getDriver().findElement(By.id("healthInsurance")).click();
		new Select(getDriver().findElement(By.id("healthInsurance"))).selectByVisibleText("Adeslas");
		getDriver().findElement(By.xpath("//option[@value='ADESLAS']")).click();
		getDriver().findElement(By.id("healthCardNumber")).click();
		getDriver().findElement(By.id("healthCardNumber")).clear();
		getDriver().findElement(By.id("healthCardNumber")).sendKeys("28334457853");
		getDriver().findElement(By.id("email")).click();
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys("pepegotera@gmail.com");
		getDriver().findElement(By.id("user.username")).click();
		getDriver().findElement(By.id("user.username")).clear();
		getDriver().findElement(By.id("user.username")).sendKeys("pepegotera3");
		getDriver().findElement(By.id("user.password")).click();
		getDriver().findElement(By.id("user.password")).clear();
		getDriver().findElement(By.id("user.password")).sendKeys("pepegotera");
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		
	}
	
	@Then("I am signed up with invalid insurance")
	public void billHasBeenCharged() throws Exception {		
		Assert.assertEquals("> Citación online", getDriver().findElement(By.linkText("> Citación online")).getText());
	}
	
	@And("I try to sign up with invalid insurance")
	public void invalidBillAmount() throws Exception{
		getDriver().findElement(By.id("firstName")).click();
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("Elena");
		getDriver().findElement(By.id("firstName")).sendKeys(Keys.DOWN);
		getDriver().findElement(By.id("firstName")).sendKeys(Keys.TAB);
		getDriver().findElement(By.id("lastName")).clear();
		getDriver().findElement(By.id("lastName")).sendKeys("N");
		getDriver().findElement(By.id("lastName")).sendKeys(Keys.DOWN);
		getDriver().findElement(By.id("lastName")).sendKeys(Keys.TAB);
		getDriver().findElement(By.id("document")).clear();
		getDriver().findElement(By.id("document")).sendKeys("2");
		getDriver().findElement(By.id("document")).sendKeys(Keys.DOWN);
		getDriver().findElement(By.id("document")).sendKeys(Keys.TAB);
		getDriver().findElement(By.id("birthDate")).click();
		getDriver().findElement(By.id("birthDate")).clear();
		getDriver().findElement(By.id("birthDate")).sendKeys("1983-05-25");
		getDriver().findElement(By.id("healthInsurance")).click();
		new Select(getDriver().findElement(By.id("healthInsurance"))).selectByVisibleText("Sanitas");
		getDriver().findElement(By.xpath("//option[@value='SANITAS']")).click();
		getDriver().findElement(By.id("email")).click();
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys("elenanito@gmail.com");
		getDriver().findElement(By.id("user.username")).click();
		getDriver().findElement(By.id("user.username")).clear();
		getDriver().findElement(By.id("user.username")).sendKeys("elenanito");
		getDriver().findElement(By.id("user.password")).click();
		getDriver().findElement(By.id("user.password")).clear();
		getDriver().findElement(By.id("user.password")).sendKeys("elenanito");
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		getDriver().findElement(By.xpath("//form[@id='add-owner-form']/div/div[3]/div[2]")).click();
		
	}
	
	@Then("I am not signed up with invalid insurance")
	public void iHaveNotSignUp() throws Exception {		
		Assert.assertEquals("you must write your health card number", getDriver().findElement(By.xpath("//form[@id='add-owner-form']/div/div[4]/div[2]/div/div")).getText());
	}
	


}
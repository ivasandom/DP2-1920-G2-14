package org.springframework.samples.bdd.stepdefs;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ClientSignUpDefinitionSteps extends CommonStep {
	
    @Given("I am on sign up page")
    public void IVisitBillFormPage() {
    	getDriver().findElement(By.linkText("> Citación online")).click();
    	getDriver().findElement(By.linkText("Regístrate")).click();
    }

    
	@And("I try to sign up with valid data")
	public void validBillAmount() throws Exception{		
		getDriver().findElement(By.id("firstName")).click();
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("Luís");
		getDriver().findElement(By.id("lastName")).clear();
		getDriver().findElement(By.id("lastName")).sendKeys("Pérez");
		getDriver().findElement(By.id("document")).click();
		getDriver().findElement(By.id("document")).clear();
		getDriver().findElement(By.id("document")).sendKeys("17457215K");
		getDriver().findElement(By.id("birthDate")).click();
		getDriver().findElement(By.id("birthDate")).clear();
		getDriver().findElement(By.id("birthDate")).sendKeys("1983-05-25");
		getDriver().findElement(By.id("healthInsurance")).click();
		new Select(getDriver().findElement(By.id("healthInsurance"))).selectByVisibleText("Mapfre");
		getDriver().findElement(By.xpath("//option[@value='MAPFRE']")).click();
		getDriver().findElement(By.id("healthCardNumber")).click();
		getDriver().findElement(By.id("healthCardNumber")).clear();
		getDriver().findElement(By.id("healthCardNumber")).sendKeys("12345");
		getDriver().findElement(By.id("email")).click();
		getDriver().findElement(By.id("email")).clear();
		getDriver().findElement(By.id("email")).sendKeys("luis@gmail.com");
		getDriver().findElement(By.id("user.username")).click();
		getDriver().findElement(By.id("user.username")).clear();
		getDriver().findElement(By.id("user.username")).sendKeys("luisperez");
		getDriver().findElement(By.id("user.password")).click();
		getDriver().findElement(By.id("user.password")).clear();
		getDriver().findElement(By.id("user.password")).sendKeys("luisperez");
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		getDriver().findElement(By.linkText("> Citación online")).click();
		getDriver().findElement(By.id("username")).click();
		getDriver().findElement(By.id("username")).clear();
		getDriver().findElement(By.id("username")).sendKeys("luisperez");
		getDriver().findElement(By.id("password")).click();
		getDriver().findElement(By.id("password")).clear();
		getDriver().findElement(By.id("password")).sendKeys("luisperez");
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		
	}
	
	@Then("I have been signed up")
	public void iHaveBeenSignedUp() throws Exception {		
		Assert.assertEquals("luisperez", getDriver().findElement(By.xpath("//div[@id='navbarSupportedContent']/ul[2]/li/a")).getText());
	}
	
	@And("I try to sign up without email")
	public void invalidBillAmount() throws Exception{		
		getDriver().findElement(By.id("firstName")).click();
		getDriver().findElement(By.id("firstName")).clear();
		getDriver().findElement(By.id("firstName")).sendKeys("María");
		getDriver().findElement(By.id("lastName")).clear();
		getDriver().findElement(By.id("lastName")).sendKeys("Sanz");
		getDriver().findElement(By.id("document")).clear();
		getDriver().findElement(By.id("document")).sendKeys("12343213J");
		getDriver().findElement(By.id("birthDate")).click();
		getDriver().findElement(By.id("birthDate")).clear();
		getDriver().findElement(By.id("birthDate")).sendKeys("1983-05-25");
		getDriver().findElement(By.id("healthInsurance")).click();
		new Select(getDriver().findElement(By.id("healthInsurance"))).selectByVisibleText("Cigna");
		getDriver().findElement(By.xpath("//option[@value='CIGNA']")).click();
		getDriver().findElement(By.id("healthCardNumber")).click();
		getDriver().findElement(By.id("healthCardNumber")).clear();
		getDriver().findElement(By.id("healthCardNumber")).sendKeys("43235");
		getDriver().findElement(By.id("user.username")).click();
		getDriver().findElement(By.id("user.username")).clear();
		getDriver().findElement(By.id("user.username")).sendKeys("marisan");
		getDriver().findElement(By.id("user.password")).click();
		getDriver().findElement(By.id("user.password")).clear();
		getDriver().findElement(By.id("user.password")).sendKeys("marisan");
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}
	
	@Then("I have not been signed up")
	public void iHaveNotSignUp() throws Exception {		
		Assert.assertEquals("must not be empty", getDriver().findElement(By.xpath("//form[@id='add-owner-form']/div/div[5]/div")).getText());
	}
	


}
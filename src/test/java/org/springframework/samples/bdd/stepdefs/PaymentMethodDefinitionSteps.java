package org.springframework.samples.bdd.stepdefs;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class PaymentMethodDefinitionSteps extends CommonStep {
	
    @Given("I am on new payment method form")
    public void IVisitBillFormPage() {
		getDriver().findElement(By.id("navbarDropdown")).click();
		getDriver().findElement(By.linkText("My payment methods")).click();
    }
    
	@And("I try to save a valid payment method")
	public void addNewPaymentMethod() throws Exception{		
		getDriver().findElement(By.linkText("+ Add card")).click();
		getDriver().switchTo().frame(1);
		getDriver().findElement(By.name("cardnumber")).click();
		getDriver().findElement(By.name("cardnumber")).clear();
		getDriver().findElement(By.name("cardnumber")).sendKeys("4242 4242 4242 4242");
		getDriver().findElement(By.name("exp-date")).clear();
		getDriver().findElement(By.name("exp-date")).sendKeys("04 / 22");
		getDriver().findElement(By.name("cvc")).clear();
		getDriver().findElement(By.name("cvc")).sendKeys("422");
		getDriver().findElement(By.name("postal")).clear();
		getDriver().findElement(By.name("postal")).sendKeys("41012");
		getDriver().switchTo().defaultContent();
		getDriver().findElement(By.id("add-button")).click();
		
	}
	
	@Then("Payment method is saved")
	public void paymentMethodShown() throws Exception {		
		Assert.assertEquals("Token", getDriver().findElement(By.xpath("//th[2]")).getText());
	}
	
	@And("I try to save a duplicated payment method")
	public void addNewPaymentMethodDuplicated() throws Exception{		
		getDriver().findElement(By.linkText("+ Add card")).click();
		getDriver().switchTo().frame(1);
		getDriver().findElement(By.name("cardnumber")).click();
		getDriver().findElement(By.name("cardnumber")).clear();
		getDriver().findElement(By.name("cardnumber")).sendKeys("4242 4242 4242 4242");
		getDriver().findElement(By.name("exp-date")).clear();
		getDriver().findElement(By.name("exp-date")).sendKeys("04 / 22");
		getDriver().findElement(By.name("cvc")).clear();
		getDriver().findElement(By.name("cvc")).sendKeys("422");
		getDriver().findElement(By.name("postal")).clear();
		getDriver().findElement(By.name("postal")).sendKeys("41012");
		getDriver().switchTo().defaultContent();
		getDriver().findElement(By.id("add-button")).click();		
	}
	
	@Then("Payment method is not saved")
	public void paymentMethodNotShown() throws Exception {		
		Assert.assertEquals("Card already exists.", getDriver().findElement(By.id("token.errors")).getText());
	}
	


}
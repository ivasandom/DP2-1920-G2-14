package org.springframework.samples.bdd.stepdefs;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class BillChargeDefinitionSteps extends CommonStep {
	
    @Given("I am on bill charge form")
    public void IVisitBillFormPage() {
		getDriver().findElement(By.linkText("> Panel de administración")).click();
		getDriver().findElement(By.linkText("Bills")).click();
		getDriver().findElement(By.linkText("View details")).click();
		getDriver().findElement(By.linkText("Charge bill")).click();
    }
    
	@And("I try to charge a valid amount")
	public void validBillAmount() throws Exception{		
		getDriver().findElement(By.id("amount")).clear();
		getDriver().findElement(By.id("amount")).sendKeys("100.00");
		getDriver().findElement(By.xpath("//option[@value='BANKTRANSFER']")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}
	
	@Then("Amount is charged")
	public void billHasBeenCharged() throws Exception {		
		assertEquals("100.0 $", getDriver().findElement(By.xpath("//table/tbody/tr/td[2]")).getText());
		assertEquals("Bank Transfer", getDriver().findElement(By.xpath("//table/tbody/tr/td[3]")).getText());
	}
	
	@And("I try to charge a invalid amount")
	public void invalidBillAmount() throws Exception{		
		getDriver().findElement(By.id("amount")).clear();
		getDriver().findElement(By.id("amount")).sendKeys("-100.00");
		getDriver().findElement(By.xpath("//option[@value='BANKTRANSFER']")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}
	
	@Then("Amount is not charged")
	public void billHasNotBeenCharged() throws Exception {		
		assertEquals("amount must be bigger than zero.transaction.amount", getDriver().findElement(By.className("invalid-feedback")).getText());
	}
	

}
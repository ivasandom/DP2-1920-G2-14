package org.springframework.samples.bdd.stepdefs;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CenterElectionDefinitionSteps extends CommonStep {
	
    @Given("I am on new appointment page")
    public void IVisitBillFormPage() {
    	getDriver().findElement(By.linkText("> Citación online")).click();
    }

	@And("I try to select a center with professionals")
	public void validBillAmount() throws Exception{		
		new Select(getDriver().findElement(By.id("center"))).selectByVisibleText("Sevilla");
		getDriver().findElement(By.xpath("//option[@value='1']")).click();
		getDriver().findElement(By.id("specialty")).click();
		new Select(getDriver().findElement(By.id("specialty"))).selectByVisibleText("dermatology");
		getDriver().findElement(By.xpath("(//option[@value='1'])[2]")).click();
		
	}
	
	@Then("I find professionals with center")
	public void billHasBeenCharged() throws Exception {		
		assertEquals("Professional", getDriver().findElement(By.xpath("//div[3]/label")).getText());
	}
	
	@And("I try to select a center with no professionals")
	public void invalidBillAmount() throws Exception{		
		new Select(getDriver().findElement(By.id("center"))).selectByVisibleText("Cadiz");
		getDriver().findElement(By.xpath("//option[@value='2']")).click();
		getDriver().findElement(By.id("specialty")).click();
		new Select(getDriver().findElement(By.id("specialty"))).selectByVisibleText("surgery");
		getDriver().findElement(By.xpath("(//option[@value='2'])[2]")).click();
		
		
	}
	
	@Then("I find no professionals with center")
	public void billHasNotBeenCharged() throws Exception {		
		Assert.assertEquals("No hay profesionales con esos parÃ¡metros.", getDriver().findElement(By.xpath("//form[@id='appointment']/p")).getText());
	}
	


}
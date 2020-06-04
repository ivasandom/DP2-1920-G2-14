package org.springframework.samples.bdd.stepdefs;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.web.server.LocalServerPort;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class SpecialityElectionDefinitionSteps extends CommonStep {
	
    @Given("I am on find professionals page")
    public void IVisitBillFormPage() {
    	getDriver().findElement(By.linkText("> Citación online")).click();
    }
    
	@And("I try to find professionals with valid data")
	public void selectASpecialtyWithProfessionals() throws Exception{		
		getDriver().findElement(By.id("center")).click();
		new Select(getDriver().findElement(By.id("center"))).selectByVisibleText("Sevilla");
		getDriver().findElement(By.xpath("//option[@value='1']")).click();
		getDriver().findElement(By.id("specialty")).click();
		new Select(getDriver().findElement(By.id("specialty"))).selectByVisibleText("dermatology");
		getDriver().findElement(By.xpath("(//option[@value='1'])[2]")).click();
		getDriver().findElement(By.xpath("//div/div")).click();
	}
	
	@Then("I find professionals")
	public void professionalHasBeenShown() throws Exception {		
		Assert.assertEquals("Professional", getDriver().findElement(By.xpath("//form[@id='appointment']/div/div[3]/label")).getText());
	}
	
	@And("Try to find professionals without specialty")
	public void selectASpecialtyWithNoProfessionals() throws Exception{		
		new Select(getDriver().findElement(By.id("center"))).selectByVisibleText("Cadiz");
		getDriver().findElement(By.xpath("//option[@value='2']")).click();
		getDriver().findElement(By.id("specialty")).click();
		new Select(getDriver().findElement(By.id("specialty"))).selectByVisibleText("surgery");
		getDriver().findElement(By.xpath("(//option[@value='2'])[2]")).click();
	}
	
	@Then("I do not find professionals")
	public void professionalHasNotBeenShown() throws Exception {		
		Assert.assertEquals("No hay profesionales con esos parÃ¡metros.", getDriver().findElement(By.xpath("//form[@id='appointment']/p")).getText());
	}
	


}
package org.springframework.samples.bdd.stepdefs;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.web.server.LocalServerPort;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class DeseasesDefinitionSteps extends CommonStep {
	
    
	@And("I try to see previous client deseases")
	public void seeClientDeseases() throws Exception{		
		getDriver().findElement(By.linkText("Consultation mode")).click();
		getDriver().findElement(By.linkText("> Start consultation")).click();
		getDriver().findElement(By.linkText("View clinical history")).click();
	}
	
	@Then("I see previous deseases")
	public void deaseasesAreShown() throws Exception {		
		Assert.assertEquals("Acidez de estómago", getDriver().findElement(By.xpath("//table[3]/tbody/tr/td/dl/dd")).getText());
	}
	
	@And("I try to see previous client deseases with none")
	public void seeClientWithNoDeasesDeseases() throws Exception{		
		getDriver().findElement(By.id("navbarDropdown")).click();
		getDriver().findElement(By.linkText("Client List")).click();
		getDriver().findElement(By.linkText("Miguel Perez")).click();
		
	}
	
	@Then("I see no previous deseases")
	public void deseasesAreNotShown() throws Exception {		
		Assert.assertEquals("No hay datos", getDriver().findElement(By.xpath("//table[3]/tbody/tr/td")).getText());
	}
	

}
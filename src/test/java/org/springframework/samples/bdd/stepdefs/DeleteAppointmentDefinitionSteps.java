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

public class DeleteAppointmentDefinitionSteps extends CommonStep {
	
    @Given("I am on appointment detail page")
    public void IVisitBillFormPage() {
    	getDriver().findElement(By.linkText("> Citación online")).click();
    }

	@And("I try to delete a future appointment")
	public void deleteAFutureAppointment() throws Exception{		
		getDriver().findElement(By.id("navbarDropdown")).click();
		getDriver().findElement(By.linkText("Mis citas")).click();
		getDriver().findElement(By.xpath("(//a[contains(text(),'View more')])[124]")).click();
		getDriver().findElement(By.linkText("CANCEL APPOINTMENT")).click();
	}
	
	@Then("Appointment is deleted")
	public void appointmentHasBeenDeleted() throws Exception {		
		Assert.assertEquals("2020-08-31", getDriver().findElement(By.xpath("//table[@id='ownersTable']/tbody/tr[123]/td")).getText());
	}
	
	@And("I try to delete a past appointment")
	public void deleteAPastAppointment() throws Exception{		
		getDriver().findElement(By.id("navbarDropdown")).click();
		getDriver().findElement(By.linkText("Mis citas")).click();
		getDriver().findElement(By.linkText("View more")).click();
		getDriver().findElement(By.linkText("CANCEL APPOINTMENT")).click();
	}
	
	@Then("Appointment is not deleted")
	public void appointmentHasNotBeenDeleted() throws Exception {		
		Assert.assertEquals("Error: You cannot delete a passed appointment", getDriver().findElement(By.xpath("//p[2]")).getText());
	}
	


}
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

public class ReasonAppointmentDefinitionSteps extends CommonStep {
    
	@And("I try to create an appointment with valid a reason")
	public void saveAppointmentWithReason() throws Exception{		
		getDriver().findElement(By.id("center")).click();
		new Select(getDriver().findElement(By.id("center"))).selectByVisibleText("Sevilla");
		getDriver().findElement(By.xpath("//option[@value='1']")).click();
		getDriver().findElement(By.id("specialty")).click();
		new Select(getDriver().findElement(By.id("specialty"))).selectByVisibleText("dermatology");
		getDriver().findElement(By.xpath("(//option[@value='1'])[2]")).click();
		getDriver().findElement(By.id("professional")).click();
		new Select(getDriver().findElement(By.id("professional"))).selectByVisibleText("Guillermo Diaz");
		getDriver().findElement(By.xpath("(//option[@value='1'])[3]")).click();
		getDriver().findElement(By.id("reason")).click();
		getDriver().findElement(By.id("reason")).sendKeys("Badly");
		getDriver().findElement(By.id("date")).click();
		getDriver().findElement(By.id("date")).clear();
		getDriver().findElement(By.id("date")).sendKeys("10/10/2022");
		getDriver().findElement(By.id("startTime")).click();
		new Select(getDriver().findElement(By.id("startTime"))).selectByVisibleText("08:00:00");
		getDriver().findElement(By.id("startTime")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}
	
	@Then("Appointment is created with reason")
	public void appointmentHasBeenCreated() throws Exception {		
		Assert.assertEquals("My appointments + New appointment", getDriver().findElement(By.xpath("//h2")).getText());
	}
	
	@And("I try to create an appointment without a reason")
	public void saveAppointmentWithNoReason() throws Exception{		
		getDriver().findElement(By.id("center")).click();
		new Select(getDriver().findElement(By.id("center"))).selectByVisibleText("Sevilla");
		getDriver().findElement(By.xpath("//option[@value='1']")).click();
		getDriver().findElement(By.id("specialty")).click();
		new Select(getDriver().findElement(By.id("specialty"))).selectByVisibleText("dermatology");
		getDriver().findElement(By.xpath("(//option[@value='1'])[2]")).click();
		getDriver().findElement(By.id("professional")).click();
		new Select(getDriver().findElement(By.id("professional"))).selectByVisibleText("Guillermo Diaz");
		getDriver().findElement(By.xpath("(//option[@value='1'])[3]")).click();
		getDriver().findElement(By.id("reason")).click();
		getDriver().findElement(By.id("reason")).clear();
		getDriver().findElement(By.id("date")).click();
		getDriver().findElement(By.id("date")).clear();
		getDriver().findElement(By.id("date")).sendKeys("10/10/2019");
		getDriver().findElement(By.id("startTime")).click();
		new Select(getDriver().findElement(By.id("startTime"))).selectByVisibleText("08:00:00");
		getDriver().findElement(By.id("startTime")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		getDriver().findElement(By.id("professional")).click();
		new Select(getDriver().findElement(By.id("professional"))).selectByVisibleText("Guillermo Diaz");
		getDriver().findElement(By.xpath("(//option[@value='1'])[3]")).click();
	}
	
	@Then("Appointment with no reason is not created")
	public void appointmentHasNotBeenCreated() throws Exception {		
		Assert.assertEquals("the date must be in future.appointment.date", getDriver().findElement(By.xpath("//div[@id='appointment-date-group']/div[3]/div")).getText());
	}


}
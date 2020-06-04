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
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DateElectionDefinitionSteps extends CommonStep {

	@And("I try to save appointment with valid date")
	public void selectASpecialtyWithProfessionals() throws Exception{		
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
		getDriver().findElement(By.id("reason")).sendKeys("abdominal pain");
		getDriver().findElement(By.id("date")).click();
		getDriver().findElement(By.id("date")).clear();
		getDriver().findElement(By.id("date")).sendKeys("12/02/2021");
		getDriver().findElement(By.linkText("12")).click();
		getDriver().findElement(By.id("startTime")).click();
		new Select(getDriver().findElement(By.id("startTime"))).selectByVisibleText("16:15:00");
		getDriver().findElement(By.xpath("//option[@value='16:15:00']")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		
	}
	
	@Then("Appointment is saved")
	public void professionalHasBeenShown() throws Exception {		
		Assert.assertEquals("My appointments + New appointment", getDriver().findElement(By.xpath("//h2")).getText());
	}
	
	@And("I try to save appointment with invalid date")
	public void selectASpecialtyWithNoProfessionals() throws Exception{		
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
		getDriver().findElement(By.id("reason")).sendKeys("abdominal pain");
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
	
	@Then("Appointment is not saved")
	public void professionalHasNotBeenShown() throws Exception {		
		Assert.assertEquals("the date must be in future.appointment.date", getDriver().findElement(By.xpath("//div[@id='appointment-date-group']/div[3]/div")).getText());
	}
	


}
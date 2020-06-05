package org.springframework.samples.bdd.stepdefs;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ConsutationDefinitionSteps extends CommonStep {
	
    @Given("I am on consultation page")
    public void IVisitConsultationPage() {
		getDriver().findElement(By.linkText("Consultation mode")).click();
		getDriver().findElement(By.linkText("> Start consultation")).click();
    }
    
	@And("I try to save a diagnosis with valid data")
	public void saveConsultationValid() throws Exception{		
		getDriver().findElement(By.xpath("//form[@id='appointment']/div/div/div/a[2]/p")).click();
		getDriver().findElement(By.id("diagnosis.description")).click();
		getDriver().findElement(By.id("diagnosis.description")).clear();
		getDriver().findElement(By.id("diagnosis.description")).sendKeys("healthy");
		getDriver().findElement(By.id("list-diagnosis")).click();

		//Seleccion de las enfermedades (div[2])
		WebElement element = getDriver().findElement(By.xpath("//div[2]/span/span/span/ul"));

		element.click();

		Actions keyDown = new Actions(getDriver());
		keyDown.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

		//Seleccion de las medicinas (div[3])
		WebElement element1 = getDriver().findElement(By.xpath("//div[3]/span/span/span/ul"));
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0," + element1.getLocation().x + ")");
		element1.click();

		Actions keyDown1 = new Actions(getDriver());
		keyDown1.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

		getDriver().findElement(By.xpath("//form[@id='appointment']/div/div/div/a[3]")).click();
		getDriver().findElement(By.id("bill.price")).click();
		getDriver().findElement(By.id("bill.price")).clear();
		getDriver().findElement(By.id("bill.price")).sendKeys("12");
		getDriver().findElement(By.id("bill.iva")).click();
		getDriver().findElement(By.id("bill.iva")).clear();
		getDriver().findElement(By.id("bill.iva")).sendKeys("0.");
		WebElement element3 = getDriver().findElement(By.xpath("//div[@class='col-12 text-center']"));
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].style.visibility='hidden'", element3);
		WebElement element2 = getDriver().findElement(By.xpath("//div[2]/div/button"));
		element2.click();
	}
	
	@Then("Diagnosis is created")
	public void professionalHasBeenShown() throws Exception {		
		Assert.assertEquals("COMPLETED", getDriver().findElement(By.xpath("//table[@id='ownersTable']/tbody/tr/td[4]/span")).getText());
	}
	
	@And("I try to save a diagnosis without description")
	public void saveConsultationNoDescription() throws Exception{		
		getDriver().findElement(By.linkText("Consultation mode")).click();
		getDriver().findElement(By.linkText("> Start consultation")).click();
		getDriver().findElement(By.xpath("//form[@id='appointment']/div/div/div/a[2]/div/h5")).click();
		getDriver().findElement(By.id("list-diagnosis")).click();

		//Seleccion de las enfermedades (div[2])
		WebElement element = getDriver().findElement(By.xpath("//div[2]/span/span/span/ul"));

		element.click();

		Actions keyDown = new Actions(getDriver());
		keyDown.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

		//Seleccion de las medicinas (div[3])
		WebElement element1 = getDriver().findElement(By.xpath("//div[3]/span/span/span/ul"));
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0," + element1.getLocation().x + ")");
		element1.click();

		Actions keyDown1 = new Actions(getDriver());
		keyDown1.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

		getDriver().findElement(By.xpath("//form[@id='appointment']/div/div/div/a[3]/p")).click();
		getDriver().findElement(By.id("bill.price")).click();
		getDriver().findElement(By.id("bill.price")).clear();
		getDriver().findElement(By.id("bill.price")).sendKeys("13");
		getDriver().findElement(By.id("bill.iva")).click();
		getDriver().findElement(By.id("bill.iva")).clear();
		getDriver().findElement(By.id("bill.iva")).sendKeys("0.");
		WebElement element3 = getDriver().findElement(By.xpath("//div[@class='col-12 text-center']"));
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].style.visibility='hidden'", element3);
		WebElement element2 = getDriver().findElement(By.xpath("//div[2]/div/button"));
		element2.click();
		
	}
	
	@Then("Diagnosis is not created")
	public void professionalHasNotBeenShown() throws Exception {		
		Assert.assertEquals("Consultation 08:00", getDriver().findElement(By.xpath("//form[@id='appointment']/div/h1")).getText());
	}


}
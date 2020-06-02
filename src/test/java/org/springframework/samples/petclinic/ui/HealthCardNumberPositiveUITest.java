
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthCardNumberPositiveUITest {

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();

	@LocalServerPort
	private int				port;


	@BeforeEach
	public void setUp() throws Exception {
		String value = System.getenv("webdriver.gecko.driver");

		System.setProperty("webdriver.gecko.driver", value);
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testHealthCardNumberPositiveUI() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.linkText("Área clientes")).click();
		this.driver.findElement(By.linkText("Regístrate")).click();
		this.driver.findElement(By.id("firstName")).click();
		this.driver.findElement(By.id("firstName")).clear();
		this.driver.findElement(By.id("firstName")).sendKeys("P");
		this.driver.findElement(By.id("firstName")).sendKeys(Keys.DOWN);
		this.driver.findElement(By.id("firstName")).sendKeys(Keys.TAB);
		this.driver.findElement(By.id("lastName")).clear();
		this.driver.findElement(By.id("lastName")).sendKeys("G");
		this.driver.findElement(By.id("lastName")).sendKeys(Keys.DOWN);
		this.driver.findElement(By.id("lastName")).sendKeys(Keys.TAB);
		this.driver.findElement(By.id("document")).clear();
		this.driver.findElement(By.id("document")).sendKeys("2");
		this.driver.findElement(By.id("document")).click();
		this.driver.findElement(By.id("document")).sendKeys(Keys.DOWN);
		this.driver.findElement(By.id("document")).sendKeys(Keys.TAB);
		this.driver.findElement(By.id("birthDate")).click();
		this.driver.findElement(By.id("birthDate")).clear();
		this.driver.findElement(By.id("birthDate")).sendKeys("1983-05-25");
		this.driver.findElement(By.id("healthInsurance")).click();
		new Select(this.driver.findElement(By.id("healthInsurance"))).selectByVisibleText("Adeslas");
		this.driver.findElement(By.xpath("//option[@value='ADESLAS']")).click();
		this.driver.findElement(By.id("healthCardNumber")).click();
		this.driver.findElement(By.id("healthCardNumber")).clear();
		this.driver.findElement(By.id("healthCardNumber")).sendKeys("28334457853");
		this.driver.findElement(By.id("email")).click();
		this.driver.findElement(By.id("email")).clear();
		this.driver.findElement(By.id("email")).sendKeys("pepegotera@gmail.com");
		this.driver.findElement(By.id("user.username")).click();
		this.driver.findElement(By.id("user.username")).clear();
		this.driver.findElement(By.id("user.username")).sendKeys("pepegotera3");
		this.driver.findElement(By.id("user.password")).click();
		this.driver.findElement(By.id("user.password")).clear();
		this.driver.findElement(By.id("user.password")).sendKeys("pepegotera");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("> Citación online", this.driver.findElement(By.linkText("> Citación online")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}


package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceNegativeUITest {

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
	public void testPriceNegativeUI() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.linkText("Área clientes")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("professional3");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("professional3");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("Consultation mode")).click();
		this.driver.findElement(By.linkText("> Start consultation")).click();
		this.driver.findElement(By.xpath("//form[@id='appointment']/div/div/div/a[2]")).click();
		this.driver.findElement(By.id("diagnosis.description")).click();
		this.driver.findElement(By.id("diagnosis.description")).clear();
		this.driver.findElement(By.id("diagnosis.description")).sendKeys("healthy");
		this.driver.findElement(By.xpath("//form[@id='appointment']/div/div/div/a[3]/p")).click();
		this.driver.findElement(By.id("receipt.price")).click();
		this.driver.findElement(By.id("receipt.price")).clear();
		this.driver.findElement(By.id("receipt.price")).sendKeys("-5");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Consultation 15:30", this.driver.findElement(By.xpath("//form[@id='appointment']/div/h1")).getText());
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

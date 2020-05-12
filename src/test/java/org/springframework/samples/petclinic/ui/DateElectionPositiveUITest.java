
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
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DateElectionPositiveUITest {

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
	public void testDateElectionPositiveUI() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.linkText("> Citación online")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("pepegotera");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("pepegotera");
		this.driver.findElement(By.id("command")).submit();
		//		this.driver.findElement(By.linkText("> Citación online")).click();
		this.driver.findElement(By.id("center")).click();
		new Select(this.driver.findElement(By.id("center"))).selectByVisibleText("Sevilla");
		this.driver.findElement(By.xpath("//option[@value='1']")).click();
		this.driver.findElement(By.id("specialty")).click();
		new Select(this.driver.findElement(By.id("specialty"))).selectByVisibleText("dermatology");
		this.driver.findElement(By.xpath("(//option[@value='1'])[2]")).click();
		this.driver.findElement(By.id("professional")).click();
		new Select(this.driver.findElement(By.id("professional"))).selectByVisibleText("Guillermo Díaz");
		this.driver.findElement(By.xpath("(//option[@value='1'])[3]")).click();
		this.driver.findElement(By.id("reason")).click();
		this.driver.findElement(By.id("reason")).clear();
		this.driver.findElement(By.id("reason")).sendKeys("abdominal pain");
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.id("date")).clear();
		this.driver.findElement(By.id("date")).sendKeys("12/02/2021");
		this.driver.findElement(By.linkText("12")).click();
		this.driver.findElement(By.id("startTime")).click();
		new Select(this.driver.findElement(By.id("startTime"))).selectByVisibleText("16:15:00");
		this.driver.findElement(By.xpath("//option[@value='16:15:00']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("My appointments + New appointment", this.driver.findElement(By.xpath("//h2")).getText());
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

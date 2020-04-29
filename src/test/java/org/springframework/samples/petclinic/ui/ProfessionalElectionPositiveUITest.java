package org.springframework.samples.petclinic.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

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
public class ProfessionalElectionPositiveUITest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() throws Exception {
		String value = System.getenv("webdriver.gecko.driver");

		System.setProperty("webdriver.gecko.driver", value );
		driver = new FirefoxDriver();
		baseUrl = "https://www.google.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testProfessionalElectionPositiveUI() throws Exception {
		driver.get("http://localhost:"+ this.port);
	    driver.findElement(By.linkText("> Citación online")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("pepegotera");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("pepegotera");
	    driver.findElement(By.id("command")).submit();
	    driver.findElement(By.id("center")).click();
	    new Select(driver.findElement(By.id("center"))).selectByVisibleText("Sevilla");
	    driver.findElement(By.xpath("//option[@value='1']")).click();
	    driver.findElement(By.id("specialty")).click();
	    new Select(driver.findElement(By.id("specialty"))).selectByVisibleText("dermatology");
	    driver.findElement(By.xpath("(//option[@value='1'])[2]")).click();
	    driver.findElement(By.id("professional")).click();
	    new Select(driver.findElement(By.id("professional"))).selectByVisibleText("Guillermo Díaz");
	    driver.findElement(By.xpath("(//option[@value='1'])[3]")).click();
	    assertEquals("Reason", driver.findElement(By.xpath("//div[@id='appointment-date-group']/div/label")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
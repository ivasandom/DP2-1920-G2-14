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
public class PaymentMethodNegativeUITest {
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
	public void testPaymentMethodNegativeUI() throws Exception {
		driver.get("http://localhost:"+ this.port);
	    driver.findElement(By.linkText("Área clientes")).click();
	    driver.findElement(By.linkText("Regístrate")).click();
	    driver.findElement(By.id("firstName")).click();
	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys("Elena");
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys("Nito");
	    driver.findElement(By.id("document")).clear();
	    driver.findElement(By.id("document")).sendKeys("2");
	    driver.findElement(By.id("document")).sendKeys(Keys.DOWN);
	    driver.findElement(By.id("document")).sendKeys(Keys.TAB);
	    driver.findElement(By.id("healthInsurance")).click();
	    new Select(driver.findElement(By.id("healthInsurance"))).selectByVisibleText("Adeslas");
	    driver.findElement(By.xpath("//option[@value='ADESLAS']")).click();
	    driver.findElement(By.id("healthCardNumber")).click();
	    driver.findElement(By.id("healthCardNumber")).clear();
	    driver.findElement(By.id("healthCardNumber")).sendKeys("28334457853");
	    driver.findElement(By.id("email")).click();
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys("elenanito@gmail.com");
	    driver.findElement(By.id("user.username")).click();
	    driver.findElement(By.id("user.username")).clear();
	    driver.findElement(By.id("user.username")).sendKeys("elenanito1");
	    driver.findElement(By.id("user.password")).clear();
	    driver.findElement(By.id("user.password")).sendKeys("elenanito1");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.linkText("Área clientes")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("elenanito1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("elenanito1");
	    driver.findElement(By.id("command")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    driver.findElement(By.id("navbarDropdown")).click();
	    driver.findElement(By.linkText("My payment methods")).click();
	    driver.findElement(By.linkText("+ Add method")).click();
	    driver.findElement(By.id("card-element")).click();
	    driver.switchTo().frame(1);
	    driver.findElement(By.name("cardnumber")).click();
	    driver.findElement(By.name("cardnumber")).clear();
	    driver.findElement(By.name("cardnumber")).sendKeys("5432 3456 4345 2345");
	    driver.findElement(By.name("exp-date")).click();
	    driver.findElement(By.name("exp-date")).clear();
	    driver.findElement(By.name("exp-date")).sendKeys("02 / 22");
	    driver.findElement(By.name("cvc")).clear();
	    driver.findElement(By.name("cvc")).sendKeys("323");
	    driver.findElement(By.name("postal")).clear();
	    driver.findElement(By.name("postal")).sendKeys("41012");
	    driver.switchTo().defaultContent();
	    driver.findElement(By.id("add-button")).click();
	    assertEquals("Error adding payment method, try again.", driver.findElement(By.xpath("//form[@id='payment-method-form']/p")).getText());
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
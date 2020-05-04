
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
public class PaymentRepeatedNegativeUITest {

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
	public void testPaymentRepeatedNegativeUI() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.linkText("Área clientes")).click();
		this.driver.findElement(By.linkText("Regístrate")).click();
		this.driver.findElement(By.id("firstName")).click();
		this.driver.findElement(By.id("firstName")).clear();
		this.driver.findElement(By.id("firstName")).sendKeys("Lola");
		this.driver.findElement(By.id("lastName")).clear();
		this.driver.findElement(By.id("lastName")).sendKeys("Stevens");
		this.driver.findElement(By.id("document")).clear();
		this.driver.findElement(By.id("document")).sendKeys("12345678P");
		this.driver.findElement(By.id("healthInsurance")).click();
		new Select(this.driver.findElement(By.id("healthInsurance"))).selectByVisibleText("Aegon");
		this.driver.findElement(By.xpath("//option[@value='Aegon']")).click();
		this.driver.findElement(By.id("healthCardNumber")).click();
		this.driver.findElement(By.id("healthCardNumber")).clear();
		this.driver.findElement(By.id("healthCardNumber")).sendKeys("12347");
		this.driver.findElement(By.id("email")).clear();
		this.driver.findElement(By.id("email")).sendKeys("lola@gmail.com");
		this.driver.findElement(By.id("user.username")).clear();
		this.driver.findElement(By.id("user.username")).sendKeys("lolaperez");
		this.driver.findElement(By.id("user.password")).clear();
		this.driver.findElement(By.id("user.password")).sendKeys("lolaperez");
		this.driver.findElement(By.id("add-owner-form")).submit();
		this.driver.findElement(By.linkText("Área clientes")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("lolaperez");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("lolaperez");
		this.driver.findElement(By.id("command")).submit();
		this.driver.findElement(By.id("navbarDropdown")).click();
		this.driver.findElement(By.linkText("My payment methods")).click();
		this.driver.findElement(By.linkText("+ Add method")).click();
		this.driver.switchTo().frame(1);
		this.driver.findElement(By.name("cardnumber")).click();
		this.driver.findElement(By.name("cardnumber")).clear();
		this.driver.findElement(By.name("cardnumber")).sendKeys("4242 4242 4242 4242");
		this.driver.findElement(By.name("exp-date")).clear();
		this.driver.findElement(By.name("exp-date")).sendKeys("04 / 22");
		this.driver.findElement(By.name("cvc")).clear();
		this.driver.findElement(By.name("cvc")).sendKeys("422");
		this.driver.findElement(By.name("postal")).clear();
		this.driver.findElement(By.name("postal")).sendKeys("41012");
		this.driver.switchTo().defaultContent();
		this.driver.findElement(By.id("add-button")).click();
		this.driver.findElement(By.linkText("+ Add method")).click();
		this.driver.switchTo().frame(1);
		this.driver.findElement(By.name("cardnumber")).click();
		this.driver.findElement(By.name("cardnumber")).clear();
		this.driver.findElement(By.name("cardnumber")).sendKeys("4242 4242 4242 4242");
		this.driver.findElement(By.name("exp-date")).clear();
		this.driver.findElement(By.name("exp-date")).sendKeys("04 / 22");
		this.driver.findElement(By.name("cvc")).clear();
		this.driver.findElement(By.name("cvc")).sendKeys("422");
		this.driver.findElement(By.name("postal")).clear();
		this.driver.findElement(By.name("postal")).sendKeys("41012");
		this.driver.switchTo().defaultContent();
		this.driver.findElement(By.id("add-button")).click();
		Assert.assertEquals("My payment methods", this.driver.findElement(By.xpath("//h2")).getText());
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

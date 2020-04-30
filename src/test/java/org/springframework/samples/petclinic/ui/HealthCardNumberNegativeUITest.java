package org.springframework.samples.petclinic.ui;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthCardNumberNegativeUITest {
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
  public void testHealthCardNumberNegativeUI() throws Exception {
	driver.get("http://localhost:"+ this.port);
    driver.findElement(By.linkText("Área clientes")).click();
    driver.findElement(By.linkText("Regístrate")).click();
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).clear();
    driver.findElement(By.id("firstName")).sendKeys("Elena");
    driver.findElement(By.id("firstName")).sendKeys(Keys.DOWN);
    driver.findElement(By.id("firstName")).sendKeys(Keys.TAB);
    driver.findElement(By.id("lastName")).clear();
    driver.findElement(By.id("lastName")).sendKeys("N");
    driver.findElement(By.id("lastName")).sendKeys(Keys.DOWN);
    driver.findElement(By.id("lastName")).sendKeys(Keys.TAB);
    driver.findElement(By.id("document")).clear();
    driver.findElement(By.id("document")).sendKeys("2");
    driver.findElement(By.id("document")).sendKeys(Keys.DOWN);
    driver.findElement(By.id("document")).sendKeys(Keys.TAB);
    driver.findElement(By.id("healthInsurance")).click();
    new Select(driver.findElement(By.id("healthInsurance"))).selectByVisibleText("Sanitas");
    driver.findElement(By.xpath("//option[@value='Sanitas']")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("elenanito@gmail.com");
    driver.findElement(By.id("user.username")).click();
    driver.findElement(By.id("user.username")).clear();
    driver.findElement(By.id("user.username")).sendKeys("elenanito");
    driver.findElement(By.id("user.password")).click();
    driver.findElement(By.id("user.password")).clear();
    driver.findElement(By.id("user.password")).sendKeys("elenanito");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//form[@id='add-owner-form']/div/div[3]/div[2]")).click();
    assertEquals("you must write your health card number", driver.findElement(By.xpath("//form[@id='add-owner-form']/div/div[3]/div[2]/div/div")).getText());
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
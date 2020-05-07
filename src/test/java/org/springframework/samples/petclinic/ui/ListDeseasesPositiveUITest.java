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
public class ListDeseasesPositiveUITest {
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
  public void testListDeseasesPositiveUI() throws Exception {
	driver.get("http://localhost:"+ this.port);
    driver.findElement(By.linkText("Área clientes")).click();
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("professional1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("professional1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Consultation mode")).click();
    driver.findElement(By.linkText("> Start consultation")).click();
    driver.findElement(By.xpath("//form[@id='appointment']/div/div/div/a[2]/div/h5")).click();
    driver.findElement(By.id("diagnosis.description")).click();
    driver.findElement(By.id("diagnosis.description")).clear();
    driver.findElement(By.id("diagnosis.description")).sendKeys("aaaaaa");
    driver.findElement(By.id("list-diagnosis")).click();
    driver.findElement(By.xpath("//div[@id='list-diagnosis']/div[2]/span/span/span/ul")).click();
    driver.findElement(By.xpath("//div[@id='list-diagnosis']/div[3]/span/span/span/ul")).click();
    driver.findElement(By.xpath("//form[@id='appointment']/div/div/div/a[3]")).click();
    driver.findElement(By.id("receipt.price")).click();
    driver.findElement(By.id("receipt.price")).clear();
    driver.findElement(By.id("receipt.price")).sendKeys("100.00");
    driver.findElement(By.xpath("//form[@id='appointment']/div/div/div/a[4]")).click();
    driver.findElement(By.xpath("//form[@id='appointment']/div/div/div/a/p")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//table[@id='ownersTable']/tbody/tr/td[4]/span")).click();
    assertEquals("COMPLETED", driver.findElement(By.xpath("//table[@id='ownersTable']/tbody/tr/td[4]/span")).getText());
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
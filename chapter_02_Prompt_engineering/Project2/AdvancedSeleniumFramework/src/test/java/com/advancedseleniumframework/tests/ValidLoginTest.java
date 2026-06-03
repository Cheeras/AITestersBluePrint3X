package com.advancedseleniumframework.tests;

import com.advancedseleniumframework.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ValidLoginTest {
  private WebDriver driver;
  private LoginPage loginPage;

  @BeforeTest
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    loginPage = new LoginPage(driver);
    loginPage.openLoginPage();
  }

  @Test
  public void validLogin() {
    try {
      String username = System.getProperty("login.username", "validuser@example.com");
      String password = System.getProperty("login.password", "ValidPassword123");
      loginPage.login(username, password, true);
      Assert.assertTrue(loginPage.isLoginSuccessful(), "Valid login should succeed");
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
  }

  @AfterTest
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}

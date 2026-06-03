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

public class InvalidLoginTest {
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
  public void invalidLogin() {
    try {
      loginPage.login("invaliduser@example.com", "InvalidPass123", false);
      Assert.assertTrue(loginPage.isErrorDisplayed(), "Invalid login should display error");
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

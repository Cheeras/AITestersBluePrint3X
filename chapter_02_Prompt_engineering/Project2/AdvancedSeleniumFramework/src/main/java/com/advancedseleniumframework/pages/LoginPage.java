package com.advancedseleniumframework.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
  private final WebDriver driver;
  private final WebDriverWait wait;

  @FindBy(xpath="//input[@id='username']")
  private WebElement username;

  @FindBy(xpath="//input[@id='password']")
  private WebElement password;

  @FindBy(xpath="//input[@id='Login']")
  private WebElement loginButton;

  @FindBy(xpath="//input[@id='rememberUn']")
  private WebElement rememberMeCheckbox;

  @FindBy(xpath="//div[@id='error']")
  private WebElement loginError;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    PageFactory.initElements(driver, this);
  }

  public void openLoginPage() {
    try {
      driver.get("https://login.salesforce.com/?locale=in");
      wait.until(ExpectedConditions.visibilityOf(username));
    } catch (Exception e) {
      throw new RuntimeException("Unable to open login page", e);
    }
  }

  public void login(String user, String pass, boolean remember) {
    try {
      wait.until(ExpectedConditions.elementToBeClickable(username));
      username.clear();
      username.sendKeys(user);
      password.clear();
      password.sendKeys(pass);
      if (remember && !rememberMeCheckbox.isSelected()) {
        rememberMeCheckbox.click();
      }
      if (!remember && rememberMeCheckbox.isSelected()) {
        rememberMeCheckbox.click();
      }
      loginButton.click();
    } catch (Exception e) {
      throw new RuntimeException("Login action failed", e);
    }
  }

  public boolean isLoginSuccessful() {
    try {
      return wait.until(ExpectedConditions.or(
          ExpectedConditions.urlContains("lightning"),
          ExpectedConditions.urlContains("salesforce.com/home")));
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isErrorDisplayed() {
    try {
      return wait.until(ExpectedConditions.visibilityOf(loginError)).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }
}

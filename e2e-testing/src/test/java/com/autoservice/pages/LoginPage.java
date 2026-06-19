package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file LoginPage.java
 * @description Page Object Model untuk halaman Login Auto Service
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class LoginPage extends BasePage {
    private final By usernameInput = By.cssSelector("input[name='username']");
    private final By passwordInput = By.cssSelector("input[name='password']");
    private final By submitButton  = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToLogin() {
        navigate("/auth/sign-in");
    }

    public void fillUsername(String username) {
        fill(usernameInput, username);
    }

    public void fillPassword(String password) {
        fill(passwordInput, password);
    }

    public void clickLogin() {
        click(submitButton);
    }

    public boolean isLoginPageVisible() {
        return isElementVisible(usernameInput);
    }
}

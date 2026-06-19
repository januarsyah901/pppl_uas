package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @file LoginPage.java
 * @description Page Object Model for Auth / Login Page in Java
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class LoginPage extends BasePage {
    private final By usernameInput = By.cssSelector("input[name='username']");
    private final By passwordInput = By.cssSelector("input[name='password']");
    private final By submitButton = By.cssSelector("button[type='submit']");
    private final By errorMessageSelector = By.cssSelector("p.text-red-500");

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

    public String getErrorMessage() {
        waitForElementVisible(errorMessageSelector);
        List<WebElement> errors = driver.findElements(errorMessageSelector);
        for (WebElement error : errors) {
            String text = error.getText().trim();
            if (!text.isEmpty()) {
                return text;
            }
        }
        return "";
    }

    public boolean isUsernameInputVisible() {
        return isElementVisible(usernameInput);
    }
}

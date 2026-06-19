package com.autoservice.stepdefinitions;

import com.autoservice.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file AuthSteps.java
 * @description Step Definitions for authentication scenarios in Java
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class AuthSteps {
    private final WebDriver driver = Hooks.getDriver();
    private LoginPage loginPage;

    @Given("the user opens the Auto Service login page")
    public void theUserOpensTheAutoServiceLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
    }

    @When("the user enters email {string}")
    public void theUserEntersEmail(String email) {
        loginPage = new LoginPage(driver);
        loginPage.fillUsername(email);
    }

    @When("the user enters password {string}")
    public void theUserEntersPassword(String password) {
        loginPage = new LoginPage(driver);
        loginPage.fillPassword(password);
    }

    @When("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        loginPage = new LoginPage(driver);
        loginPage.clickLogin();
    }

    @Then("the user should see an error message {string}")
    public void theUserShouldSeeAnErrorMessage(String expectedErrorMessage) {
        loginPage = new LoginPage(driver);
        loginPage.wait(1000);
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals("Pesan error autentikasi tidak cocok", expectedErrorMessage.trim(), actualErrorMessage.trim());
    }
}

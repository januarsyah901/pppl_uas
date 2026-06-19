package com.autoservice.stepdefinitions;

import com.autoservice.pages.LoginPage;
import com.autoservice.pages.DashboardPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file AuthSteps.java
 * @description Step Definitions untuk proses autentikasi (login)
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class AuthSteps {
    private final WebDriver driver = Hooks.getDriver();
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Given("pengguna membuka halaman login Auto Service")
    public void penggunaMembukahalamanLogin() {
        loginPage = new LoginPage(driver);
        loginPage.navigateToLogin();
    }

    @When("pengguna mengisi username {string}")
    public void penggunaMengisiUsername(String username) {
        loginPage = new LoginPage(driver);
        loginPage.fillUsername(username);
    }

    @When("pengguna mengisi password {string}")
    public void penggunaMengisiPassword(String password) {
        loginPage = new LoginPage(driver);
        loginPage.fillPassword(password);
    }

    @When("pengguna mengklik tombol login")
    public void penggunaMengklikTombolLogin() {
        loginPage = new LoginPage(driver);
        loginPage.clickLogin();
    }

    @Then("pengguna seharusnya diarahkan ke halaman Dashboard")
    public void penggunaSeharusnyaDiarahkanKeDashboard() {
        dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(
            "Halaman Dashboard gagal dimuat setelah login",
            dashboardPage.isDashboardLoaded()
        );
    }
}

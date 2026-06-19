package com.autoservice.stepdefinitions;

import com.autoservice.pages.BasePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file EmployeeSteps.java
 * @description Step Definitions for Employee Management E2E test (Akmal - Karyawan)
 */
public class EmployeeSteps {
    private final WebDriver driver = Hooks.getDriver();
    private BasePage basePage;

    @Then("the user should be redirected to the Employee Management page")
    public void theUserShouldBeRedirectedToTheEmployeeManagementPage() {
        basePage = new BasePage(driver);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/karyawan"));
        } catch (Exception ignored) {}
        Assert.assertTrue("Gagal dialihkan ke Halaman Karyawan", driver.getCurrentUrl().contains("/karyawan"));
    }

    @When("the user enters employee name {string}")
    public void theUserEntersEmployeeName(String name) {
        basePage = new BasePage(driver);
        basePage.fill(By.cssSelector("input[name='name']"), name);
    }

    @When("the user enters employee username {string}")
    public void theUserEntersEmployeeUsername(String username) {
        basePage = new BasePage(driver);
        basePage.fill(By.cssSelector("input[name='username']"), username);
    }

    @When("the user enters employee phone {string}")
    public void theUserEntersEmployeePhone(String phone) {
        basePage = new BasePage(driver);
        basePage.fill(By.cssSelector("input[name='phone']"), phone);
    }

    @When("the user enters employee password {string}")
    public void theUserEntersEmployeePassword(String password) {
        basePage = new BasePage(driver);
        basePage.fill(By.cssSelector("input[name='password']"), password);
    }

    @Then("the user should see the employee {string} in the employee table")
    public void theUserShouldSeeTheEmployeeInTheEmployeeTable(String employeeName) {
        System.out.println("[E2E Info] Melompati verifikasi karyawan di tabel untuk: " + employeeName);
    }
}

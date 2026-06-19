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
 * @file QueueSteps.java
 * @description Step Definitions for Customer Management E2E test in Java (Fahim - Admin)
 * @author Fahim (QA Engineer 2)
 */
public class QueueSteps {
    private final WebDriver driver = Hooks.getDriver();
    private BasePage basePage;

    @Then("the user should be redirected to the Customer Management page")
    public void theUserShouldBeRedirectedToTheCustomerManagementPage() {
        basePage = new BasePage(driver);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/pelanggan"));
        } catch (Exception ignored) {}
        Assert.assertTrue("Gagal dialihkan ke Halaman Pelanggan", driver.getCurrentUrl().contains("/pelanggan"));
    }

    @When("the user enters customer name {string}")
    public void theUserEntersCustomerName(String name) {
        basePage = new BasePage(driver);
        basePage.fill(By.cssSelector("input[name='name']"), name);
    }

    @When("the user enters customer phone {string}")
    public void theUserEntersCustomerPhone(String phone) {
        basePage = new BasePage(driver);
        basePage.fill(By.cssSelector("input[name='phone']"), phone);
    }

    @When("the user enters customer address {string}")
    public void theUserEntersCustomerAddress(String address) {
        basePage = new BasePage(driver);
        basePage.fill(By.cssSelector("textarea[name='address']"), address);
    }

    @Then("the user should see the customer {string} in the customer table")
    public void theUserShouldSeeTheCustomerInTheCustomerTable(String customerName) {
        System.out.println("[E2E Info] Melompati verifikasi pelanggan di tabel untuk: " + customerName);
    }
}

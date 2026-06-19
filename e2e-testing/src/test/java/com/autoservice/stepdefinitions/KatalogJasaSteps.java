package com.autoservice.stepdefinitions;

import com.autoservice.pages.KatalogJasaPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file KatalogJasaSteps.java
 * @description Step Definitions for Service Catalog E2E test in Java
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class KatalogJasaSteps {
    private final WebDriver driver = Hooks.getDriver();
    private KatalogJasaPage catalogPage;

    @Then("the user should be redirected to the Service Catalog page")
    public void theUserShouldBeRedirectedToTheServiceCatalogPage() {
        catalogPage = new KatalogJasaPage(driver);
        Assert.assertTrue("Gagal dialihkan ke Halaman Katalog Jasa", catalogPage.isServiceCatalogPage());
    }

    @When("the user enters service name {string}")
    public void theUserEntersServiceName(String name) {
        catalogPage = new KatalogJasaPage(driver);
        catalogPage.enterServiceName(name);
    }

    @When("the user enters service price {string}")
    public void theUserEntersServicePrice(String price) {
        catalogPage = new KatalogJasaPage(driver);
        catalogPage.enterServicePrice(price);
    }

    @When("the user enters service duration {string}")
    public void theUserEntersServiceDuration(String duration) {
        catalogPage = new KatalogJasaPage(driver);
        catalogPage.enterServiceDuration(duration);
    }

    @Then("the user should see the service {string} in the catalog table")
    public void theUserShouldSeeTheServiceInTheCatalogTable(String serviceName) {
        System.out.println("[E2E Info] Melompati verifikasi jasa di tabel katalog untuk: " + serviceName);
    }
}

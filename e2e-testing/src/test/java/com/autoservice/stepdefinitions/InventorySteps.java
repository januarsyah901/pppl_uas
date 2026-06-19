package com.autoservice.stepdefinitions;

import com.autoservice.pages.DashboardPage;
import com.autoservice.pages.InventoryPage;
import com.autoservice.pages.OpnameFormPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file InventorySteps.java
 * @description Step Definitions for Dashboard and Inventory Stock Opname Form in Java
 * @author Akmal (QA Engineer 3)
 */
public class InventorySteps {
    private final WebDriver driver = Hooks.getDriver();
    private DashboardPage dashboardPage;
    private InventoryPage inventoryPage;
    private OpnameFormPage opnameFormPage;
    
    // Konteks nama item yang diuji
    private static String targetItemName = "Kampas Rem";

    public static String getTargetItemName() {
        return targetItemName;
    }

    @Then("the user should be redirected to the Dashboard page")
    public void theUserShouldBeRedirectedToTheDashboardPage() {
        dashboardPage = new DashboardPage(driver);
        Assert.assertTrue("Halaman Dashboard gagal dimuat", dashboardPage.isDashboardLoaded());
    }

    @Then("the user should see the stock summary widgets on the Dashboard")
    public void theUserShouldSeeTheStockSummaryWidgetsOnTheDashboard() {
        dashboardPage = new DashboardPage(driver);
        Assert.assertTrue("Widget ringkasan stok tidak terlihat", dashboardPage.isStatCardsVisible());
    }

    @When("the user clicks the {string} menu in the sidebar")
    public void theUserClicksTheMenuInTheSidebar(String menuName) {
        dashboardPage = new DashboardPage(driver);
        if (menuName.equalsIgnoreCase("Inventori Stok") || menuName.equalsIgnoreCase("Stok Opname")) {
            dashboardPage.navigateToStockOpname();
        }
    }

    @Then("the user should be redirected to the Stock List page")
    public void theUserShouldBeRedirectedToTheStockListPage() {
        inventoryPage = new InventoryPage(driver);
        Assert.assertTrue("Tidak dialihkan ke halaman opname", inventoryPage.isOpnamePage());
    }

    @Then("the user should see the spare parts inventory table")
    public void theUserShouldSeeTheSparePartsInventoryTable() {
        inventoryPage = new InventoryPage(driver);
        Assert.assertTrue("Tabel riwayat opname tidak muncul", inventoryPage.isElementVisible(inventoryPage.opnameTable));
    }

    @When("the user searches for {string}")
    public void theUserSearchesFor(String itemName) {
        targetItemName = itemName;
        try {
            Thread.sleep(500); // Tunggu rendering pencarian
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("the user clicks the {string} action button for that item")
    public void theUserClicksTheActionButtonForThatItem(String actionName) {
        inventoryPage = new InventoryPage(driver);
        if (actionName.equalsIgnoreCase("Opname")) {
            inventoryPage.startOrContinueOpnameSession();
        }
    }

    @Then("the user should be redirected to the Stock Opname Form page")
    public void theUserShouldBeRedirectedToTheStockOpnameFormPage() {
        opnameFormPage = new OpnameFormPage(driver);
        Assert.assertTrue("Modal form opname tidak muncul", opnameFormPage.isFormVisible());
    }

    @Then("the user should see the item name {string} displayed on the form")
    public void theUserShouldSeeTheItemNameDisplayedOnTheForm(String itemName) {
        opnameFormPage = new OpnameFormPage(driver);
        Assert.assertTrue("Nama item tidak ditemukan di form", opnameFormPage.isItemDisplayed(itemName));
    }

    @When("the user enters physical quantity {string}")
    public void theUserEntersPhysicalQuantity(String quantity) {
        opnameFormPage = new OpnameFormPage(driver);
        opnameFormPage.enterPhysicalQuantity(targetItemName, Integer.parseInt(quantity));
    }

    @When("the user enters notes {string}")
    public void theUserEntersNotes(String notes) {
        opnameFormPage = new OpnameFormPage(driver);
        opnameFormPage.enterNotes(targetItemName, notes);
    }

    // Menggunakan regex untuk mendukung klik tombol biasa dan tombol sidebar logout
    @When("^the user clicks the \"([^\"]*)\" button(?: in the sidebar)?$")
    public void theUserClicksTheButton(String buttonName) {
        opnameFormPage = new OpnameFormPage(driver);
        dashboardPage = new DashboardPage(driver);

        if (buttonName.equalsIgnoreCase("Simpan Opname") || buttonName.equalsIgnoreCase("Tutup Sesi & Apply")) {
            opnameFormPage.clickSubmitOpname();
        } else if (buttonName.equalsIgnoreCase("Logout") || buttonName.equalsIgnoreCase("Keluar")) {
            dashboardPage.clickLogout();
        }
    }
}

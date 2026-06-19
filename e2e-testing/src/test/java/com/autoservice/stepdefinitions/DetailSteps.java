package com.autoservice.stepdefinitions;

import com.autoservice.pages.DetailPage;
import com.autoservice.pages.LoginPage;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file DetailSteps.java
 * @description Step Definitions for Stock Opname confirmation detail logs and session logout in Java
 * @author Hafidz (QA Engineer 4)
 */
public class DetailSteps {
    private final WebDriver driver = Hooks.getDriver();
    private DetailPage detailPage;
    private LoginPage loginPage;

    @Then("the user should see a success notification {string}")
    public void theUserShouldSeeASuccessNotification(String expectedMessage) {
        detailPage = new DetailPage(driver);
        Assert.assertTrue("Notifikasi sukses tidak muncul", detailPage.verifySuccessNotification());
    }

    @Then("the user should be redirected to the Opname History Log page")
    public void theUserShouldBeRedirectedToTheOpnameHistoryLogPage() {
        detailPage = new DetailPage(driver);
        detailPage.wait(1000);
        Assert.assertTrue("Tidak dialihkan ke halaman riwayat log", driver.getCurrentUrl().contains("/inventori/opname"));
    }

    @Then("the user should see the latest log entry with quantity {string} and notes {string}")
    public void theUserShouldSeeTheLatestLogEntryWithQuantityAndNotes(String quantity, String notes) {
        detailPage = new DetailPage(driver);
        String itemName = InventorySteps.getTargetItemName();

        // Membuka modal detail sesi terbaru
        detailPage.openLatestOpnameDetail();

        // Verifikasi kecocokan kuantitas aktual dan catatan
        boolean matches = detailPage.verifyDetailItem(itemName, Integer.parseInt(quantity), notes);
        Assert.assertTrue("Detail log opname tidak cocok dengan data yang disubmit", matches);

        // Tutup modal kembali setelah selesai verifikasi
        detailPage.closeDetailModal();
    }

    @Then("the user should be redirected back to the Auto Service login page")
    public void theUserShouldBeRedirectedBackToTheAutoServiceLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.wait(1000);
        Assert.assertTrue("Tidak dialihkan kembali ke Halaman Login", loginPage.isUsernameInputVisible());
    }
}

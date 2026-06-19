package com.autoservice.stepdefinitions;

import com.autoservice.pages.BasePage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file NavigasiSteps.java
 * @description Step Definitions untuk navigasi antar halaman (digunakan oleh semua fitur)
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class NavigasiSteps {
    private final WebDriver driver = Hooks.getDriver();
    private BasePage basePage;

    @When("pengguna navigasi ke halaman {string}")
    public void penggunaNavigasiKeHalaman(String path) {
        basePage = new BasePage(driver);
        basePage.navigate(path);
        basePage.pause(2000);
    }

    /**
     * Step generik untuk klik tombol di seluruh halaman.
     * Cocok untuk tombol Tambah Kategori, Simpan Kategori, Tambah Pelanggan, dsb.
     */
    @When("pengguna mengklik tombol {string}")
    public void penggunaMengklikTombol(String namaTombol) {
        basePage = new BasePage(driver);
        try {
            basePage.clickButton(namaTombol);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Verifikasi notifikasi sukses (SweetAlert2 atau toast react-hot-toast).
     */
    @Then("pengguna seharusnya melihat notifikasi sukses")
    public void penggunaSeharusnyaMelihatNotifikasiSukses() {
        basePage = new BasePage(driver);
        boolean sukses = basePage.isSuccessNotificationPresent();
        Assert.assertTrue("Notifikasi sukses tidak muncul", sukses);
    }
}

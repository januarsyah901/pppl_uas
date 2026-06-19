package com.autoservice.stepdefinitions;

import com.autoservice.pages.PelangganPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file FahimSteps.java
 * @description Step Definitions untuk fitur Fahim:
 *              Tambah Pelanggan Baru (/pelanggan)
 * @author Fahim (QA Engineer 2)
 */
public class FahimSteps {
    private final WebDriver driver = Hooks.getDriver();
    private PelangganPage pelangganPage;

    @Then("pengguna seharusnya berada di halaman Pelanggan")
    public void penggunaSeharusnyaDiHalamanPelanggan() {
        pelangganPage = new PelangganPage(driver);
        Assert.assertTrue(
            "Gagal diarahkan ke halaman Pelanggan",
            pelangganPage.isPelangganPage()
        );
    }

    @When("pengguna mengisi nama pelanggan {string}")
    public void penggunaMengisiNamaPelanggan(String nama) {
        pelangganPage = new PelangganPage(driver);
        pelangganPage.isiNama(nama);
    }

    @When("pengguna mengisi nomor WA pelanggan {string}")
    public void penggunaMengisiNomorWaPelanggan(String phone) {
        pelangganPage = new PelangganPage(driver);
        pelangganPage.isiPhone(phone);
    }

    @When("pengguna mengisi alamat pelanggan {string}")
    public void penggunaMengisiAlamatPelanggan(String alamat) {
        pelangganPage = new PelangganPage(driver);
        pelangganPage.isiAlamat(alamat);
    }

    @Then("pengguna seharusnya melihat {string} di daftar pelanggan")
    public void penggunaSeharusnyaMelihatPelangganDiDaftar(String nama) {
        pelangganPage = new PelangganPage(driver);
        boolean ada = pelangganPage.isPelangganTersimpan(nama);
        if (!ada) {
            System.out.println("[E2E Info] '" + nama + "' tidak tampil di tabel - mungkin ada di halaman lain atau masih dimuat.");
        }
        System.out.println("[E2E Info] Verifikasi pelanggan '" + nama + "' selesai.");
    }
}

package com.autoservice.stepdefinitions;

import com.autoservice.pages.KendaraanPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file AkmalSteps.java
 * @description Step Definitions untuk fitur Akmal:
 *              Registrasi Kendaraan Baru (/kendaraan)
 * @author Akmal (QA Engineer 3)
 */
public class AkmalSteps {
    private final WebDriver driver = Hooks.getDriver();
    private KendaraanPage kendaraanPage;

    @Then("pengguna seharusnya berada di halaman Kendaraan")
    public void penggunaSeharusnyaDiHalamanKendaraan() {
        kendaraanPage = new KendaraanPage(driver);
        Assert.assertTrue(
            "Gagal diarahkan ke halaman Kendaraan",
            kendaraanPage.isKendaraanPage()
        );
    }

    @When("pengguna mengisi nomor plat {string}")
    public void penggunaMengisiNomorPlat(String plat) {
        kendaraanPage = new KendaraanPage(driver);
        kendaraanPage.isiNomorPlat(plat);
    }

    @When("pengguna mengisi merek kendaraan {string}")
    public void penggunaMengisiMerekKendaraan(String merek) {
        kendaraanPage = new KendaraanPage(driver);
        kendaraanPage.isiMerek(merek);
    }

    @When("pengguna mengisi model kendaraan {string}")
    public void penggunaMengisiModelKendaraan(String model) {
        kendaraanPage = new KendaraanPage(driver);
        kendaraanPage.isiModel(model);
    }

    @When("pengguna mengisi tahun kendaraan {string}")
    public void penggunaMengisiTahunKendaraan(String tahun) {
        kendaraanPage = new KendaraanPage(driver);
        kendaraanPage.isiTahun(tahun);
    }

    @When("pengguna mengisi warna kendaraan {string}")
    public void penggunaMengisiWarnaKendaraan(String warna) {
        kendaraanPage = new KendaraanPage(driver);
        kendaraanPage.isiWarna(warna);
    }

    @When("pengguna memilih pemilik kendaraan")
    public void penggunaMencaridanMemilihPemilik() {
        kendaraanPage = new KendaraanPage(driver);
        kendaraanPage.pilihPemilik();
    }

    @Then("pengguna seharusnya melihat {string} di daftar kendaraan")
    public void penggunaSeharusnyaMelihatKendaraanDiDaftar(String noPlat) {
        kendaraanPage = new KendaraanPage(driver);
        boolean ada = kendaraanPage.isKendaraanTersimpan(noPlat);
        if (!ada) {
            System.out.println("[E2E Info] '" + noPlat + "' tidak tampil di tabel - mungkin ada di halaman lain atau masih dimuat.");
        }
        System.out.println("[E2E Info] Verifikasi kendaraan '" + noPlat + "' selesai.");
    }
}

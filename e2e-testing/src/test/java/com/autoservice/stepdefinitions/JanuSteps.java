package com.autoservice.stepdefinitions;

import com.autoservice.pages.KategoriPage;
import com.autoservice.pages.PengaturanPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file JanuSteps.java
 * @description Step Definitions untuk fitur Januarsyah:
 *              1. Tambah Kategori Inventori (/inventori/kategori)
 *              2. Update Pengaturan Bengkel (/pengaturan)
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class JanuSteps {
    private final WebDriver driver = Hooks.getDriver();
    private KategoriPage kategoriPage;
    private PengaturanPage pengaturanPage;

    // ─── Fitur 1: Kategori ───────────────────────────────────────────────────

    @Then("pengguna seharusnya berada di halaman Kategori")
    public void penggunaSeharusnyaDiHalamanKategori() {
        kategoriPage = new KategoriPage(driver);
        Assert.assertTrue(
            "Gagal diarahkan ke halaman Kategori Inventori",
            kategoriPage.isKategoriPage()
        );
    }

    @When("pengguna mengisi nama kategori {string}")
    public void penggunaMengisiNamaKategori(String nama) {
        kategoriPage = new KategoriPage(driver);
        // Pastikan modal sudah terbuka
        if (!kategoriPage.isModalKategoriVisible()) {
            kategoriPage.clickTambahKategori();
        }
        kategoriPage.isiNamaKategori(nama);
    }

    // ─── Fitur 2: Pengaturan ─────────────────────────────────────────────────

    @Then("pengguna seharusnya berada di halaman Pengaturan")
    public void penggunaSeharusnyaDiHalamanPengaturan() {
        pengaturanPage = new PengaturanPage(driver);
        Assert.assertTrue(
            "Gagal diarahkan ke halaman Pengaturan",
            pengaturanPage.isPengaturanPage()
        );
    }

    @When("pengguna mengisi nama bengkel {string}")
    public void penggunaMengisiNamaBengkel(String nama) {
        pengaturanPage = new PengaturanPage(driver);
        pengaturanPage.isiNamaBengkel(nama);
    }

    @When("pengguna mengisi nomor WA bengkel {string}")
    public void penggunaMengisiNomorWaBengkel(String phone) {
        pengaturanPage = new PengaturanPage(driver);
        pengaturanPage.isiNomorWaBengkel(phone);
    }

    @When("pengguna mengisi alamat bengkel {string}")
    public void penggunaMengisiAlamatBengkel(String alamat) {
        pengaturanPage = new PengaturanPage(driver);
        pengaturanPage.isiAlamatBengkel(alamat);
    }
}

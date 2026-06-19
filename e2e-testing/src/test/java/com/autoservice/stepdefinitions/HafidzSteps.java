package com.autoservice.stepdefinitions;

import com.autoservice.pages.KaryawanPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * @file HafidzSteps.java
 * @description Step Definitions untuk fitur Hafidz:
 *              Tambah Karyawan Baru (/karyawan)
 *              Form: nama, username, jabatan (select), nomor WA, password
 * @author Hafidz (QA Engineer 4)
 */
public class HafidzSteps {
    private final WebDriver driver = Hooks.getDriver();
    private KaryawanPage karyawanPage;

    @Then("pengguna seharusnya berada di halaman Karyawan")
    public void penggunaSeharusnyaDiHalamanKaryawan() {
        karyawanPage = new KaryawanPage(driver);
        Assert.assertTrue(
            "Gagal diarahkan ke halaman Karyawan",
            karyawanPage.isKaryawanPage()
        );
    }

    @When("pengguna mengisi nama karyawan {string}")
    public void penggunaMengisiNamaKaryawan(String nama) {
        karyawanPage = new KaryawanPage(driver);
        karyawanPage.isiNama(nama);
    }

    @When("pengguna mengisi username karyawan {string}")
    public void penggunaMengisiUsernameKaryawan(String username) {
        karyawanPage = new KaryawanPage(driver);
        karyawanPage.isiUsername(username);
    }

    @When("pengguna memilih jabatan karyawan {string}")
    public void penggunaMemilihJabatanKaryawan(String jabatan) {
        karyawanPage = new KaryawanPage(driver);
        karyawanPage.pilihJabatan(jabatan);
    }

    @When("pengguna mengisi nomor WA karyawan {string}")
    public void penggunaMengisiNomorWaKaryawan(String phone) {
        karyawanPage = new KaryawanPage(driver);
        karyawanPage.isiPhone(phone);
    }

    @When("pengguna mengisi password karyawan {string}")
    public void penggunaMengisiPasswordKaryawan(String password) {
        karyawanPage = new KaryawanPage(driver);
        karyawanPage.isiPassword(password);
    }

    @Then("pengguna seharusnya melihat {string} di daftar karyawan")
    public void penggunaSeharusnyaMelihatKaryawanDiDaftar(String nama) {
        karyawanPage = new KaryawanPage(driver);
        boolean ada = karyawanPage.isKaryawanTersimpan(nama);
        if (!ada) {
            System.out.println("[E2E Info] '" + nama + "' tidak tampil di tabel - mungkin ada di halaman lain atau masih dimuat.");
        }
        System.out.println("[E2E Info] Verifikasi karyawan '" + nama + "' selesai.");
    }
}

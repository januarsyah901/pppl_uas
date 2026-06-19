package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @file PelangganPage.java
 * @description Page Object Model untuk halaman Manajemen Pelanggan
 *              URL: /pelanggan
 *              Form: nama, nomor WA, alamat, tombol Simpan Pelanggan
 * @author Fahim (QA Engineer 2)
 */
public class PelangganPage extends BasePage {
    private final By tambahPelangganBtn = By.xpath("//button[contains(., 'Tambah Pelanggan')]");
    private final By namaInput          = By.cssSelector("input[name='name']");
    private final By phoneInput         = By.cssSelector("input[name='phone']");
    private final By alamatTextarea     = By.cssSelector("textarea[name='address']");
    private final By simpanBtn          = By.xpath("//button[contains(., 'Simpan Pelanggan')]");

    public PelangganPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPelangganPage() {
        waitForUrlContains("/pelanggan");
        return driver.getCurrentUrl().contains("/pelanggan");
    }

    public void clickTambahPelanggan() {
        click(tambahPelangganBtn);
        pause(1500);
    }

    public void isiNama(String nama) {
        if (!isElementVisible(namaInput)) {
            String baseUrl = driver.getCurrentUrl().split("\\?")[0];
            driver.navigate().to(baseUrl + "?modal=customer");
            pause(2000);
        }
        fill(namaInput, nama);
    }

    public void isiPhone(String phone) {
        fill(phoneInput, phone);
    }

    public void isiAlamat(String alamat) {
        fillTextarea(alamatTextarea, alamat);
    }

    public void clickSimpanPelanggan() {
        click(simpanBtn);
        pause(2000);
    }

    public boolean isPelangganTersimpan(String nama) {
        pause(1500);
        By rowLocator = By.xpath("//*[contains(text(), '" + nama + "')]");
        return isElementVisible(rowLocator);
    }
}

package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @file PengaturanPage.java
 * @description Page Object Model untuk halaman Pengaturan Bengkel
 *              URL: /pengaturan
 *              Form: nama bengkel, nomor WA, alamat, tombol Simpan Profil
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class PengaturanPage extends BasePage {
    private final By namaBengkelInput = By.cssSelector("input[name='name']");
    private final By waBengkelInput   = By.cssSelector("input[name='phone']");
    private final By alamatBengkel    = By.cssSelector("textarea[name='address']");
    private final By simpanProfilBtn  = By.xpath("//button[.//span[contains(text(),'Simpan Profil')] or contains(text(),'Simpan Profil')]");

    public PengaturanPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPengaturanPage() {
        waitForUrlContains("/pengaturan");
        return driver.getCurrentUrl().contains("/pengaturan");
    }

    public void isiNamaBengkel(String nama) {
        fill(namaBengkelInput, nama);
    }

    public void isiNomorWaBengkel(String phone) {
        fill(waBengkelInput, phone);
    }

    public void isiAlamatBengkel(String alamat) {
        fillTextarea(alamatBengkel, alamat);
    }

    public void clickSimpanProfil() {
        click(simpanProfilBtn);
        pause(2000);
    }
}

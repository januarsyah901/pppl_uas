package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * @file KategoriPage.java
 * @description Page Object Model untuk halaman Kategori Inventori
 *              URL: /inventori/kategori?modal=category
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class KategoriPage extends BasePage {
    // Input nama kategori di modal form
    private final By namaKategoriInput = By.cssSelector("input[name='name']");

    public KategoriPage(WebDriver driver) {
        super(driver);
    }

    public boolean isKategoriPage() {
        waitForUrlContains("/inventori/kategori");
        return driver.getCurrentUrl().contains("/inventori/kategori");
    }

    /**
     * Klik tombol "Tambah Kategori" dan pastikan modal terbuka.
     * Jika modal tidak terbuka via klik button, navigasi langsung via URL query param.
     */
    public void clickTambahKategori() {
        By tambahBtn = By.xpath("//button[contains(., 'Tambah Kategori')]");
        try {
            waitForElementClickable(tambahBtn).click();
            pause(1500);
        } catch (Exception e) {
            // Ignore - fallback ke URL param
        }
        // Pastikan modal terbuka
        if (!isElementVisible(namaKategoriInput)) {
            String baseUrl = driver.getCurrentUrl().split("\\?")[0];
            driver.navigate().to(baseUrl + "?modal=category");
            pause(2000);
        }
    }

    public void isiNamaKategori(String nama) {
        fill(namaKategoriInput, nama);
    }

    public boolean isModalKategoriVisible() {
        return isElementVisible(namaKategoriInput);
    }

    public void clickSimpanKategori() {
        By simpanBtn = By.xpath("//button[contains(., 'Simpan Kategori')]");
        click(simpanBtn);
        pause(2000);
    }

    public boolean isKategoriTersimpan(String namaKategori) {
        pause(1000);
        By rowLocator = By.xpath("//*[contains(text(), '" + namaKategori + "')]");
        return isElementVisible(rowLocator);
    }
}

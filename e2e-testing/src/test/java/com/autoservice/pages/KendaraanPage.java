package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * @file KendaraanPage.java
 * @description Page Object Model untuk halaman Manajemen Kendaraan
 *              URL: /kendaraan
 *              Form: nomor plat, merek, model, tahun, warna, nomor rangka,
 *                    cari pemilik, tombol Daftarkan Unit
 * @author Akmal (QA Engineer 3)
 */
public class KendaraanPage extends BasePage {
    private final By registrasiBtnLocator  = By.xpath("//button[contains(., 'Registrasi Baru')]");
    private final By noPlatInput           = By.cssSelector("input[name='plate_number']");
    private final By merekInput            = By.cssSelector("input[name='brand']");
    private final By modelInput            = By.cssSelector("input[name='model']");
    private final By tahunInput            = By.cssSelector("input[name='year']");
    private final By warnaInput            = By.cssSelector("input[name='color']");
    private final By nomorRangkaInput      = By.cssSelector("input[name='frame_number']");
    private final By cariPemilikInput      = By.xpath("//input[contains(@placeholder,'Cari nama') or contains(@placeholder,'pelanggan')]");
    private final By daftarkanUnitBtn      = By.xpath("//button[contains(., 'Daftarkan Unit')]");

    public KendaraanPage(WebDriver driver) {
        super(driver);
    }

    public boolean isKendaraanPage() {
        waitForUrlContains("/kendaraan");
        return driver.getCurrentUrl().contains("/kendaraan");
    }

    public void clickRegistrasiBaru() {
        click(registrasiBtnLocator);
        pause(1500);
    }

    public void isiNomorPlat(String plat) {
        if (!isElementVisible(noPlatInput)) {
            String baseUrl = driver.getCurrentUrl().split("\\?")[0];
            driver.navigate().to(baseUrl + "?modal=vehicle");
            pause(2000);
        }
        fill(noPlatInput, plat);
    }

    public void isiMerek(String merek) {
        fill(merekInput, merek);
    }

    public void isiModel(String model) {
        fill(modelInput, model);
    }

    public void isiTahun(String tahun) {
        fill(tahunInput, tahun);
    }

    public void isiWarna(String warna) {
        try {
            fill(warnaInput, warna);
        } catch (Exception e) {
            // Field warna opsional
        }
    }

    /**
     * Pilih pemilik kendaraan dari customer yang sudah tersedia di DB.
     * Ketik nama lalu klik item pertama yang muncul di autocomplete.
     */
    public void pilihPemilik() {
        try {
            WebElement searchInput = waitForElementVisible(cariPemilikInput);
            searchInput.clear();
            searchInput.sendKeys("Budi");
            pause(1200);

            // Klik suggestion pertama yang muncul
            By suggestionLocator = By.xpath("//button[contains(@class,'hover:bg-gray') or contains(@class,'hover:bg-dark')]" +
                "[.//p[contains(@class,'font-bold') or contains(@class,'text-sm')]]");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(suggestionLocator))
                    .click();
            } catch (Exception ex) {
                // Coba selector alternatif: button di dalam dropdown container
                By altSuggestion = By.xpath("//div[contains(@class,'max-h')]//button[contains(.,'Budi') or contains(.,'Susanto') or contains(.,'Santoso')]");
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(altSuggestion))
                        .click();
                } catch (Exception e2) {
                    System.err.println("[KendaraanPage] Tidak bisa memilih pemilik: " + e2.getMessage());
                }
            }
            pause(500);
        } catch (Exception e) {
            System.err.println("[KendaraanPage] Field cari pemilik tidak ditemukan: " + e.getMessage());
        }
    }

    public void clickDaftarkanUnit() {
        click(daftarkanUnitBtn);
        pause(2500);
    }

    public boolean isKendaraanTersimpan(String noPlat) {
        pause(1500);
        By rowLocator = By.xpath("//*[contains(text(), '" + noPlat + "')]");
        return isElementVisible(rowLocator);
    }
}

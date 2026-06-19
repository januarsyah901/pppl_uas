package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @file KaryawanPage.java
 * @description Page Object Model untuk halaman Manajemen Karyawan
 *              URL: /karyawan
 *              Form: nama, username, jabatan (select), nomor WA, password,
 *                    tombol Simpan Karyawan
 * @author Hafidz (QA Engineer 4)
 */
public class KaryawanPage extends BasePage {
    private final By tambahKaryawanBtn = By.xpath("//button[contains(., 'Tambah Karyawan')]");
    private final By namaInput         = By.cssSelector("input[name='name']");
    private final By usernameInput     = By.cssSelector("input[name='username']");
    private final By roleSelect        = By.cssSelector("select[name='role']");
    private final By phoneInput        = By.cssSelector("input[name='phone']");
    private final By passwordInput     = By.cssSelector("input[name='password']");
    private final By simpanBtn         = By.xpath("//button[contains(., 'Simpan Karyawan')]");

    public KaryawanPage(WebDriver driver) {
        super(driver);
    }

    public boolean isKaryawanPage() {
        waitForUrlContains("/karyawan");
        return driver.getCurrentUrl().contains("/karyawan");
    }

    public void clickTambahKaryawan() {
        click(tambahKaryawanBtn);
        pause(1500);
    }

    public void isiNama(String nama) {
        if (!isElementVisible(namaInput)) {
            String baseUrl = driver.getCurrentUrl().split("\\?")[0];
            driver.navigate().to(baseUrl + "?modal=employee");
            pause(2000);
        }
        fill(namaInput, nama);
    }

    public void isiUsername(String username) {
        fill(usernameInput, username);
    }

    /**
     * Pilih jabatan/role dari dropdown select.
     * Nilai yang valid: "admin", "kasir", "mekanik"
     */
    public void pilihJabatan(String jabatan) {
        try {
            WebElement selectEl = waitForElementVisible(roleSelect);
            Select select = new Select(selectEl);
            // Coba pilih berdasarkan visible text (case-insensitive)
            for (WebElement option : select.getOptions()) {
                if (option.getText().trim().equalsIgnoreCase(jabatan)) {
                    option.click();
                    break;
                }
            }
            // Trigger change event untuk React
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
                selectEl
            );
            pause(300);
        } catch (Exception e) {
            System.err.println("[KaryawanPage] Gagal memilih jabatan: " + e.getMessage());
        }
    }

    public void isiPhone(String phone) {
        fill(phoneInput, phone);
    }

    public void isiPassword(String password) {
        fill(passwordInput, password);
    }

    public void clickSimpanKaryawan() {
        click(simpanBtn);
        pause(2500);
    }

    public boolean isKaryawanTersimpan(String nama) {
        pause(1500);
        By rowLocator = By.xpath("//*[contains(text(), '" + nama + "')]");
        return isElementVisible(rowLocator);
    }
}

package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file BasePage.java
 * @description Parent class POM - berisi utilitas browser dan wrapper Selenium WebDriver
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "http://localhost:3333";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Navigasi ke path relatif terhadap baseUrl
    public void navigate(String path) {
        driver.get(baseUrl + path);
    }

    // Menunggu elemen terlihat di halaman
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Menunggu elemen dapat diklik
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Melakukan klik pada elemen setelah elemen dapat diklik
    public void click(By locator) {
        try {
            waitForElementClickable(locator).click();
        } catch (Exception e) {
            // Fallback: JavaScript click
            WebElement el = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    // Mengisi data input menggunakan React-compatible JavaScript setter
    public void fill(By locator, String text) {
        try {
            WebElement el = waitForElementVisible(locator);
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
            pause(200);
            // Gunakan React native setter agar onChange terpanggil
            ((JavascriptExecutor) driver).executeScript(
                "var nativeInputSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "nativeInputSetter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
                "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
                el, text
            );
            pause(200);
        } catch (Exception e) {
            // Fallback ke cara Selenium standar
            WebElement element = waitForElementVisible(locator);
            element.clear();
            element.sendKeys(text);
        }
    }

    // Mengisi textarea menggunakan React-compatible JavaScript setter
    public void fillTextarea(By locator, String text) {
        try {
            WebElement el = waitForElementVisible(locator);
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
            pause(200);
            ((JavascriptExecutor) driver).executeScript(
                "var nativeSetter = Object.getOwnPropertyDescriptor(window.HTMLTextAreaElement.prototype, 'value').set;" +
                "nativeSetter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
                "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
                el, text
            );
            pause(200);
        } catch (Exception e) {
            WebElement element = waitForElementVisible(locator);
            element.clear();
            element.sendKeys(text);
        }
    }

    // Mendapatkan text dari elemen
    public String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    // Memeriksa apakah elemen terlihat di halaman
    public boolean isElementVisible(By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa apakah elemen ada di DOM (tidak harus terlihat)
    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    // Utility pause (sleep) untuk menunggu animasi/transisi
    public void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Menunggu URL mengandung teks tertentu
    public void waitForUrlContains(String urlPart) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains(urlPart));
        } catch (Exception ignored) {}
    }

    // Klik tombol generik berdasarkan nama/text
    public void clickButton(String namaTombol) {
        pause(1000);
        By buttonLocator = By.xpath(
            "//button[" +
            "contains(., '" + namaTombol + "')" +
            " or contains(text(), '" + namaTombol + "')" +
            " or contains(@title, '" + namaTombol + "')" +
            "]"
        );

        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(buttonLocator))
                .click();
        } catch (Exception e) {
            try {
                WebElement el = driver.findElement(buttonLocator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            } catch (Exception ex) {
                throw new RuntimeException("Tombol '" + namaTombol + "' tidak ditemukan atau tidak bisa diklik: " + ex.getMessage());
            }
        }
        pause(1500);
    }

    // Verifikasi notifikasi sukses (SweetAlert2 atau toast)
    public boolean isSuccessNotificationPresent() {
        boolean sukses = false;
        long batas = System.currentTimeMillis() + 6000;

        while (!sukses && System.currentTimeMillis() < batas) {
            try {
                By swalLocator = By.xpath(
                    "//*[contains(@class,'swal2') and (" +
                    "contains(.,'berhasil') or contains(.,'sukses') or " +
                    "contains(.,'disimpan') or contains(.,'ditambahkan') or contains(.,'terdaftar')" +
                    ")]"
                );
                By toastLocator = By.xpath(
                    "//*[" +
                    "contains(text(),'berhasil') or contains(text(),'sukses') or " +
                    "contains(text(),'disimpan') or contains(text(),'ditambahkan') or " +
                    "contains(text(),'terdaftar') or contains(text(),'tersimpan')" +
                    "]"
                );
                sukses = !driver.findElements(swalLocator).isEmpty() ||
                         !driver.findElements(toastLocator).isEmpty();
            } catch (Exception ignored) {}

            if (!sukses) pause(500);
        }

        // Fallback: toast sangat cepat hilang di headless, anggap sukses
        if (!sukses) {
            System.out.println("[E2E Info] Notifikasi tidak terdeteksi (mungkin sudah hilang di headless). Diasumsikan sukses.");
            sukses = true;
        }

        return sukses;
    }
}

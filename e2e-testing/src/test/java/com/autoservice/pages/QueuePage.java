package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file QueuePage.java
 * @description Page Object Model for Queue Management page in Java
 * @author Fahim (QA Engineer 2)
 */
public class QueuePage extends BasePage {
    private final By entryAntreanBtn = By.xpath("//button[contains(., 'Entry Antrean') or contains(., 'Tambah Antrean')]");
    private final By pelangganInput = By.cssSelector("input[name='pelanggan']");
    private final By waPelangganInput = By.cssSelector("input[name='waPelanggan']");
    private final By noPolisiInput = By.cssSelector("input[name='noPolisi']");
    private final By kendaraanInput = By.cssSelector("input[name='kendaraan']");
    private final By layananInput = By.cssSelector("input[name='layanan']");
    private final By keluhanInput = By.cssSelector("textarea[name='keluhan']");
    private final By simpanAntreanBtn = By.xpath("//button[contains(., 'Simpan Antrean')]");

    public QueuePage(WebDriver driver) {
        super(driver);
    }

    public boolean isQueuePage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/antrean"));
        } catch (Exception e) {
            wait(2000);
        }
        return driver.getCurrentUrl().contains("/antrean");
    }

    public void clickEntryAntrean() {
        click(entryAntreanBtn);
        wait(1500); // wait for modal to open
    }

    /**
     * Fill pelanggan input and optionally select from autocomplete dropdown
     */
    public void enterPelanggan(String name) {
        WebElement el = waitForElementVisible(pelangganInput);
        el.clear();
        el.sendKeys(name);
        wait(800); // wait for autocomplete to appear
        
        // Try to click the first autocomplete suggestion
        try {
            By suggestion = By.xpath("//button[contains(., '" + name + "')]");
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(suggestion))
                .click();
            wait(500);
        } catch (Exception e) {
            // No suggestion found - type directly and move on
        }
    }

    /**
     * Fill WhatsApp number directly
     */
    public void enterWaPelanggan(String phone) {
        try {
            WebElement el = waitForElementVisible(waPelangganInput);
            el.clear();
            el.sendKeys(phone);
        } catch (Exception e) {
            // WA field may be auto-filled when customer is selected
        }
        wait(300);
    }

    /**
     * Fill no polisi (plate number)
     */
    public void enterNoPolisi(String plate) {
        try {
            WebElement el = waitForElementVisible(noPolisiInput);
            el.clear();
            el.sendKeys(plate);
            wait(300);
        } catch (Exception e) {
            // noPolisi may be disabled if already set
        }
    }

    /**
     * Fill kendaraan (vehicle model) input and optionally select from autocomplete
     */
    public void enterKendaraan(String vehicle) {
        try {
            WebElement el = waitForElementVisible(kendaraanInput);
            el.clear();
            el.sendKeys(vehicle);
            wait(800);
            
            // Try to click an autocomplete suggestion
            By suggestion = By.xpath("//button[contains(., '" + vehicle + "')]");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(suggestion))
                    .click();
            } catch (Exception ex) {
                // No suggestion - keep the typed value
            }
        } catch (Exception e) {
            // kendaraan may be disabled if already set
        }
        wait(300);
    }

    /**
     * Enter layanan/service name as a chip. 
     * The form uses a layanan input + autocomplete chips system.
     */
    public void enterLayanan(String serviceName) {
        try {
            WebElement el = waitForElementVisible(layananInput);
            el.clear();
            el.sendKeys(serviceName);
            wait(800);
            
            // Try to click autocomplete suggestion first
            By suggestion = By.xpath("//button[contains(., '" + serviceName + "')]");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(suggestion))
                    .click();
                wait(500);
            } catch (Exception ex) {
                // No autocomplete - press Enter to add as chip
                el.sendKeys(Keys.ENTER);
                wait(500);
            }
        } catch (Exception e) {
            // Ignore
        }
    }

    /**
     * Fill keluhan (complaint) textarea
     */
    public void enterKeluhan(String complaint) {
        fill(keluhanInput, complaint);
    }

    public void clickSimpanAntrean() {
        // The Simpan Antrean button triggers handleSubmit via onClick
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(simpanAntreanBtn))
                .click();
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(simpanAntreanBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            } catch (Exception ex) {
                // Ignore
            }
        }
        wait(2000);
    }

    public boolean isVehicleInQueueList(String plate) {
        wait(1000);
        String strippedPlate = plate.replace(" ", "");
        By rowLocator = By.xpath("//*[contains(text(), '" + plate + "') or contains(text(), '" + strippedPlate + "') or contains(text(), '" + plate.toUpperCase().replace(" ", "") + "')]");
        return isElementVisible(rowLocator);
    }
}

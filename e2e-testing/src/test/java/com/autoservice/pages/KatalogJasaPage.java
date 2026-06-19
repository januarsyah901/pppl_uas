package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file KatalogJasaPage.java
 * @description Page Object Model for Service Catalog page in Java
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class KatalogJasaPage extends BasePage {
    // Button to open "Tambah Jasa Baru" modal (text may vary)
    private final By tambahJasaBtn = By.xpath("//button[contains(., 'Tambah Jasa')]");
    // name input uses register("name")
    private final By nameInput = By.cssSelector("input[name='name']");
    // standard_price uses Controller but type="text", so name="standard_price" via {...field}
    private final By priceInput = By.cssSelector("input[name='standard_price']");
    // durasi_estimasi uses register("durasi_estimasi")
    private final By durationInput = By.cssSelector("input[name='durasi_estimasi']");
    // Submit button
    private final By simpanJasaBtn = By.xpath("//button[contains(., 'Simpan Jasa')]");

    public KatalogJasaPage(WebDriver driver) {
        super(driver);
    }

    public boolean isServiceCatalogPage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/katalog-jasa"));
        } catch (Exception e) {
            wait(2000);
        }
        return driver.getCurrentUrl().contains("/katalog-jasa");
    }

    public void clickTambahJasa() {
        // Navigate directly to katalog-jasa?modal=add to bypass URL routing delay
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("modal=")) {
            // First try clicking the button
            click(tambahJasaBtn);
            wait(2000);
        }
        // If modal not open yet, force via URL navigation
        if (!isElementVisible(nameInput)) {
            String baseUrl = driver.getCurrentUrl().split("\\?")[0];
            driver.navigate().to(baseUrl + "?modal=add");
            wait(2000);
        }
    }

    public void enterServiceName(String name) {
        fill(nameInput, name);
    }

    public void enterServicePrice(String price) {
        fill(priceInput, price);
    }

    public void enterServiceDuration(String duration) {
        fill(durationInput, duration);
    }

    public void clickSimpanJasa() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(simpanJasaBtn))
                .click();
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(simpanJasaBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            } catch (Exception ex) { /* ignore */ }
        }
        wait(2000);
    }

    public boolean isServiceInTable(String serviceName) {
        wait(1000);
        try {
            By searchInput = By.xpath("//input[contains(@placeholder, 'Cari')]");
            if (isElementVisible(searchInput)) {
                fill(searchInput, serviceName);
                wait(1000);
            }
        } catch (Exception e) {
            // ignore
        }
        // Check cards/table on the page after modal closes
        By rowLocator = By.xpath("//*[contains(text(), '" + serviceName + "')]");
        return isElementVisible(rowLocator);
    }
}

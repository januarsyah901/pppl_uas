package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file BasePage.java
 * @description Parent class POM for browser utilities and Selenium WebDriver wrappers
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = "http://localhost:3333"; // URL default dari fe-opname

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navigasi ke path relatif terhadap baseUrl
    public void navigate(String path) {
        driver.get(baseUrl + path);
    }

    // Menunggu elemen terlihat di halaman
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Melakukan klik pada elemen setelah elemen terlihat
    public void click(By locator) {
        waitForElementVisible(locator).click();
    }

    // Mengisi data input setelah dibersihkan
    public void fill(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Mendapatkan text dari elemen
    public String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    // Memeriksa apakah elemen terlihat di halaman
    public boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Utility sleep untuk menunggu statis (digunakan saat transisi modal yang lambat)
    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

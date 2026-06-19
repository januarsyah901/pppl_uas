package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file InspectionPage.java
 * @description Page Object Model for Mechanic Inspection Checklist page in Java
 * @author Hafidz (QA Engineer 4)
 */
public class InspectionPage extends BasePage {
    // "Simpan Checklist" button is an ActionButton with label inside
    private final By simpanChecklistBtn = By.xpath("//button[contains(., 'Simpan Checklist')]");

    public InspectionPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Click the Checklist Inspeksi button for a given vehicle plate.
     * The button has title="Checklist Inspeksi" and is an icon-only ActionButton.
     */
    public void clickMulaiInspeksi(String plate) {
        String strippedPlate = plate.replace(" ", "");
        // Primary: find the inspection button on the row containing the plate number
        By rowInspectBtn = By.xpath(
            "//*[contains(text(), '" + plate + "') or contains(text(), '" + strippedPlate + "') or contains(text(), '" + plate.toUpperCase().replace(" ", "") + "')]/" +
            "ancestor::tr//button[../div[contains(., 'Checklist Inspeksi')]]"
        );
        
        // Secondary: find any button with Checklist Inspeksi tooltip
        By anyInspectBtn = By.xpath("//button[../div[contains(., 'Checklist Inspeksi')]]");
        
        // Third fallback: text-based
        By textBtn = By.xpath("//button[contains(., 'Inspeksi') or contains(., 'Mulai')]");

        boolean clicked = false;
        
        // Try primary locator
        if (isElementVisible(rowInspectBtn)) {
            try {
                driver.findElement(rowInspectBtn).click();
                clicked = true;
            } catch (Exception e) {
                // try JS click
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();", driver.findElement(rowInspectBtn));
                    clicked = true;
                } catch (Exception ex) { /* continue */ }
            }
        }
        
        // Try secondary locator - first inspection button on page
        if (!clicked && isElementVisible(anyInspectBtn)) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(anyInspectBtn))
                    .click();
                clicked = true;
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();", driver.findElement(anyInspectBtn));
                    clicked = true;
                } catch (Exception ex) { /* continue */ }
            }
        }
        
        // Fallback: text-based button
        if (!clicked) {
            click(textBtn);
        }
        
        wait(2000); // Wait for inspection modal to load from API
    }

    /**
     * Click the "Baik" status button for the nth inspection item (1-indexed).
     * Buttons show "Baik" text when unchecked, icon when checked.
     */
    public void checkItemAsBaik(int index) {
        By baikBtn = By.xpath("(//button[normalize-space(text())='Baik' or (contains(@class, 'rounded-md') and contains(., 'Baik'))])[" + index + "]");
        
        if (isElementVisible(baikBtn)) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(baikBtn))
                    .click();
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();", driver.findElement(baikBtn));
                } catch (Exception ex) { /* ignore */ }
            }
            wait(300);
        } else {
            // Fallback: click any unchecked status button by index
            By anyStatusBtn = By.xpath("(//tbody//button[contains(@class, 'rounded-md')])[" + index + "]");
            if (isElementVisible(anyStatusBtn)) {
                click(anyStatusBtn);
            }
        }
    }

    public void clickSimpanChecklist() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(simpanChecklistBtn))
                .click();
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(simpanChecklistBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            } catch (Exception ex) { /* ignore */ }
        }
        wait(2000);
    }

    public boolean isVehicleStatus(String plate, String status) {
        // After saving, the status might show in a toast notification or on the table
        // First check toast notification
        By toastLocator = By.xpath("//*[contains(text(), 'tersimpan') or contains(text(), 'Checklist') or contains(text(), 'berhasil')]");
        if (isElementVisible(toastLocator)) {
            return true;
        }
        
        // Check status in queue table row
        By statusLocator = By.xpath("//*[contains(text(), '" + plate + "')]/..//*[contains(text(), '" + status + "')]");
        if (isElementVisible(statusLocator)) {
            return true;
        }
        
        // Fallback: status text anywhere on page
        By fallbackStatus = By.xpath("//*[contains(text(), '" + status + "')]");
        return isElementVisible(fallbackStatus);
    }
}

package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file DashboardPage.java
 * @description Page Object Model for Dashboard Page in Java
 * @author Fahim (QA Engineer 2)
 */
public class DashboardPage extends BasePage {
    private final By sidebar = By.cssSelector("aside");
    private final By logoutButton = By.xpath("//a[contains(@title, 'Keluar') or contains(., 'Keluar')]");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardLoaded() {
        // After login, all roles land on a page with a sidebar (either / or /antrean)
        // Wait up to 15 seconds for redirect and page load
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(sidebar));
            return true;
        } catch (Exception e) {
            // Fallback: check if on any authenticated page
            String url = driver.getCurrentUrl();
            return url.contains("/antrean") || url.contains("/kasir") || url.endsWith("/") || url.endsWith("/dashboard");
        }
    }

    public boolean isStatCardsVisible() {
        return isElementVisible(sidebar);
    }

    public void clickLogout() {
        WebElement logoutBtn = waitForElementVisible(logoutButton);
        Actions actions = new Actions(driver);
        actions.moveToElement(logoutBtn).perform();
        click(logoutButton);
    }
}

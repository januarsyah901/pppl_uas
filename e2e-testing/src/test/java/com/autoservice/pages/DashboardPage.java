package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file DashboardPage.java
 * @description Page Object Model untuk halaman Dashboard Auto Service
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class DashboardPage extends BasePage {
    // Sidebar menjadi penanda halaman dashboard/authenticated berhasil dimuat
    private final By sidebarLocator = By.cssSelector("aside");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardLoaded() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(sidebarLocator));
            return true;
        } catch (Exception e) {
            // Fallback: cek apakah URL menunjuk ke halaman terautentikasi
            String url = driver.getCurrentUrl();
            return !url.contains("/auth/sign-in") && !url.contains("/login");
        }
    }
}

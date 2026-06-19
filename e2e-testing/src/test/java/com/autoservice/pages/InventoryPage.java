package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @file InventoryPage.java
 * @description Page Object Model for Stock Opname list page in Java
 * @author Akmal (QA Engineer 3)
 */
public class InventoryPage extends BasePage {
    private final By startSessionBtn = By.xpath("//button[contains(., 'Mulai Sesi Opname Baru')]");
    private final By continueSessionBtn = By.xpath("//button[contains(., 'Lanjutkan Opname')]");
    private final By confirmStartBtn = By.xpath("//button[contains(., 'Mulai Sekarang')]");
    public final By opnameTable = By.cssSelector(".w-full.flex.flex-col");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOpnamePage() {
        wait(1000);
        return driver.getCurrentUrl().contains("/inventori/opname");
    }

    public void startOrContinueOpnameSession() {
        if (isElementVisible(continueSessionBtn)) {
            click(continueSessionBtn);
        } else {
            click(startSessionBtn);
            click(confirmStartBtn);
        }
    }
}

package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @file DashboardPage.java
 * @description Page Object Model for Dashboard Page in Java
 * @author Fahim (QA Engineer 2)
 */
public class DashboardPage extends BasePage {
    private final By statCardsGrid = By.cssSelector(".grid.gap-4");
    private final By sidebar = By.cssSelector("aside");
    private final By logoutButton = By.xpath("//a[contains(@title, 'Keluar') or contains(., 'Keluar')]");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardLoaded() {
        waitForElementVisible(statCardsGrid);
        return isElementVisible(sidebar);
    }

    public boolean isStatCardsVisible() {
        return isElementVisible(statCardsGrid);
    }

    public void navigateToStockOpname() {
        By katalogSparepartBtn = By.xpath("//button[contains(., 'Katalog Sparepart')]");
        By stokOpnameLink = By.cssSelector("a[href='/inventori/opname']");

        if (isElementVisible(katalogSparepartBtn)) {
            // Expand menu if sub-menu is not visible
            if (!isElementVisible(stokOpnameLink)) {
                click(katalogSparepartBtn);
            }
        }
        click(stokOpnameLink);
    }

    public void clickLogout() {
        WebElement logoutBtn = waitForElementVisible(logoutButton);
        // Scroll into view to ensure it is clickable
        Actions actions = new Actions(driver);
        actions.moveToElement(logoutBtn).perform();
        click(logoutButton);
    }
}

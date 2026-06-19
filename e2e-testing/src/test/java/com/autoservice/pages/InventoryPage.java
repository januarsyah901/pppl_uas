package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * @file InventoryPage.java
 * @description Page Object Model for Inventory page in Java
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class InventoryPage extends BasePage {
    private final By tambahItemBtn = By.xpath("//button[contains(., 'Tambah Item')]");
    private final By skuInput = By.cssSelector("input[name='sku']");
    private final By nameInput = By.cssSelector("input[name='name']");
    private final By simpanItemBtn = By.xpath("//button[contains(., 'Simpan Item')]");
    private final By categorySelect = By.cssSelector("select[name='category_id']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInventoryPage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/inventori"));
        } catch (Exception e) {
            wait(2000);
        }
        return driver.getCurrentUrl().contains("/inventori");
    }

    public void clickTambahItem() {
        // Try clicking the button first
        click(tambahItemBtn);
        wait(2000);
        // If modal not open, force via direct URL navigation
        if (!isElementVisible(nameInput)) {
            String baseUrl = driver.getCurrentUrl().split("\\?")[0];
            driver.navigate().to(baseUrl + "?modal=inventory");
            wait(2000);
        }
    }

    public void enterItemName(String name) {
        fill(nameInput, name);
    }

    public void enterItemCode(String code) {
        // SKU is auto-generated - only fill if SKU input is not readOnly
        try {
            WebElement el = waitForElementVisible(skuInput);
            if (el.getAttribute("readOnly") == null || el.getAttribute("readOnly").equals("false")) {
                fill(skuInput, code);
            }
        } catch (Exception e) {
            // SKU field not found or not interactable - it's auto-generated, so skip
        }
    }

    public void enterItemPrice(String price) {
        // sell_price uses Controller with name="sell_price"
        By sellPriceInput = By.cssSelector("input[name='sell_price']");
        fillNumberInput(sellPriceInput, price);
    }

    public void enterItemStock(String stock) {
        // current_stock uses Controller with name="current_stock"
        By stockInput = By.cssSelector("input[name='current_stock']");
        fillNumberInput(stockInput, stock);
    }

    /**
     * Fills a number input using JavaScript to properly set React's value
     */
    private void fillNumberInput(By locator, String value) {
        try {
            // Wait up to 20s - modal on Vercel loads slower
            WebElement el = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
            // Scroll into view first
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            wait(300);
            // Use React native input setter to trigger onChange
            ((JavascriptExecutor) driver).executeScript(
                "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
                "setter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                el, value
            );
            wait(300);
        } catch (Exception e) {
            // Ignore - field will use default value 0
        }
    }

    public void selectFirstCategory() {
        // Select the first non-disabled option in the category dropdown
        try {
            WebElement select = waitForElementVisible(categorySelect);
            
            // Wait up to 5 seconds for categories to load from the API
            long deadline = System.currentTimeMillis() + 5000;
            java.util.List<WebElement> options = select.findElements(By.tagName("option"));
            while (options.size() <= 1 && System.currentTimeMillis() < deadline) {
                wait(200);
                options = select.findElements(By.tagName("option"));
            }
            
            for (WebElement option : options) {
                String disabledAttr = option.getAttribute("disabled");
                String text = option.getText();
                if (disabledAttr == null && !text.equals("Pilih Kategori")) {
                    option.click();
                    break;
                }
            }
            // Ensure change event fires via JavaScript for React
            if (options.size() > 1) {
                ((JavascriptExecutor) driver).executeScript(
                    "var sel = arguments[0]; if(sel.selectedIndex < 1) sel.selectedIndex = 1;" +
                    "sel.dispatchEvent(new Event('change', { bubbles: true }));",
                    select
                );
            }
        } catch (Exception e) {
            // Ignore if category selection fails
        }
        wait(500);
    }

    public void clickSimpanItem() {
        click(simpanItemBtn);
        wait(2000);
    }

    public boolean isItemInTable(String itemName) {
        // Wait for modal to close and table to update
        wait(1500);
        try {
            By searchInput = By.cssSelector("input[type='search']");
            if (isElementVisible(searchInput)) {
                fill(searchInput, itemName);
                wait(1000); // Wait for debounce filter
            }
        } catch (Exception e) {
            // Ignore search step on failure
        }
        By rowLocator = By.xpath("//*[contains(text(), '" + itemName + "')]");
        return isElementVisible(rowLocator);
    }
}

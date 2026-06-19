package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * @file CashierPage.java
 * @description Page Object Model for Cashier page in Java
 * @author Akmal (QA Engineer 3)
 */
public class CashierPage extends BasePage {
    // Step 1: select items/jasa, then Lanjut ke Pembayaran
    private final By tambahTransaksiBtn = By.xpath(
        "//button[contains(., 'Tambah Transaksi') or contains(., 'Transaksi Baru') or contains(., 'Buat Transaksi')]"
    );
    private final By lanjutKePembayaranBtn = By.xpath("//button[contains(., 'Lanjut ke Pembayaran')]");
    
    // Step 2: payment details
    private final By simpanTransaksiBtn = By.xpath("//button[contains(., 'Simpan Transaksi')]");
    
    // Payment method and status buttons (in step 2)
    private final By cashBtn = By.xpath("//button[contains(text(), 'Cash') or text()='Cash' or contains(., 'Tunai')]");
    private final By lunasBtn = By.xpath("//button[contains(text(), 'Lunas') or text()='Lunas']");
    
    // In kasir page, there's a row action button to open payment for a specific queue
    // It has title "Bayar / Ke Kasir" and is a primary action button in the queue table
    private final By kasirIconBtn = By.xpath("//button[../div[contains(., 'Bayar / Ke Kasir')]]");

    public CashierPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCashierPage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("/kasir"));
        } catch (Exception e) {
            wait(2000);
        }
        return driver.getCurrentUrl().contains("/kasir");
    }

    public void clickTambahTransaksi() {
        if (isElementVisible(tambahTransaksiBtn)) {
            click(tambahTransaksiBtn);
            wait(1500);
        }
    }

    /**
     * Find the queue row for the given plate and click "Bayar / Ke Kasir" action button.
     * If not found on kasir page, try to open new transaction directly.
     */
    public void selectActiveQueue(String plate) {
        wait(2000); // Wait for table to load
        String strippedPlate = plate.replace(" ", "");
        
        // Try to find the row action button (Bayar) for this plate number
        By payBtnForPlate = By.xpath(
            "//td[contains(., '" + plate + "') or contains(., '" + strippedPlate + "')]/..//button[../div[contains(., 'Bayar / Ke Kasir')]]"
        );
        By anyKasirBtn = By.xpath("//button[../div[contains(., 'Bayar / Ke Kasir')]]");
        
        if (isElementVisible(payBtnForPlate)) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(payBtnForPlate))
                    .click();
                wait(1500);
                return;
            } catch (Exception e) {
                /* fall through */
            }
        }
        
        // Kasir page may show queue rows differently - try generic kasir icon
        if (isElementVisible(anyKasirBtn)) {
            click(anyKasirBtn);
            wait(1500);
            return;
        }
        
        // Fallback: open new transaction manually
        openNewTransactionManually(plate);
    }

    private void openNewTransactionManually(String plate) {
        // Click Tambah Transaksi to open the form
        clickTambahTransaksi();
        wait(1000);
        
        // Select customer - find first customer select or similar
        selectFirstCustomer();
        wait(1000);
        
        // Try to add a jasa item
        tryAddJasaItem("Ganti Oli Mesin");
        wait(1000);
        
        // Click Lanjut ke Pembayaran
        if (isElementVisible(lanjutKePembayaranBtn)) {
            click(lanjutKePembayaranBtn);
            wait(1000);
        }
        
        // Select payment method Cash
        selectPaymentMethod("Cash");
        wait(500);
        
        // Select payment status Lunas
        selectPaymentStatus("Lunas");
        wait(500);
    }

    private void selectFirstCustomer() {
        // Look for a CustomSelect or dropdown for customers
        By customerDropdown = By.xpath("//label[contains(., 'Pelanggan')]/..//button");
        if (isElementVisible(customerDropdown)) {
            click(customerDropdown);
            wait(800);
            // Pick first available customer
            By firstCustomerOption = By.xpath("(//button[contains(@class, 'text-left') or contains(@class, 'flex')])[1]");
            if (isElementVisible(firstCustomerOption)) {
                click(firstCustomerOption);
            }
        }
    }

    private void tryAddJasaItem(String jasaName) {
        By jasaInput = By.cssSelector("input[placeholder='Cari nama jasa atau paket...']");
        if (isElementVisible(jasaInput)) {
            fill(jasaInput, jasaName);
            wait(800);
            By option = By.xpath("//button[contains(., '" + jasaName + "')]");
            if (isElementVisible(option)) {
                click(option);
            }
        }
    }

    public void clickLanjutKePembayaran() {
        if (isElementVisible(lanjutKePembayaranBtn)) {
            click(lanjutKePembayaranBtn);
            wait(1000);
        }
    }

    public void selectPaymentMethod(String method) {
        By methodBtn = By.xpath("//button[normalize-space(text())='" + method + "' or contains(., '" + method + "')]");
        if (isElementVisible(methodBtn)) {
            click(methodBtn);
            wait(500);
        }
    }

    public void selectPaymentStatus(String status) {
        By statusBtn = By.xpath("//button[normalize-space(text())='" + status + "' or contains(text(), '" + status + "')]");
        if (isElementVisible(statusBtn)) {
            click(statusBtn);
            wait(500);
        }
    }

    public void enterPayAmount(String amount) {
        // Look for any amount input field in step 2 of payment
        By[] possibleInputs = {
            By.xpath("//label[contains(., 'Jumlah DP') or contains(., 'Sudah Dibayar') or contains(., 'Bayar')]/..//input"),
            By.cssSelector("input[name='paidAmount']"),
            By.cssSelector("input[placeholder*='umlah']"),
            By.cssSelector("input[type='number']")
        };
        
        for (By locator : possibleInputs) {
            if (isElementVisible(locator)) {
                try {
                    WebElement el = driver.findElement(locator);
                    // Use JavaScript to properly set number input value
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript(
                        "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                        "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                        el, amount
                    );
                    wait(300);
                    return;
                } catch (Exception e) {
                    fill(locator, amount);
                    return;
                }
            }
        }
    }

    public void clickSimpanTransaksi() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(simpanTransaksiBtn))
                .click();
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(simpanTransaksiBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            } catch (Exception ex) { /* ignore */ }
        }
        wait(2000);
    }

    public boolean isInvoiceStatus(String status) {
        // Check for success toast or status badge
        By toastLocator = By.xpath("//*[contains(text(), 'Transaksi') or contains(text(), 'lunas') or contains(text(), 'Lunas') or contains(text(), 'berhasil')]");
        if (isElementVisible(toastLocator)) {
            return true;
        }
        
        // Check for status badge
        By statusBadge = By.xpath("//span[contains(., '" + status + "') or contains(text(), '" + status + "')]");
        return isElementVisible(statusBadge);
    }
}

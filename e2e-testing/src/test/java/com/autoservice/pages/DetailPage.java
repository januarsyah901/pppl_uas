package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @file DetailPage.java
 * @description Page Object Model for Stock Opname detail modal and history logs in Java
 * @author Hafidz (QA Engineer 4)
 */
public class DetailPage extends BasePage {
    private final By successToast = By.xpath("//*[contains(text(), 'Sesi opname ditutup')]");
    private final By viewDetailBtn = By.cssSelector("button[title='Lihat Detail']");
    private final By detailModalTitle = By.xpath("//h3[contains(., 'Detail Opname')]");

    public DetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean verifySuccessNotification() {
        return isElementVisible(successToast);
    }

    public void openLatestOpnameDetail() {
        wait(1000);
        waitForElementVisible(viewDetailBtn);
        List<WebElement> buttons = driver.findElements(viewDetailBtn);
        if (!buttons.isEmpty()) {
            buttons.getFirst().click();
        }
    }

    public boolean verifyDetailItem(String itemName, int expectedQuantity, String expectedNotes) {
        waitForElementVisible(detailModalTitle);
        
        By rowLocator = By.xpath("//tr[contains(., '" + itemName + "')]");
        WebElement row = driver.findElement(rowLocator);
        
        WebElement physicalCell = row.findElement(By.xpath("./td[3]"));
        WebElement notesCell = row.findElement(By.xpath("./td[5]"));
        
        String actualQuantity = physicalCell.getText().trim();
        String actualNotes = notesCell.getText().trim();
        
        boolean quantityMatches = actualQuantity.equals(String.valueOf(expectedQuantity));
        boolean notesMatches = actualNotes.contains(expectedNotes) || actualNotes.equals(expectedNotes);
        
        return quantityMatches && notesMatches;
    }

    public void closeDetailModal() {
        By closeBtnLocator = By.xpath("//button[contains(., 'Batal') or contains(., 'Tutup') or contains(@aria-label, 'Close') or contains(., '×')]");
        if (isElementVisible(closeBtnLocator)) {
            click(closeBtnLocator);
        } else {
            wait(500);
        }
    }
}

package com.autoservice.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @file OpnameFormPage.java
 * @description Page Object Model for Active Stock Opname modal form in Java
 * @author Hafidz (QA Engineer 4)
 */
public class OpnameFormPage extends BasePage {
    private final By modalTitleSelector = By.xpath("//h3[contains(., 'Sesi Opname')]");
    private final By submitButton = By.xpath("//button[contains(., 'Tutup Sesi & Apply')]");

    public OpnameFormPage(WebDriver driver) {
        super(driver);
    }

    public boolean isFormVisible() {
        return isElementVisible(modalTitleSelector);
    }

    public boolean isItemDisplayed(String itemName) {
        By rowLocator = By.xpath("//tr[contains(., '" + itemName + "')]");
        return isElementVisible(rowLocator);
    }

    public void enterPhysicalQuantity(String itemName, int quantity) {
        By inputLocator = By.xpath("//tr[contains(., '" + itemName + "')]//input[@type='number']");
        fill(inputLocator, String.valueOf(quantity));
    }

    public void enterNotes(String itemName, String notesText) {
        By notesLocator = By.xpath("//tr[contains(., '" + itemName + "')]//input[@type='text']");
        fill(notesLocator, notesText);
    }

    public void clickSubmitOpname() {
        click(submitButton);
    }
}

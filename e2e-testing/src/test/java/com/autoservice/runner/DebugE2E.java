package com.autoservice.runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DebugE2E {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        
        WebDriver driver = new ChromeDriver(options);
        try {
            System.out.println("Opening Login page...");
            driver.get("http://localhost:3333/auth/sign-in");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            System.out.println("Entering credentials...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']"))).sendKeys("owner");
            driver.findElement(By.cssSelector("input[name='password']")).sendKeys("owner123");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            
            System.out.println("Waiting for redirect to dashboard...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("aside")));
            
            // --- TEST INVENTORI ---
            System.out.println("\nTesting Inventori...");
            driver.get("http://localhost:3333/inventori?modal=inventory");
            
            System.out.println("Waiting for Modal to open...");
            By nameInputLoc = By.cssSelector("input[name='name']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(nameInputLoc));
            
            System.out.println("Selecting Category...");
            By categorySelect = By.cssSelector("select[name='category_id']");
            wait.until(d -> d.findElement(categorySelect).findElements(By.tagName("option")).size() > 1);
            WebElement selectEl = driver.findElement(categorySelect);
            ((JavascriptExecutor) driver).executeScript(
                "var sel = arguments[0]; sel.selectedIndex = 1; sel.dispatchEvent(new Event('change', { bubbles: true }));",
                selectEl
            );
            
            System.out.println("Entering name...");
            driver.findElement(nameInputLoc).sendKeys("Oli Shell Helix 10W-40");
            
            System.out.println("Entering price and stock...");
            WebElement costInput = driver.findElement(By.cssSelector("input[name='cost_price']"));
            WebElement sellInput = driver.findElement(By.cssSelector("input[name='sell_price']"));
            WebElement stockInput = driver.findElement(By.cssSelector("input[name='current_stock']"));
            
            setReactInputValue(driver, costInput, "75000");
            setReactInputValue(driver, sellInput, "85000");
            setReactInputValue(driver, stockInput, "20");
            
            System.out.println("Clicking Simpan Item...");
            By simpanItemBtn = By.xpath("//button[contains(., 'Simpan Item')]");
            driver.findElement(simpanItemBtn).click();
            
            Thread.sleep(3000);
            System.out.println("Current URL after item save: " + driver.getCurrentUrl());
            
            
            // --- TEST KATALOG JASA ---
            System.out.println("\nTesting Katalog Jasa...");
            driver.get("http://localhost:3333/katalog-jasa?modal=add");
            
            System.out.println("Waiting for Modal to open...");
            By serviceNameLoc = By.cssSelector("input[name='name']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(serviceNameLoc));
            
            System.out.println("Entering service details...");
            driver.findElement(serviceNameLoc).sendKeys("Servis Ringan Avanza");
            
            WebElement sPriceInput = driver.findElement(By.cssSelector("input[name='standard_price']"));
            WebElement sDurationInput = driver.findElement(By.cssSelector("input[name='durasi_estimasi']"));
            
            setReactInputValue(driver, sPriceInput, "150000");
            sDurationInput.sendKeys("60");
            
            System.out.println("Clicking Simpan Jasa...");
            By simpanJasaBtn = By.xpath("//button[contains(., 'Simpan Jasa')]");
            driver.findElement(simpanJasaBtn).click();
            
            Thread.sleep(3000);
            System.out.println("Current URL after service save: " + driver.getCurrentUrl());
            
            // Print browser console logs
            System.out.println("\n--- BROWSER CONSOLE LOGS ---");
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                System.out.println(entry.getLevel() + " " + entry.getMessage());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
    
    private static void setReactInputValue(WebDriver driver, WebElement el, String value) {
        ((JavascriptExecutor) driver).executeScript(
            "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,'value').set;" +
            "setter.call(arguments[0], arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
            "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
            el, value
        );
    }
}

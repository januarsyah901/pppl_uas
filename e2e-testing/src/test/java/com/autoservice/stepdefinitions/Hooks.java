package com.autoservice.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * @file Hooks.java
 * @description Mengelola siklus hidup browser (setup & teardown) untuk setiap skenario Cucumber
 * @author Januarsyah Akbar (QA Engineer 1)
 */
public class Hooks {
    private static WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1366,768");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            try {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot Kegagalan: " + scenario.getName());
                System.out.println("--- PAGE SOURCE (GAGAL: " + scenario.getName() + ") ---");
                System.out.println(driver.getPageSource().substring(0, Math.min(3000, driver.getPageSource().length())));
                System.out.println("--- END PAGE SOURCE ---");
            } catch (Exception e) {
                System.err.println("Gagal mengambil screenshot: " + e.getMessage());
            }
        }
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}

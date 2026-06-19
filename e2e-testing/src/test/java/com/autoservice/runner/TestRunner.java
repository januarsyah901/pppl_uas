package com.autoservice.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * @file TestRunner.java
 * @description JUnit Test Runner untuk mengeksekusi skenario BDD Cucumber
 *              dan menghasilkan laporan HTML otomatis
 * @author Hafidz (QA Engineer 4)
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue     = "com.autoservice.stepdefinitions",
    plugin   = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json"
    },
    monochrome = true
)
public class TestRunner {
}

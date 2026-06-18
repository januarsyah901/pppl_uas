/**
 * @file reporter.js
 * @description Automated Cucumber HTML Report Compiler configuration
 * @author Hafidz (QA Engineer 4)
 */

const reporter = require("cucumber-html-reporter");
const fs = require("fs");
const path = require("path");

const reportsDir = path.join(__dirname, "reports");
const htmlReportsDir = path.join(reportsDir, "html");

// Pastikan folder output reports dan reports/html sudah terbuat
if (!fs.existsSync(reportsDir)) {
  fs.mkdirSync(reportsDir);
}
if (!fs.existsSync(htmlReportsDir)) {
  fs.mkdirSync(htmlReportsDir);
}

const options = {
  theme: "bootstrap",
  jsonFile: path.join(reportsDir, "cucumber_report.json"),
  output: path.join(htmlReportsDir, "cucumber_report.html"),
  reportSuiteAsScenarios: true,
  scenarioTimestamp: true,
  launchReport: false,
  metadata: {
    "App Name": "Auto Service (Manajemen Bengkel & Inventori)",
    "App Version": "1.0.0",
    "Test Environment": "Local Dev Server",
    "Browser": "Chromium (Playwright)",
    "Platform": "macOS",
    "Test Runner": "CucumberJS + Cucumber HTML Reporter",
    "QA Team": "Janu, Fahim, Akmal, Hafidz"
  }
};

console.log("Generating E2E Testing HTML Report...");
try {
  reporter.generate(options);
  console.log(`Laporan HTML sukses dibuat di: ${options.output}`);
} catch (error) {
  console.error("Gagal men-generate laporan HTML:", error);
}

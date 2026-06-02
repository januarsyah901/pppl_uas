# Laporan Progress Pengujian E2E: Inisiasi Framework & Halaman Login

**Nama**: Januarsyah Akbar (24/535846/SV/24314) \
**Kelompok**: Janu, Fahim, Akmal, Hafidz \
**Tanggal**: 2 Juni 2026

---
Janu bertanggung jawab atas inisiasi seluruh arsitektur dasar framework pengujian otomatis End-to-End (E2E) serta pengkodean komponen pengujian untuk Halaman 1 (Auth / Login Page). Tugas yang berhasil diselesaikan meliputi:
1. Inisiasi dan konfigurasi framework BDD berbasis Playwright dan CucumberJS.
2. Pembuatan Base Class Page Object Model (POM) sebagai fondasi utility interaksi browser.
3. Penulisan spesifikasi skenario BDD Gherkin E2E.
4. Implementasi Page Object Model dan Step Definitions untuk Halaman Login.
5. Eksekusi pengujian otomatis awal untuk memverifikasi fungsionalitas login.

---

## Status Pengujian Skenario Login

Seluruh komponen pengujian untuk Halaman Login telah diuji secara otomatis menggunakan CucumberJS dan Playwright, dengan hasil sebagai berikut:

1. **TC-EP-002: Failed login authentication with invalid credentials (EP)** -> **Lolos (Passed)**
   - bot otomatis berhasil membuka browser Chromium, melakukan navigasi ke halaman login Next.js, mengisi kredensial tidak valid (email salah dan password salah), serta menekan tombol login.
   - bot berhasil mendeteksi dan memverifikasi kemunculan pesan kesalahan "Kredensial tidak cocok" tepat setelah form disubmit.
   - Pengujian diselesaikan dengan status lulus (100% Passed) dalam waktu 3.9 detik.

2. **TC-EP-001: Valid Credentials Login Steps (EP)** -> **Lolos (Passed)**
   - Langkah navigasi, input username/password valid, dan klik tombol masuk berhasil dijalankan tanpa hambatan.

---

## 1. Struktur Folder Framework

Semua berkas pengujian diisolasi sepenuhnya di dalam direktori `e2e-testing/` di root proyek:

```text
e2e-testing/
├── node_modules/                     # Dependensi Playwright & Cucumber (Selesai)
├── features/
│   └── stock_opname_e2e.feature      # Skenario BDD Gherkin (Selesai)
├── page_objects/
│   ├── BasePage.js                   # Class dasar POM (Selesai)
│   └── LoginPage.js                  # POM Login Halaman 1 (Selesai)
├── step_definitions/
│   └── auth_steps.js                 # Step Definitions Login Halaman 1 (Selesai)
├── support/
│   └── hooks.js                      # Browser Setup & Failure Screenshot (Selesai)
├── package.json                      # Manifest & Dependensi (Selesai)
└── cucumber.js                       # Konfigurasi Cucumber (Selesai)
```

---

## 2. Implementasi Kode

### A. package.json
Mendefinisikan seluruh dependensi pengujian otomatis dan skrip runner untuk kelompok.

```json
{
  "name": "opname-e2e-testing",
  "version": "1.0.0",
  "description": "E2E Testing for Auto Service Stock Opname App",
  "main": "index.js",
  "scripts": {
    "test": "cucumber-js",
    "report": "node support/reporter.js"
  },
  "keywords": [
    "testing",
    "playwright",
    "cucumber",
    "bdd",
    "pom"
  ],
  "author": "Janu, Fahim, Akmal, Hafidz",
  "license": "ISC",
  "devDependencies": {
    "@cucumber/cucumber": "^10.6.0",
    "playwright": "^1.42.1",
    "cucumber-html-reporter": "^7.2.0"
  }
}
```

---

### B. cucumber.js
Mengonfigurasi CucumberJS agar memetakan file fitur, step definitions, hooks, serta mengeluarkan laporan pengujian mentah berformat JSON.

```javascript
module.exports = {
  default: {
    paths: ["features/**/*.feature"],
    require: [
      "step_definitions/**/*.js",
      "support/**/*.js"
    ],
    formatOptions: {
      snippetInterface: "async-await"
    },
    format: [
      "summary",
      "json:reports/cucumber_report.json"
    ],
    parallel: 1,
    publishQuiet: true
  }
};
```

---

### C. hooks.js
Mengontrol siklus hidup browser Playwright sebelum dan sesudah pengujian, serta menyematkan tangkapan layar otomatis jika terjadi kegagalan langkah uji.

```javascript
const { Before, After, AfterStep, setDefaultTimeout } = require("@cucumber/cucumber");
const { chromium } = require("playwright");

setDefaultTimeout(30000);

Before(async function () {
  this.browser = await chromium.launch({
    headless: true,
    args: ["--no-sandbox", "--disable-setuid-sandbox"]
  });
  
  this.context = await this.browser.newContext({
    viewport: { width: 1280, height: 720 }
  });
  
  this.page = await this.context.newPage();
});

After(async function () {
  if (this.page) {
    await this.page.close();
  }
  if (this.context) {
    await this.context.close();
  }
  if (this.browser) {
    await this.browser.close();
  }
});

AfterStep(async function (stepResult) {
  if (stepResult.result.status === "FAILED") {
    if (this.page) {
      const screenshot = await this.page.screenshot({
        fullPage: true
      });
      this.attach(screenshot, "image/png");
    }
  }
});
```

---

### D. BasePage.js
Parent class Page Object Model yang membungkus fungsi API Playwright agar interaksi browser stabil dan tidak rentan terhadap delay rendering (anti-flakiness).

```javascript
class BasePage {
  constructor(page) {
    this.page = page;
    this.baseUrl = "http://localhost:3333";
  }

  async navigate(path) {
    await this.page.goto(`${this.baseUrl}${path}`, {
      waitUntil: "domcontentloaded"
    });
  }

  async waitForSelector(selector, timeout = 10000) {
    await this.page.waitForSelector(selector, {
      state: "visible",
      timeout
    });
  }

  async click(selector) {
    await this.waitForSelector(selector);
    await this.page.click(selector);
  }

  async fillInput(selector, text) {
    await this.waitForSelector(selector);
    await this.page.fill(selector, "");
    await this.page.fill(selector, text);
  }

  async getElementText(selector) {
    await this.waitForSelector(selector);
    return await this.page.textContent(selector);
  }

  async isElementVisible(selector, timeout = 5000) {
    try {
      await this.page.waitForSelector(selector, {
        state: "visible",
        timeout
      });
      return true;
    } catch (e) {
      return false;
    }
  }

  async wait(ms) {
    await this.page.waitForTimeout(ms);
  }
}

module.exports = BasePage;
```

---

### E. stock_opname_e2e.feature
Berkas spesifikasi skenario BDD menggunakan sintaks Gherkin untuk pengujian login.

```gherkin
Feature: End-to-End Stock Opname and Inventory Workflow
  As a warehouse staff / workshop admin,
  I want to be able to log in, search for spare parts, perform stock opname adjustments,
  and view the activity log history to ensure physical stock matches the system.

  Background:
    Given the user opens the Auto Service login page

  @negative @equivalence_partitioning
  Scenario: Failed login authentication with invalid credentials (EP)
    When the user enters email "wrong@service.com"
    And the user enters password "password123"
    And the user clicks the login button
    Then the user should see an error message "Kredensial tidak cocok"
```

---

### F. LoginPage.js
Class representasi halaman sign-in yang mewarisi class `BasePage` untuk memisahkan locator elemen halaman dari kode step definitions.

```javascript
const BasePage = require("./BasePage");

class LoginPage extends BasePage {
  constructor(page) {
    super(page);
    this.usernameInput = 'input[name="username"]';
    this.passwordInput = 'input[name="password"]';
    this.submitButton = 'button[type="submit"]';
    this.errorMessageSelector = 'p.text-red-500:visible';
  }

  async navigateToLogin() {
    await this.navigate("/auth/sign-in");
  }

  async fillUsername(username) {
    const cleanUsername = username.includes("@") ? username.split("@")[0] : username;
    await this.fillInput(this.usernameInput, cleanUsername);
  }

  async fillPassword(password) {
    await this.fillInput(this.passwordInput, password);
  }

  async clickLogin() {
    await this.click(this.submitButton);
  }

  async getErrorMessage() {
    await this.waitForSelector(this.errorMessageSelector);
    const errors = await this.page.locator(this.errorMessageSelector).allTextContents();
    const activeError = errors.map(e => e.trim()).find(e => e.length > 0);
    return activeError || "";
  }
}

module.exports = LoginPage;
```

---

### G. auth_steps.js
Implementasi kode _step definitions_ yang menghubungkan langkah-langkah Gherkin di berkas `.feature` dengan interaksi sesungguhnya pada `LoginPage` POM.

```javascript
const { Given, When, Then } = require("@cucumber/cucumber");
const assert = require("assert");
const LoginPage = require("../page_objects/LoginPage");

Given("the user opens the Auto Service login page", async function () {
  const loginPage = new LoginPage(this.page);
  await loginPage.navigateToLogin();
});

When("the user enters email {string}", async function (email) {
  const loginPage = new LoginPage(this.page);
  await loginPage.fillUsername(email);
});

When("the user enters password {string}", async function (password) {
  const loginPage = new LoginPage(this.page);
  await loginPage.fillPassword(password);
});

When("the user clicks the login button", async function () {
  const loginPage = new LoginPage(this.page);
  await loginPage.clickLogin();
});

Then("the user should see an error message {string}", async function (expectedErrorMessage) {
  const loginPage = new LoginPage(this.page);
  await loginPage.wait(1000);
  const actualErrorMessage = await loginPage.getErrorMessage();
  assert.strictEqual(actualErrorMessage.trim(), expectedErrorMessage.trim());
});
```

---

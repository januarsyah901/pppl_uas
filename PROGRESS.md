# Laporan Progress: Inisiasi Framework BDD + Cucumber + POM + Playwright
**Kelompok**: Janu, Fahim, Akmal, Hafidz

Semua infrastruktur dasar pengujian otomatis telah selesai diinisialisasi di dalam folder `e2e-testing/`. Dependensi pengujian juga telah berhasil terinstal 100% tanpa kendala di laptop bang jan.

---

## Tugas Janu

Janu bertanggung jawab untuk melakukan setup folder pengujian, membuat base class POM (BasePage.js), Hooks global, serta membuat POM, Step Definitions, dan rancangan Test Case untuk Halaman 1 (Auth / Login Page).

---

## 2. Struktur Folder Framework yang Terbentuk

Semua berkas pengujian diisolasi sepenuhnya di dalam folder `e2e-testing/` di root proyek:

```text
e2e-testing/
├── node_modules/                     # Berhasil diinstal via npm install (Selesai)
├── features/
│   └── stock_opname_e2e.feature      # Skenario BDD Gherkin (Selesai)
├── page_objects/
│   ├── BasePage.js                   # Class dasar POM (Selesai)
│   ├── LoginPage.js                  # POM Login (Bagian Janu)
│   ├── DashboardPage.js              # POM Dashboard (Bagian Fahim)
│   ├── InventoryPage.js              # POM Daftar Stok (Bagian Akmal)
│   ├── OpnameFormPage.js             # POM Form Opname (Bagian Akmal & Hafidz)
│   └── DetailPage.js                 # POM Detail & Logout (Bagian Hafidz)
├── step_definitions/
│   ├── auth_steps.js                 # Steps Login (Bagian Janu)
│   ├── dashboard_steps.js            # Steps Dashboard (Bagian Fahim)
│   └── inventory_steps.js            # Steps Stok & Opname (Bagian Akmal & Hafidz)
├── support/
│   ├── hooks.js                      # Browser Setup & Failure Screenshot (Selesai)
│   └── reporter.js                   # Konfigurasi Laporan HTML Otomatis (Bagian Hafidz)
├── package.json                      # Manifest & Dependensi (Selesai)
└── cucumber.js                       # Konfigurasi Cucumber (Selesai)
```

---

## 3. Detail Berkas Kode yang Telah Dibuat

### A. `package.json`
Mendefinisikan pustaka otomatisasi pengujian serta skrip perintah eksekusi tes.

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
*(Status: **Selesai & Terinstal 100% via `npm install`**)*

---

### B. `cucumber.js`
Mengatur lokasi pencarian berkas fitur Gherkin, pendaftaran file pendukung, serta format output data mentah laporan.

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
*(Status: **Selesai**)*

---

### C. `hooks.js`
Mengatur siklus hidup browser Chromium menggunakan Playwright sebelum/sesudah pengetesan, serta menangkap screenshot otomatis jika ada langkah yang gagal.

```javascript
const { Before, After, AfterStep, setDefaultTimeout } = require("@cucumber/cucumber");
const { chromium } = require("playwright");

// Set default timeout pengujian menjadi 30 detik
setDefaultTimeout(30000);

// Hook yang dijalankan sebelum setiap skenario dimulai
Before(async function () {
  // Buka browser Chromium
  this.browser = await chromium.launch({
    headless: true,
    args: ["--no-sandbox", "--disable-setuid-sandbox"]
  });
  
  // Buat context browser baru (untuk isolasi data cookie/session antar skenario)
  this.context = await this.browser.newContext({
    viewport: { width: 1280, height: 720 }
  });
  
  // Buat halaman/tab baru
  this.page = await this.context.newPage();
});

// Hook yang dijalankan setelah setiap skenario selesai
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

// Hook yang dijalankan setelah setiap langkah (step) selesai untuk menangkap screenshot jika gagal
AfterStep(async function (stepResult) {
  // Jika status langkah adalah FAILED (Gagal), tangkap screenshot
  if (stepResult.result.status === "FAILED") {
    if (this.page) {
      const screenshot = await this.page.screenshot({
        fullPage: true
      });
      // Lampirkan screenshot ke dalam laporan JSON Cucumber
      this.attach(screenshot, "image/png");
    }
  }
});
```
*(Status: **Selesai**)*

---

### D. `BasePage.js`
Pustaka utility yang membungkus fungsi API Playwright agar penulisan class halaman oleh anggota tim lainnya menjadi lebih ringkas dan tahan terhadap delay halaman (*anti-flakiness*).

```javascript
class BasePage {
  /**
   * @param {import('playwright').Page} page
   */
  constructor(page) {
    this.page = page;
    this.baseUrl = "http://localhost:3333"; // Mengarah ke fe-opname Next.js
  }

  // Navigasi ke path relatif terhadap baseUrl
  async navigate(path) {
    await this.page.goto(`${this.baseUrl}${path}`, {
      waitUntil: "domcontentloaded"
    });
  }

  // Menunggu elemen terlihat di halaman
  async waitForSelector(selector, timeout = 10000) {
    await this.page.waitForSelector(selector, {
      state: "visible",
      timeout
    });
  }

  // Melakukan klik pada elemen yang dituju setelah menunggu elemen siap
  async click(selector) {
    await this.waitForSelector(selector);
    await this.page.click(selector);
  }

  // Mengisi data input setelah dibersihkan terlebih dahulu
  async fillInput(selector, text) {
    await this.waitForSelector(selector);
    await this.page.fill(selector, "");
    await this.page.fill(selector, text);
  }

  // Mengambil teks dari elemen
  async getElementText(selector) {
    await this.waitForSelector(selector);
    return await this.page.textContent(selector);
  }

  // Memeriksa apakah elemen terlihat di halaman tanpa memicu error jika tidak ada
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

  // Utility untuk menunggu dalam hitungan milidetik
  async wait(ms) {
    await this.page.waitForTimeout(ms);
  }
}

module.exports = BasePage;
```
*(Status: **Selesai**)*

---

### E. `stock_opname_e2e.feature`
Skenario BDD dalam format standar bahasa Inggris (Given, When, Then, Scenario, Feature) yang mendefinisikan kasus uji positif E2E (melewati 5 halaman) dan negatif (untuk Equivalence Partitioning dan Boundary Value Analysis).

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

  @positive @boundary_value_analysis @end_to_end
  Scenario: Complete end-to-end stock opname workflow (E2E)
    # Page 1: Login
    When the user enters email "admin@service.com"
    And the user enters password "password123"
    And the user clicks the login button
    
    # Page 2: Dashboard
    Then the user should be redirected to the Dashboard page
    And the user should see the stock summary widgets on the Dashboard
    When the user clicks the "Inventori Stok" menu in the sidebar
    
    # Page 3: Stock Inventory List
    Then the user should be redirected to the Stock List page
    And the user should see the spare parts inventory table
    When the user searches for "Kampas Rem"
    And the user clicks the "Opname" action button for that item
    
    # Page 4: Stock Opname Form
    Then the user should be redirected to the Stock Opname Form page
    And the user should see the item name "Kampas Rem" displayed on the form
    When the user enters physical quantity "15"
    And the user enters notes "Penyesuaian stok fisik rutin bulanan Mei 2026"
    And the user clicks the "Simpan Opname" button
    
    # Page 5: Detail & Log Opname
    Then the user should see a success notification "Opname berhasil disimpan"
    And the user should be redirected to the Opname History Log page
    And the user should see the latest log entry with quantity "15" and notes "Penyesuaian stok fisik rutin bulanan Mei 2026"
    
    # Logout Session
    When the user clicks the "Logout" button in the sidebar
    Then the user should be redirected back to the Auto Service login page
```
*(Status: **Selesai**)*

---

## 4. Langkah Selanjutnya (Ready to Code POM & Steps)

Kerangka ini sudah sepenuhnya siap dipakai. Anggota tim dapat langsung membuat file di laptop masing-masing pada folder `page_objects/` dan `step_definitions/` sesuai dengan rancangan ini.

Untuk melihat visualisasi lengkap rancangan tes kelompok, silakan buka berkas rancangan di `TEST_PLANNING.md`.

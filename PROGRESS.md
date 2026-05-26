# Laporan Progress: Inisiasi Framework BDD + Cucumber + POM + Playwright

**Nama**: Januarsyah Akbar (24/535846/SV/24314)\
**Kelompok**: Janu, Fahim, Akmal, Hafidz
---

## Pembagian Tugas

### Detail Distribusi Pekerjaan:

#### Janu — *Automation QA Engineer 1*
*   **Tanggung Jawab**:
    *   Menginisialisasi folder framework pengujian (`e2e-testing/` project) dan membuat fondasi base class POM (`BasePage.js`) serta Hooks global.
    *   Merancang test case (BVA/EP) & mengimplementasikan POM dan Step Definitions untuk **Halaman 1 (Auth / Login Page)**.
*   **Target Output File**:
    *   `e2e-testing/page_objects/BasePage.js` & `e2e-testing/support/hooks.js`
    *   `e2e-testing/page_objects/LoginPage.js`
    *   `e2e-testing/step_definitions/auth_steps.js`

#### Fahim — *Automation QA Engineer 2*
*   **Tanggung Jawab**:
    *   Mengonfigurasi berkas integrasi runner (`package.json`, `cucumber.js`) dan menulis spesifikasi skenario BDD Gherkin global (`.feature`).
    *   Merancang test case (BVA/EP) & mengimplementasikan POM dan Step Definitions untuk **Halaman 2 (Dashboard / Home Page)**.
*   **Target Output File**:
    *   `e2e-testing/features/stock_opname_e2e.feature`
    *   `e2e-testing/page_objects/DashboardPage.js`
    *   `e2e-testing/step_definitions/dashboard_steps.js`

#### Akmal — *Automation QA Engineer 3*
*   **Tanggung Jawab**:
    *   Merancang test case (BVA/EP) & mengimplementasikan POM dan Step Definitions untuk **Halaman 3 (Daftar Stok Barang / Inventory Page)** dan **Halaman 4 (Form Stock Opname Page - Input Bagian 1)**.
*   **Target Output File**:
    *   `e2e-testing/page_objects/InventoryPage.js`
    *   `e2e-testing/step_definitions/inventory_steps.js`

#### Hafidz — *Automation QA Engineer 4*
*   **Tanggung Jawab**:
    *   Mengonfigurasi berkas *Automated HTML Report* (`reporter.js`) dan menyusun berkas laporan *bug* (`BUG_REPORTING.md`).
    *   Merancang test case (BVA/EP) & mengimplementasikan POM dan Step Definitions untuk **Halaman 4 (Form Stock Opname Page - Aksi/Submit Bagian 2)** dan **Halaman 5 (Detail/Konfirmasi Opname & Log Activity & Logout)**.
*   **Target Output File**:
    *   `e2e-testing/page_objects/DetailPage.js` & `e2e-testing/page_objects/OpnameFormPage.js`
    *   `e2e-testing/step_definitions/detail_steps.js`
    *   `e2e-testing/support/reporter.js` & `e2e-testing/reports/BUG_REPORTING.md`

---


Janu bertanggung jawab untuk melakukan setup folder pengujian, membuat base class POM (BasePage.js), Hooks global, serta membuat POM, Step Definitions, dan rancangan Test Case untuk Halaman 1 (Auth / Login Page).

---

## 1. Struktur Folder Framework yang Terbentuk

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

## 2. Detail Berkas Kode yang Telah Dibuat

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


---



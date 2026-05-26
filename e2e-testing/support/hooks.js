const { Before, After, AfterStep, setDefaultTimeout } = require("@cucumber/cucumber");
const { chromium } = require("playwright");

// Set default timeout pengujian menjadi 30 detik
setDefaultTimeout(30000);

// Hook yang dijalankan sebelum setiap skenario dimulai
Before(async function () {
  // Buka browser Chromium (ganti headless: false jika ingin melihat browser visual saat debugging)
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

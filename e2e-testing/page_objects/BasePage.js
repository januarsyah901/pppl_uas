class BasePage {
  /**
   * @param {import('playwright').Page} page
   */
  constructor(page) {
    this.page = page;
    this.baseUrl = "http://localhost:3333"; // URL default dari fe-opname
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

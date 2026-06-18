const BasePage = require("./BasePage");

class InventoryPage extends BasePage {
  constructor(page) {
    super(page);
    this.startSessionBtn = 'button:has-text("Mulai Sesi Opname Baru")';
    this.continueSessionBtn = 'button:has-text("Lanjutkan Opname")';
    this.confirmStartBtn = 'button:has-text("Mulai Sekarang")';
    this.opnameTable = ".w-full.flex.flex-col"; // DataTable container
  }

  // Cek apakah user diarahkan ke halaman Stok Opname
  async isOpnamePage() {
    await this.page.waitForURL("**/inventori/opname*", { timeout: 15000 });
    return this.page.url().includes("/inventori/opname");
  }

  // Mulai sesi opname baru (atau lanjutkan jika sudah ada yang berjalan)
  async startOrContinueOpnameSession() {
    const isContinueVisible = await this.isElementVisible(this.continueSessionBtn, 2000);
    if (isContinueVisible) {
      await this.click(this.continueSessionBtn);
    } else {
      await this.click(this.startSessionBtn);
      // Konfirmasi di modal
      await this.click(this.confirmStartBtn);
    }
  }
}

module.exports = InventoryPage;

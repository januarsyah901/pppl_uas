/**
 * @file DashboardPage.js
 * @description Page Object Model for Dashboard Page
 * @author Fahim (QA Engineer 2)
 */

const BasePage = require("./BasePage");

class DashboardPage extends BasePage {
  constructor(page) {
    super(page);
    // Selector untuk memverifikasi halaman dashboard
    this.statCardsGrid = ".grid.gap-4";
    this.sidebar = "aside";
    this.logoutButton = 'a[title="Keluar"], a:has-text("Keluar")';
  }

  // Menunggu halaman dashboard dimuat sepenuhnya
  async isDashboardLoaded() {
    await this.waitForSelector(this.statCardsGrid);
    return await this.isElementVisible(this.sidebar);
  }

  // Navigasi ke menu "Stok Opname" melalui sidebar
  async navigateToStockOpname() {
    const katalogSparepartBtn = this.page.locator('button:has-text("Katalog Sparepart")');
    const stokOpnameLink = this.page.locator('a[href="/inventori/opname"]');

    // Jika menu "Katalog Sparepart" belum diekspansi, klik terlebih dahulu
    if (await katalogSparepartBtn.isVisible()) {
      if (!(await stokOpnameLink.isVisible())) {
        await katalogSparepartBtn.click();
      }
    }

    // Klik sub-menu "Stok Opname"
    await stokOpnameLink.waitFor({ state: "visible" });
    await stokOpnameLink.click();
  }

  // Klik tombol Logout di sidebar
  async clickLogout() {
    // Scroll ke bawah sidebar jika tombol tertutup
    const logoutBtn = this.page.locator(this.logoutButton);
    await logoutBtn.scrollIntoViewIfNeeded();
    await logoutBtn.click();
  }
}

module.exports = DashboardPage;

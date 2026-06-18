/**
 * @file DetailPage.js
 * @description Page Object Model for Stock Opname detail modal and history logs
 * @author Hafidz (QA Engineer 4)
 */

const BasePage = require("./BasePage");

class DetailPage extends BasePage {
  constructor(page) {
    super(page);
    this.successToast = 'div:has-text("Sesi opname ditutup")';
    this.historyTable = ".w-full.flex.flex-col table";
    this.detailModalTitle = 'h3:has-text("Detail Opname")';
    this.viewDetailBtn = 'button[title="Lihat Detail"]';
  }

  // Menunggu notifikasi sukses muncul
  async verifySuccessNotification() {
    // Menunggu toast react-hot-toast/notify muncul
    const toast = this.page.locator('div[role="status"], div:has-text("Sesi opname")').first();
    await toast.waitFor({ state: "visible", timeout: 10000 });
    return await toast.isVisible();
  }

  // Membuka modal detail sesi opname terbaru (paling atas di tabel riwayat)
  async openLatestOpnameDetail() {
    // Cari tombol aksi "Lihat Detail" pertama di tabel riwayat
    const firstViewBtn = this.page.locator(this.viewDetailBtn).first();
    await firstViewBtn.waitFor({ state: "visible", timeout: 15000 });
    await firstViewBtn.click();
  }

  // Memverifikasi isi data item di dalam modal detail
  async verifyDetailItem(itemName, expectedQuantity, expectedNotes) {
    // Pastikan modal detail terbuka
    await this.page.waitForSelector(this.detailModalTitle);
    
    // Temukan baris tabel di modal untuk item tertentu
    const row = this.page.locator("tr").filter({ has: this.page.locator("p", { hasText: itemName }) });
    await row.waitFor({ state: "visible" });
    
    // Ambil kolom kuantitas aktual (kolom ke-3) dan catatan (kolom ke-5)
    // Berdasarkan markup, urutan kolom: Nama Item (1), Sistem (2), Aktual (3), Selisih (4), Catatan (5)
    const physicalCell = row.locator("td").nth(2);
    const notesCell = row.locator("td").nth(4);
    
    const actualQuantity = await physicalCell.textContent();
    const actualNotes = await notesCell.textContent();
    
    return {
      quantityMatches: actualQuantity.trim() === expectedQuantity.toString(),
      notesMatches: actualNotes.trim().includes(expectedNotes) || actualNotes.trim() === expectedNotes
    };
  }

  // Menutup modal detail
  async closeDetailModal() {
    const closeBtn = this.page.locator('button:has-text("Batal"), button[aria-label="Close"], svg').first();
    await closeBtn.click();
  }
}

module.exports = DetailPage;

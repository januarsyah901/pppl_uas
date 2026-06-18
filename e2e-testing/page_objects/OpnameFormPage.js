const BasePage = require("./BasePage");

class OpnameFormPage extends BasePage {
  constructor(page) {
    super(page);
    this.modalTitleSelector = 'h3:has-text("Sesi Opname")';
    this.submitButton = 'button:has-text("Tutup Sesi & Apply")';
  }

  // Cek apakah modal Form Opname sudah terlihat
  async isFormVisible() {
    return await this.isElementVisible(this.modalTitleSelector);
  }

  // Cek apakah suatu item (misalnya "Kampas Rem") ada di dalam form modal
  async isItemDisplayed(itemName) {
    const itemLocator = this.page.locator(`tr:has-text("${itemName}")`);
    return await itemLocator.isVisible();
  }

  // Mengisi jumlah fisik aktual untuk item tertentu
  async enterPhysicalQuantity(itemName, quantity) {
    const row = this.page.locator("tr").filter({ has: this.page.locator("p", { hasText: itemName }) });
    const inputField = row.locator('input[type="number"]');
    await inputField.waitFor({ state: "visible" });
    await inputField.fill(quantity.toString());
  }

  // Mengisi catatan untuk item tertentu
  async enterNotes(itemName, notesText) {
    const row = this.page.locator("tr").filter({ has: this.page.locator("p", { hasText: itemName }) });
    const notesField = row.locator('input[type="text"]');
    await notesField.waitFor({ state: "visible" });
    await notesField.fill(notesText);
  }

  // Klik tombol simpan opname (Tutup Sesi & Apply)
  async clickSubmitOpname() {
    await this.click(this.submitButton);
  }
}

module.exports = OpnameFormPage;

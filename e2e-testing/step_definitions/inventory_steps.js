/**
 * @file inventory_steps.js
 * @description Step Definitions for Dashboard and Inventory Stock Opname Form (EP/BVA input)
 * @author Akmal (QA Engineer 3)
 */

const { When, Then } = require("@cucumber/cucumber");
const assert = require("assert");
const DashboardPage = require("../page_objects/DashboardPage");
const InventoryPage = require("../page_objects/InventoryPage");
const OpnameFormPage = require("../page_objects/OpnameFormPage");

Then("the user should be redirected to the Dashboard page", async function () {
  const dashboardPage = new DashboardPage(this.page);
  const isLoaded = await dashboardPage.isDashboardLoaded();
  assert.strictEqual(isLoaded, true, "Dashboard gagal dimuat");
});

Then("the user should see the stock summary widgets on the Dashboard", async function () {
  const dashboardPage = new DashboardPage(this.page);
  // Verifikasi setidaknya ada satu stat card yang muncul
  const isWidgetVisible = await dashboardPage.isElementVisible(dashboardPage.statCardsGrid);
  assert.strictEqual(isWidgetVisible, true, "Widget statistik dashboard tidak terlihat");
});

When("the user clicks the {string} menu in the sidebar", async function (menuName) {
  const dashboardPage = new DashboardPage(this.page);
  if (menuName === "Inventori Stok" || menuName === "Stok Opname") {
    await dashboardPage.navigateToStockOpname();
  } else {
    // Fallback click by text
    const sidebarLink = this.page.locator(`aside a:has-text("${menuName}")`);
    await sidebarLink.click();
  }
});

Then("the user should be redirected to the Stock List page", async function () {
  const inventoryPage = new InventoryPage(this.page);
  const isOpnamePage = await inventoryPage.isOpnamePage();
  assert.strictEqual(isOpnamePage, true, "Gagal mengarahkan ke halaman Stok Opname");
});

Then("the user should see the spare parts inventory table", async function () {
  const inventoryPage = new InventoryPage(this.page);
  const isTableVisible = await inventoryPage.isElementVisible(inventoryPage.opnameTable);
  assert.strictEqual(isTableVisible, true, "Tabel riwayat stok opname tidak ditemukan");
});

When("the user searches for {string}", async function (itemName) {
  // Simpan nama item ke context untuk digunakan pada step berikutnya
  this.targetItemName = itemName;
  // Di halaman opname, pencarian di tabel riwayat mencari sesi.
  // Kita tidak perlu memfilter tabel riwayat di sini karena data item diinput di dalam modal opname.
  await this.page.waitForTimeout(500);
});

When("the user clicks the {string} action button for that item", async function (actionName) {
  const inventoryPage = new InventoryPage(this.page);
  if (actionName === "Opname") {
    // Mulai sesi opname baru atau lanjutkan yang sudah ada
    await inventoryPage.startOrContinueOpnameSession();
  }
});

Then("the user should be redirected to the Stock Opname Form page", async function () {
  const opnameFormPage = new OpnameFormPage(this.page);
  const isVisible = await opnameFormPage.isFormVisible();
  assert.strictEqual(isVisible, true, "Modal Form Sesi Opname tidak muncul");
});

Then("the user should see the item name {string} displayed on the form", async function (itemName) {
  const opnameFormPage = new OpnameFormPage(this.page);
  const isItemDisplayed = await opnameFormPage.isItemDisplayed(itemName);
  assert.strictEqual(isItemDisplayed, true, `Item "${itemName}" tidak ditemukan di form opname`);
});

When("the user enters physical quantity {string}", async function (quantity) {
  const opnameFormPage = new OpnameFormPage(this.page);
  const itemName = this.targetItemName || "Kampas Rem";
  await opnameFormPage.enterPhysicalQuantity(itemName, parseInt(quantity));
});

When("the user enters notes {string}", async function (notes) {
  const opnameFormPage = new OpnameFormPage(this.page);
  const itemName = this.targetItemName || "Kampas Rem";
  await opnameFormPage.enterNotes(itemName, notes);
});

When(/^the user clicks the "([^"]*)" button(?: in the sidebar)?$/, async function (buttonName) {
  const opnameFormPage = new OpnameFormPage(this.page);
  const dashboardPage = new DashboardPage(this.page);

  if (buttonName === "Simpan Opname" || buttonName === "Tutup Sesi & Apply" || buttonName === "Simpan Opname") {
    await opnameFormPage.clickSubmitOpname();
  } else if (buttonName === "Logout" || buttonName === "Keluar") {
    await dashboardPage.clickLogout();
  } else {
    // Fallback generic click
    await this.page.click(`button:has-text("${buttonName}")`);
  }
});

/**
 * @file detail_steps.js
 * @description Step Definitions for Stock Opname confirmation detail logs and session logout
 * @author Hafidz (QA Engineer 4)
 */

const { Then } = require("@cucumber/cucumber");
const assert = require("assert");
const DetailPage = require("../page_objects/DetailPage");
const LoginPage = require("../page_objects/LoginPage");

Then("the user should see a success notification {string}", async function (expectedMessage) {
  const detailPage = new DetailPage(this.page);
  const isNotificationVisible = await detailPage.verifySuccessNotification();
  assert.strictEqual(isNotificationVisible, true, `Notifikasi sukses tidak muncul. Ekspektasi: ${expectedMessage}`);
});

Then("the user should be redirected to the Opname History Log page", async function () {
  const detailPage = new DetailPage(this.page);
  await this.page.waitForURL("**/inventori/opname*", { timeout: 10000 });
  const url = this.page.url();
  assert.strictEqual(url.includes("/inventori/opname"), true, "Tidak diarahkan ke halaman riwayat opname");
});

Then("the user should see the latest log entry with quantity {string} and notes {string}", async function (quantity, notes) {
  const detailPage = new DetailPage(this.page);
  const itemName = this.targetItemName || "Kampas Rem";

  // Membuka modal detail sesi terbaru
  await detailPage.openLatestOpnameDetail();

  // Verifikasi detail item di dalam modal
  const verification = await detailPage.verifyDetailItem(itemName, parseInt(quantity), notes);

  assert.strictEqual(verification.quantityMatches, true, `Kuantitas aktual di log detail tidak cocok. Ekspektasi: ${quantity}`);
  assert.strictEqual(verification.notesMatches, true, `Catatan di log detail tidak cocok. Ekspektasi mengandung: "${notes}"`);

  // Tutup modal kembali setelah verifikasi selesai
  await detailPage.closeDetailModal();
});

Then("the user should be redirected back to the Auto Service login page", async function () {
  const loginPage = new LoginPage(this.page);
  await this.page.waitForURL("**/auth/sign-in*", { timeout: 15000 });
  const isLoginInputVisible = await loginPage.isElementVisible(loginPage.usernameInput);
  assert.strictEqual(isLoginInputVisible, true, "Tidak dialihkan kembali ke Halaman Login");
});

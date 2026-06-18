/**
 * @file LoginPage.js
 * @description Page Object Model for Auth / Login Page
 * @author Januarsyah Akbar (QA Engineer 1)
 */

const BasePage = require("./BasePage");

class LoginPage extends BasePage {
  constructor(page) {
    super(page);
    this.usernameInput = 'input[name="username"]';
    this.passwordInput = 'input[name="password"]';
    this.submitButton = 'button[type="submit"]';
    this.errorMessageSelector = 'p.text-red-500:visible';
  }

  // Navigasi ke halaman sign-in
  async navigateToLogin() {
    await this.navigate("/auth/sign-in");
  }

  // Mengisi field username
  async fillUsername(username) {
    // Jika input Gherkin berupa email (e.g. "admin@service.com"), kita ambil username saja (e.g. "admin")
    const cleanUsername = username.includes("@") ? username.split("@")[0] : username;
    await this.fillInput(this.usernameInput, cleanUsername);
  }

  // Mengisi field password
  async fillPassword(password) {
    await this.fillInput(this.passwordInput, password);
  }

  // Mengklik tombol login submit
  async clickLogin() {
    await this.click(this.submitButton);
  }

  // Mengambil teks pesan error yang aktif (bukan sekadar spasi)
  async getErrorMessage() {
    await this.waitForSelector(this.errorMessageSelector);
    const errors = await this.page.locator(this.errorMessageSelector).allTextContents();
    const activeError = errors.map(e => e.trim()).find(e => e.length > 0);
    return activeError || "";
  }
}

module.exports = LoginPage;

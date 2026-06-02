const { Given, When, Then } = require("@cucumber/cucumber");
const assert = require("assert");
const LoginPage = require("../page_objects/LoginPage");

Given("the user opens the Auto Service login page", async function () {
  const loginPage = new LoginPage(this.page);
  await loginPage.navigateToLogin();
});

When("the user enters email {string}", async function (email) {
  const loginPage = new LoginPage(this.page);
  await loginPage.fillUsername(email);
});

When("the user enters password {string}", async function (password) {
  const loginPage = new LoginPage(this.page);
  await loginPage.fillPassword(password);
});

When("the user clicks the login button", async function () {
  const loginPage = new LoginPage(this.page);
  await loginPage.clickLogin();
});

Then("the user should see an error message {string}", async function (expectedErrorMessage) {
  const loginPage = new LoginPage(this.page);
  // Tunggu sedikit agar proses otentikasi palsu/asli di frontend selesai memunculkan error
  await loginPage.wait(1000);
  const actualErrorMessage = await loginPage.getErrorMessage();
  assert.strictEqual(actualErrorMessage.trim(), expectedErrorMessage.trim());
});

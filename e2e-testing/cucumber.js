module.exports = {
  default: {
    paths: ["features/**/*.feature"],
    require: [
      "step_definitions/**/*.js",
      "support/**/*.js"
    ],
    formatOptions: {
      snippetInterface: "async-await"
    },
    format: [
      "summary",
      "json:reports/cucumber_report.json"
    ],
    parallel: 1,
    publishQuiet: true
  }
};

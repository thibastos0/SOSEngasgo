const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: "https://sosengasgo.onrender.com",
    baseUrl: "https://sosengasgo.onrender.com/login",
    video: false,
    supportFile: false
  }
});

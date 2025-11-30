// ***********************************************************
// This support file is loaded before test files and
// can be used to configure global behavior for Cypress.
// ***********************************************************

// Import commands.js if you add custom commands later
// import './commands'

// Alternatively you can use CommonJS syntax:
// require('./commands')

// Prevent Cypress from failing tests on uncaught exceptions
// (useful for third-party code that might throw errors)
Cypress.on('uncaught:exception', (err, runnable) => {
  // returning false here prevents Cypress from failing the test
  return false
})

// Smoke tests básicos para validar o workflow do GitHub Actions
// Mantém verificações mínimas e estáveis sem dependências de dados

describe("Smoke: aplicação carrega", () => {
  it("abre a página de login", () => {
    cy.visit("/login");
    // Verifica que a página renderizou algum conteúdo básico
    cy.contains("Login", { matchCase: false });
  });
});

describe("Smoke: elementos essenciais do login existem", () => {
  it("tem campos de email e senha e botão de enviar", () => {
    cy.visit("/login");
    cy.get("input#username").should("exist");
    cy.get("input#password").should("exist");
    cy.get("button[type='submit']").should("exist");
  });
});

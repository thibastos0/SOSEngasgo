# SOSEngasgo

Projeto Integrador (3º semestre) – Aplicação Spring Boot para gestão e suporte a eventos de engasgo em ambiente escolar.

## Sumário
- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Setup Rápido](#setup-rápido)
- [Execução](#execução)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Principais Tecnologias](#principais-tecnologias)
- [Fluxo de Autenticação](#fluxo-de-autenticação)
- [Padrões e Convenções](#padrões-e-convenções)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Testes](#testes)
- [Roadmap / Melhorias Futuras](#roadmap--melhorias-futuras)
- [Documento de Segurança / Spring Security](#documento-de-segurança--spring-security)

## Visão Geral
O sistema provê uma interface de **acionamento de emergência** (`/emergencia`) acessível a todos os usuários autenticados, permitindo notificação rápida em casos de engasgo. Uma área de **gestão** (`/gestao/**`) é restrita a usuários com perfil `ROLE_GESTOR`, incluindo cadastro/edição de usuários, dashboard e componentes auxiliares (relatórios, alertas). A segurança foi projetada para: senhas com *BCrypt*, filtro JSON de login customizado e proteção CSRF em formulários.

## Arquitetura
Camadas principais:
- **Web / View**: Thymeleaf + Bootstrap + WebJars. Navbar reutilizável via fragmentos Thymeleaf com controle de acesso baseado em roles.
- **Segurança**: Spring Security 6 com filtro custom `JsonUsernamePasswordAuthFilter` para login via JSON.
- **Serviços / Domínio**: `UsuarioDetailsService` e serviços futuros para módulos (alertas, auditoria, etc.).
- **Persistência**: MongoDB (Spring Data). Repositórios simples com métodos derivados (ex: `findByContatoUsuarioEmail`).

## Pré-requisitos
- JDK 17
- (Opcional) Docker e MongoDB local ou Atlas (URI via `.env`)
- Git

## Setup Rápido
```bash
# Clonar
git clone https://github.com/thibastos0/SOSEngasgo.git
cd SOSEngasgo

# Rodar diretamente com Maven Wrapper
./mvnw spring-boot:run
```
Access: http://localhost:8080

## Execução
Ambiente de desenvolvimento: basta rodar `spring-boot:run`. Em produção gerar JAR:
```bash
./mvnw clean package
java -jar target/SOSEngasgo-0.0.1-SNAPSHOT.jar
```

## Estrutura de Pastas
```
src/main/java/com/example/SOSEngasgo/
  config/               -> Configuração de segurança (WebConfig)
  controller/           -> Controllers (EmergenciaController, GestaoController, UsuarioController)
  security/             -> Filtros e classes de autenticação (JsonUsernamePasswordAuthFilter, UsuarioDetails)
  service/              -> Serviços (UsuarioDetailsService)
  model/                -> Entidades de domínio
  repository/           -> Repositórios MongoDB
  ...
src/main/resources/
  templates/
    fragments/          -> Fragmentos reutilizáveis (navbar.html)
    gestao/             -> Páginas de gestão (dashboard, usuários)
    emergencia.html     -> Página de acionamento de emergência
    login.html          -> Página de login
  static/
    css/                -> Estilos (emergencia.css, gestao.css, styles.css)
    js/                 -> Scripts (autenticar.js, usuario.js, usuarios-form.js, emergencia.js)
  application.properties -> Configurações padrão
```

## Principais Tecnologias
- Spring Boot 3.5.x
- Spring Data MongoDB
- Spring Security 6
- Thymeleaf (com Thymeleaf Spring Security extras para `sec:authorize`)
- Bootstrap 5 / Font Awesome / jQuery via WebJars
- BCrypt para hash de senhas
- Dotenv (java-dotenv) para variáveis em desenvolvimento

## Fluxo de Autenticação
1. Usuário envia POST JSON para `/autenticacao` com `{ "contatoUsuario": { "email": "..." }, "senha": "..." }`.
2. Filtro `JsonUsernamePasswordAuthFilter` extrai credenciais e delega ao `AuthenticationManager`.
3. `DaoAuthenticationProvider` carrega usuário via `UsuarioDetailsService` (buscar por email) e valida senha BCrypt.
4. Em sucesso, resposta `200 Login ok`; sessão HTTP mantém `SecurityContext` (graças a `requireExplicitSave(false)`).
5. Cliente redireciona para `/emergencia` (página principal).
6. Usuários com `ROLE_GESTOR` veem link "Gestão" no navbar para acessar `/gestao/**`.
7. Logout via formulário POST para `/logout` inclui token CSRF.

Mais detalhes em [`SPRING_SECURITY.md`](SPRING_SECURITY.md).

### CSRF em requisições AJAX (fetch)
- O login JSON em `/autenticacao` NÃO exige CSRF (rota ignorada pelo CSRF).
- Todas as demais operações de escrita (POST/PUT/DELETE) exigem enviar o token CSRF no cabeçalho.

Como disponibilizamos o token nos templates:

```html
<meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}"/>
```

Como enviar o token no fetch:

```js
const token = document.querySelector('meta[name="_csrf"]').content;
await fetch('/gestao/usuarios/salvar', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'X-CSRF-TOKEN': token
  },
  body: JSON.stringify(payload)
});
```

## Funcionalidades Principais

### Acionamento de Emergência
- **Rota**: `/emergencia`
- **Acesso**: Todos usuários autenticados
- **Descrição**: Página com botão centralizado para acionamento imediato em casos de engasgo
- **Features**:
  - Design responsivo com destaque visual (gradiente, animação pulsante)
  - Confirmação antes do acionamento
  - Prevenção de múltiplos cliques (debounce automático)
  - Placeholder para integração com API de notificações
  - Navbar com navegação contextual (Emergência/Gestão/Perfil)

### Área de Gestão
- **Rota**: `/gestao/**`
- **Acesso**: Apenas `ROLE_GESTOR`
- **Módulos**:
  - Dashboard com estatísticas
  - Cadastro/edição de usuários
  - Relatórios (planejado)
  - Alertas e auditoria (planejado)
- **Navegação**: Sidebar + navbar com acesso bidirecional à emergência

## Padrões e Convenções
- Senhas nunca em texto puro: armazenar resultado de `BCryptPasswordEncoder`.
- Templates não incluem CSS/JS inline (preferir arquivos externos em `static/`).
- Navbar reutilizável via fragmentos Thymeleaf (`fragments/navbar.html`).
- Controle de exibição condicional usando `sec:authorize` (ex: mostrar link "Gestão" apenas para gestores).
- Selects com valores controlados por enums/backend (planejado).
- Código evita `findAll()` para localizar usuário por email (consulta derivada específica).

## Variáveis de Ambiente
Uso de `java-dotenv` para carregar `.env` em desenvolvimento.
Exemplo `.env`:
```
MONGODB_URI=mongodb://localhost:27017/sosengasgo
APP_ENV=dev
```
Adicionar mais conforme necessidade.

## Testes
Executar:
```bash
./mvnw test
```
Testes iniciais (placeholder) em `SosEngasgoApplicationTests`; expandir com testes de serviços e segurança (ex: testes de acesso negado / permitido).

## Roadmap / Melhorias Futuras
- [ ] Implementar backend de acionamento de emergência (endpoint REST `/api/emergencia/acionar`).
- [ ] Sistema de notificações push/email para responsáveis em emergências.
- [ ] Histórico de acionamentos com visualização para gestores.
- [ ] Geolocalização ao acionar emergência (se aplicável).
- [ ] Respostas de login em JSON estruturado (ex: `{ "status":"OK", "roles":["GESTOR"] }`).
- [ ] Endpoints REST para listar domínios (perfis, tipos, faixas etárias).
- [ ] Validação avançada de telefone + máscara.
- [ ] Auditoria / trilha de ações de usuários.
- [ ] Testes de integração para filtro de autenticação e controle de acesso.
- [ ] Sanitização / normalização de campos (ex: email lower-case).
- [ ] Autocomplete de escolas (substituir campo texto `escolaId`).

## Documento de Segurança / Spring Security
Ver arquivo separado: [`SPRING_SECURITY.md`](SPRING_SECURITY.md)

---
Qualquer dúvida ou sugestão de melhoria, abrir issue no repositório.

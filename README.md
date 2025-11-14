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
O sistema provê uma interface de gestão (rotas `/gestao/**`) acessível apenas a usuários com perfil adequado (ex: `ROLE_GESTOR`). Inclui cadastro/edição de usuários, dashboard e componentes auxiliares (relatórios, alertas). A segurança foi projetada para: senhas com *BCrypt*, filtro JSON de login customizado e proteção CSRF em formulários.

## Arquitetura
Camadas principais:
- **Web / View**: Thymeleaf + Bootstrap + WebJars.
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
  security/             -> Filtros e classes de autenticação (JsonUsernamePasswordAuthFilter, UsuarioDetails)
  service/              -> Serviços (UsuarioDetailsService)
  ...
src/main/resources/
  templates/            -> Páginas Thymeleaf
  static/js/            -> Scripts (autenticar.js, usuario.js, usuarios-form.js)
  application.properties -> Configurações padrão
```

## Principais Tecnologias
- Spring Boot 3.5.x
- Spring Data MongoDB
- Spring Security 6
- Thymeleaf / Bootstrap / Font Awesome / jQuery via WebJars
- BCrypt para hash de senhas
- Dotenv (java-dotenv) para variáveis em desenvolvimento

## Fluxo de Autenticação
1. Usuário envia POST JSON para `/autenticacao` com `{ "contatoUsuario": { "email": "..." }, "senha": "..." }`.
2. Filtro `JsonUsernamePasswordAuthFilter` extrai credenciais e delega ao `AuthenticationManager`.
3. `DaoAuthenticationProvider` carrega usuário via `UsuarioDetailsService` (buscar por email) e valida senha BCrypt.
4. Em sucesso, resposta `200 Login ok`; sessão HTTP mantém `SecurityContext` (graças a `requireExplicitSave(false)`).
5. Acesso a rotas `/gestao/**` exige `ROLE_GESTOR`.
6. Logout via formulário POST para `/logout` inclui token CSRF.

Mais detalhes em [`SPRING_SECURITY.md`](SPRING_SECURITY.md).

## Padrões e Convenções
- Senhas nunca em texto puro: armazenar resultado de `BCryptPasswordEncoder`.
- Templates não incluem JS inline (preferir arquivos em `static/js`).
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
- [ ] Respostas de login em JSON estruturado.
- [ ] Endpoints REST para listar domínios (perfis, tipos, faixas etárias).
- [ ] Validação avançada de telefone + máscara.
- [ ] Auditoria / trilha de ações de usuários.
- [ ] Testes de integração para filtro de autenticação.
- [ ] Sanitização / normalização de campos (ex: email lower-case).
- [ ] Autocomplete de escolas (substituir campo texto `escolaId`).

## Documento de Segurança / Spring Security
Ver arquivo separado: [`SPRING_SECURITY.md`](SPRING_SECURITY.md)

---
Qualquer dúvida ou sugestão de melhoria, abrir issue no repositório.

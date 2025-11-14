# Spring Security – Detalhamento da Implementação

## Objetivos
Garantir autenticação confiável, controle de acesso por perfil e proteção contra ataques comuns (CSRF, session fixation, brute force parcialmente mitigado por BCrypt).

## Visão Geral da Configuração
Arquivo principal: `WebConfig.java`.
Principais pontos:
- `securityContext.requireExplicitSave(false)` habilita salvamento automático do `SecurityContext` após autenticação.
- Filtro custom JSON (`JsonUsernamePasswordAuthFilter`) processa o login via payload JSON.
- `DaoAuthenticationProvider` com `BCryptPasswordEncoder` evita aceitar senhas em texto puro.
- Autorização baseada em role: `/gestao/**` exige `hasRole("GESTOR")` (internamente autoridade `ROLE_GESTOR`).
- CSRF ativo por padrão; ignorado apenas para `/autenticacao/**` (login JSON), exigido em formulários (logout, cadastros futuros).
- Logout seguro via POST em `/logout` + token CSRF.

## Fluxo de Login (JSON)
1. Cliente envia POST para `/autenticacao` contendo:
```json
{
  "contatoUsuario": { "email": "exemplo@dominio.com" },
  "senha": "plaintextSenha"
}
```
2. Filtro lê o corpo e constrói `UsernamePasswordAuthenticationToken`.
3. `AuthenticationManager` delega ao `DaoAuthenticationProvider`:
   - Carrega usuário (busca por email em `UsuarioRepository` via `UsuarioDetailsService`).
   - Compara senha informada com hash BCrypt armazenado.
4. Sucesso: resposta `200` (texto simples "Login ok") + sessão já contém `SecurityContext`.
5. Falha: resposta `401` ("Login ou senha inválidos!").

## Classes Principais
### `JsonUsernamePasswordAuthFilter`
- Extende `UsernamePasswordAuthenticationFilter`.
- Ajusta `RequiresAuthenticationRequestMatcher` para aceitar POST `/autenticacao`.
- Converte JSON em credenciais.
- Usa handlers simples de sucesso/falha.

### `UsuarioDetailsService`
- Implementa `UserDetailsService`.
- Método `loadUserByUsername` consulta `UsuarioRepository.findByContatoUsuarioEmail(email)`.
- Constrói instância de `UsuarioDetails` com authorities derivadas do campo `perfilAcesso` do domínio (ex: `GESTOR` → `ROLE_GESTOR`).

### `UsuarioDetails`
- Implementa `UserDetails`.
- Retorna coleção de `GrantedAuthority` (uma ou mais; hoje uma principal).

## Autorizações
| Rota                | Regra                     | Observação                         |
|---------------------|---------------------------|------------------------------------|
| `/login`            | `permitAll()`             | Página de login (Thymeleaf)        |
| `/autenticacao/**`  | `permitAll()`             | JSON login                         |
| Recursos estáticos  | `permitAll()`             | CSS, JS, webjars                   |
| `/gestao/**`        | `hasRole("GESTOR")`       | Gestão restrita                    |
| Demais              | `authenticated()`         | Requer qualquer usuário logado     |

## CSRF
- Protegido em formulários (logout e CRUD). Cada template envia hidden input com `th:name="${_csrf.parameterName}"` e `th:value="${_csrf.token}"`.
- Ignorado para `/autenticacao/**` para permitir login via fetch sem token prévio.

## Logout
- Endpoint: `/logout` (POST).
- Em sucesso: redireciona para `/login?logout=true` (config padrão atual). Pode ser alterado para JSON modificando `logoutSuccessHandler`.

## Senhas
- Armazenadas com `BCryptPasswordEncoder`.
- Migração inicial de senhas texto puro (runner) já removida após conversão.
- Recomendações futuras: política de expiração, tentativas máximas, bloqueio temporário.

## Boas Práticas Aplicadas
- Nenhum form de logout via GET (evita ataques CSRF fáceis).
- Filtro custom isolado – evita lógica de autenticação em controller.
- Busca otimizada por email (sem percorrer todos usuários).
- Separação de responsabilidades (detalhes de segurança não misturados com lógica de negócio).

## Melhorias Futuras (Sugestões)
1. Padronizar respostas de login/logout em JSON (ex: `{ "status":"OK", "roles":["GESTOR"] }`).
2. Rate limiting / proteger contra brute force (ex: bucket de tentativas por IP + usuário).
3. Adicionar auditoria (log estruturado de login/logout, falhas, mudanças de perfil).
4. Implementar testes de integração usando `@SpringBootTest` + `WebTestClient` para cenários de acesso negado/permitido.
5. Adicionar camada de autorização mais granular (ex: métodos com `@PreAuthorize`).
6. Security Headers – configurar `http.headers(...)` (CSP, X-Frame-Options, etc.).
7. Remover `permitAll()` de `/logout` (não crítico, mas reforça intenção de permitir apenas usuários logados; POST + CSRF já protege).

## Exemplo de Teste de Integração (Pseudo-código)
```java
@WebTestClient
void acessoGestaoSemLogin_deveFalhar401() {
  webTestClient.get().uri("/gestao/dashboard")
    .exchange()
    .expectStatus().isUnauthorized();
}
```

## Resumo
A configuração atual fornece uma base segura: autenticação por filtro JSON, roles claros, CSRF ativo para operações de estado e senhas robustas com BCrypt. As melhorias listadas ampliam proteção contra cenários de alta tráfego e ameaças mais sofisticadas.

---
Dúvidas ou evolução de segurança? Abra uma issue ou documente propostas nesta página.

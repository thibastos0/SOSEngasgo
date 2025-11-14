package com.example.SOSEngasgo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro para processar login JSON em POST /autenticacao
 * Lê { contatoUsuario: { email }, senha } e delega ao AuthenticationManager.
 * O Spring Security gerencia a sessão e o SecurityContext automaticamente.
 */
public class JsonUsernamePasswordAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonUsernamePasswordAuthFilter() {
        super(new RegexRequestMatcher("^/autenticacao$", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        String contentType = request.getContentType();
        if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
            throw new IllegalArgumentException("Content-Type deve ser application/json");
        }

        JsonNode root = objectMapper.readTree(request.getInputStream());
        String email = null;
        if (root.has("contatoUsuario") && root.get("contatoUsuario").has("email")) {
            email = root.get("contatoUsuario").get("email").asText();
        }
        String senha = root.has("senha") ? root.get("senha").asText() : null;

        if (email == null || senha == null) {
            throw new IllegalArgumentException("JSON deve conter contatoUsuario.email e senha");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, senha);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // Delegar para o handler padrão que salvará o SecurityContext e chamará o successHandler
        super.successfulAuthentication(request, response, chain, authResult);
    }
}

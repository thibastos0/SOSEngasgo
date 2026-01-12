package com.example.SOSEngasgo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.SOSEngasgo.service.UsuarioDetailsService;
import com.example.SOSEngasgo.security.JsonUsernamePasswordAuthFilter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usar BCrypt explicitamente evita aceitar senhas em texto puro sem prefixo.
        return new BCryptPasswordEncoder();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public AuthenticationManager authenticationManager(UsuarioDetailsService usuarioDetailsService, PasswordEncoder passwordEncoder) {
        // Usa DaoAuthenticationProvider - recebe o bean do UsuarioDetailsService com repository injetado
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http
            // Permite que mudanças no SecurityContext sejam salvas automaticamente (comportamento legado)
            .securityContext(securityContext -> securityContext.requireExplicitSave(false))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/autenticacao/**",
            "/telegram/webhook"
            ))// Desabilita CSRF para endpoints específicos
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/autenticacao/**",         // Permitir acesso ao endpoint de login
                    
                    "/logout",                  // Permitir acesso ao logout
                    "/login",                   // Permitir acesso à página de login
                    "/",                        // Permitir acesso à página inicial
                    "/index.html",              // Permitir acesso à página inicial
                    "/css/**",                  // Permitir acesso aos CSS
                    "/js/**",                   // Permitir acesso aos JS (exceto os de gestão)
                    "/webjars/**",               // Permitir acesso aos webjars
                    "/telegram/webhook"         // Permitir acesso ao webhook do Telegram
                ).permitAll()
                .requestMatchers("/gestao/**").hasRole("GESTOR") // Proteger todas as rotas /gestao
                .anyRequest().authenticated() // qualquer outra pessoa precisa estar logado
            )
            .formLogin(login -> login
                .loginPage("/login") // Página de login personalizada
                .loginProcessingUrl("/autenticacao/form-login") // Endpoint para form padrão (não usado, login é via JSON custom)
                .defaultSuccessUrl("/emergencia", true) // Redirecionar após login para página de emergência
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Endpoint de logout
                .logoutSuccessUrl("/login?logout=true") // Redirecionar após logout
                .permitAll()
            )
            .exceptionHandling(exceptions -> exceptions
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // Redirecionar para /emergencia se usuário autenticado não tiver role necessária
                    response.sendRedirect("/emergencia");
                })
            );

        // Filtro de autenticação JSON para /autenticacao
        JsonUsernamePasswordAuthFilter jsonFilter = new JsonUsernamePasswordAuthFilter();
        jsonFilter.setAuthenticationManager(authenticationManager);
        AuthenticationSuccessHandler successHandler = (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Login ok");
        };
        AuthenticationFailureHandler failureHandler = (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Login ou senha inválidos!");
        };
        jsonFilter.setAuthenticationSuccessHandler(successHandler);
        jsonFilter.setAuthenticationFailureHandler(failureHandler);
        http.addFilterAt(jsonFilter, UsernamePasswordAuthenticationFilter.class);
           return http.build();
    }
}

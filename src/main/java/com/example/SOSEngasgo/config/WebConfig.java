package com.example.SOSEngasgo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/gestao/**")  // Proteger todas as rotas /gestao
                .excludePathPatterns(
                    "/autenticacao/**",         // Permitir acesso ao endpoint de login
                    "/logout",                  // Permitir acesso ao logout
                    "/login.html",              // Permitir acesso à página de login
                    "/",                        // Permitir acesso à página inicial
                    "/index.html",              // Permitir acesso à página inicial
                    "/css/**",                  // Permitir acesso aos CSS
                    "/js/**",                   // Permitir acesso aos JS (exceto os de gestão)
                    "/webjars/**"               // Permitir acesso aos webjars
                );
    }
}

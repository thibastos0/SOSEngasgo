package com.example.SOSEngasgo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        HttpSession session = request.getSession(false);
        
        // Verificar se existe sessão e se o usuário está autenticado
        if (session == null || session.getAttribute("autenticado") == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        // Usuário autenticado, permitir acesso
        return true;
    }
}

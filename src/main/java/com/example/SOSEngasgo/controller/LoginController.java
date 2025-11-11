package com.example.SOSEngasgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SOSEngasgo.model.Usuario;
import com.example.SOSEngasgo.service.AutenticaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/autenticacao")
public class LoginController {

    @Autowired
    private AutenticaService autenticaService;

    @PostMapping("")
    public ResponseEntity<String> autenticarUsuario(@RequestBody Usuario usuario, HttpSession session) {
        String email = usuario.getContatoUsuario().getEmail();
        String senha = usuario.getSenha();
        
        return autenticaService.autenticar(email, senha)
            .map(usuarioAutenticado -> {
                // Salvar dados do usuário na sessão
                session.setAttribute("usuarioId", usuarioAutenticado.getId());
                session.setAttribute("usuarioNome", usuarioAutenticado.getNome());
                session.setAttribute("perfilAcesso", usuarioAutenticado.getPerfilAcesso());
                session.setAttribute("autenticado", true);
                
                return ResponseEntity.ok("Login ok");
            })
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Login ou senha inválidos!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout realizado com sucesso");
    }

}

package com.example.SOSEngasgo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestao")
public class GestaoController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "gestao/dashboard";
    }

    @GetMapping("/usuarios")
    public String usuarios() {
        // Redireciona para lista de usuários (a criar)
        return "gestao/usuarios/lista";
    }

    @GetMapping("/usuarios/novo")
    public String novoUsuario() {
        // Formulário de novo usuário (a criar)
        return "gestao/usuarios/form";
    }

    @GetMapping("/relatorios")
    public String relatorios() {
        return "gestao/relatorios";
    }

    @GetMapping("/alertas")
    public String alertas() {
        return "gestao/alertas";
    }

    @GetMapping("/instituicao")
    public String instituicao() {
        return "gestao/instituicao";
    }

    @GetMapping("/auditoria")
    public String auditoria() {
        return "gestao/auditoria";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "gestao/perfil";
    }

    @GetMapping("/configuracoes")
    public String configuracoes() {
        return "gestao/configuracoes";
    }
}

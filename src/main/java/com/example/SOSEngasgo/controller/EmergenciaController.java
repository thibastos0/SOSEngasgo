package com.example.SOSEngasgo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller para página de acionamento de emergência.
 * Acessível a todos usuários autenticados (qualquer role).
 */
@Controller
@RequestMapping("/emergencia")
public class EmergenciaController {

    @GetMapping
    public String emergencia() {
        return "emergencia";
    }
}

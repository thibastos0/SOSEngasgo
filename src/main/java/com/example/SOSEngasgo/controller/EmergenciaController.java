package com.example.SOSEngasgo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.SOSEngasgo.telegram.service.TelegramService;
import org.springframework.security.core.Authentication;
import java.util.Map;


/**
 * Controller para página de acionamento de emergência.
 * Acessível a todos usuários autenticados (qualquer role).
 */
@Controller
@RequestMapping("/emergencia")
public class EmergenciaController {

    private final TelegramService telegramService;

    public EmergenciaController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @GetMapping
    public String emergencia() {
        return "emergencia";
    }

    @GetMapping("/acao")
    public String emergenciaAcao() {
        return "emergencia/acao";
    }

    // EmergenciaApiController.java
    @PostMapping("/api/emergencia/acionar")
    public ResponseEntity<?> acionar(Authentication auth) {
        String nome = auth.getName();
        telegramService.enviarAlertaEmergencia(nome, -23.55052, -46.633308); // Exemplo: São Paulo
        return ResponseEntity.ok(Map.of("status", "ok", "mensagem", "Emergência acionada"));
}

}

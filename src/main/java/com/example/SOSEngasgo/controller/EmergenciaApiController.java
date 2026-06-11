package com.example.SOSEngasgo.controller;

import com.example.SOSEngasgo.telegram.service.TelegramService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/emergencia")
public class EmergenciaApiController {

    private final TelegramService telegramService;

    public EmergenciaApiController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping("/acionar")
    public ResponseEntity<?> acionar(
            @RequestBody Map<String, Double> body,
            Authentication auth) {

        double latitude  = body.getOrDefault("latitude", 0.0);
        double longitude = body.getOrDefault("longitude", 0.0);
        String email = auth.getName(); // vem do FirebaseTokenFilter

        telegramService.enviarAlertaEmergencia(email, latitude, longitude);

        return ResponseEntity.ok(Map.of(
            "status", "ok",
            "mensagem", "Emergência acionada com sucesso"
        ));
    }
}
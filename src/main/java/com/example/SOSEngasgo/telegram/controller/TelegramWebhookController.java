package com.example.SOSEngasgo.telegram.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.SOSEngasgo.telegram.service.TelegramService;
import com.pengrad.telegrambot.model.Update;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/telegram")
public class TelegramWebhookController {

    //@Autowired
    private final TelegramService telegramService;

    public TelegramWebhookController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> onUpdateReceived(@RequestBody Update update) {
        
        telegramService.processUpdate(update);
        return ResponseEntity.ok().build();
    }
    

}
